package com.edaochina.shopping.api.service.trade.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.trade.PaymentRecordMapper;
import com.edaochina.shopping.api.dao.trade.WithdrawalRecordMapper;
import com.edaochina.shopping.api.dao.user.SysCommunityPartnerMapper;
import com.edaochina.shopping.api.dao.user.SysUserAgentMapper;
import com.edaochina.shopping.api.dao.user.SysUserMerchantMapper;
import com.edaochina.shopping.api.service.pay.PayApi;
import com.edaochina.shopping.api.service.trade.WithdrawalRecordService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.trade.*;
import com.edaochina.shopping.domain.entity.trade.PaymentRecord;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import com.edaochina.shopping.domain.vo.trade.WithdrawalRecordVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-29
 */
@Service
public class WithdrawalRecordServiceImpl extends ServiceImpl<WithdrawalRecordMapper, WithdrawalRecord> implements WithdrawalRecordService {
    private Logger logger = LoggerFactory.getLogger(WithdrawalRecordServiceImpl.class);
    @Resource
    WithdrawalRecordMapper mapper;
    @Resource
    SysUserMerchantMapper merchantMapper;
    @Resource
    SysUserAgentMapper agentMapper;
    @Resource
    SysCommunityPartnerMapper sysCommunityPartnerMapper;

    @Resource
    PayApi payApi;

    @Resource
    PaymentRecordMapper paymentRecordMapper;


    /**
     * 查询申请列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult<WithdrawalRecordVo> selectWithdrawalList(SysWithdrawalRecordListDTO dto) {
        logger.info("selectWithdrawalList:" + JSON.toJSONString(dto));
        PageHelperUtils.setPageHelper(dto.getPages());
        List<WithdrawalRecordVo> withdrawalRecordVos = mapper.queryByDto(dto);
        return PageHelperUtils.parseToPageResult(withdrawalRecordVos);
    }

    /**
     * 审批提现申请
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean approval(WithdrawalRecordApprovalDTO dto) {
        logger.info("approval" + JSON.toJSONString(dto));
        WithdrawalRecord record = this.getById(dto.getId());
        if (record == null) {
            return false;
        }
        logger.info("record status:" + record.getStatus());
        if (0 != record.getStatus()) {
            // 如果已经审批了不允许再次审批
            return false;
        }
        record.setStatus(dto.getStatus());
        if (-1 == dto.getStatus()) {
            record.setRefuseReason(dto.getRefuseReason());
        }
        if (1 == dto.getStatus()) {
            // 現在改爲體現多少，總金額就扣多少
            BigDecimal total = record.getApplyMoney();
            //3-商家
            logger.info("record.getUserRole:" + record.getUserRole());
            int i = 0;
            if (CommonConstants.THREE_STR.equals(record.getUserRole())) {
             /*   SysUserMerchant merchant = merchantMapper.selectById(record.getUserId());
                BigDecimal tmpBalanceMoney = merchant.getBalanceMoney().subtract(total);
                merchant.setBalanceMoney(tmpBalanceMoney);*/
                i = merchantMapper.updateBalanceMoney(record.getUserId(), -total.doubleValue());
            }
            //4-代理
            if (CommonConstants.FOUR_STR.equals(record.getUserRole())) {
              /*  SysUserAgent agent = agentMapper.selectById(record.getUserId());
                BigDecimal tmpBalanceMoney = agent.getBalanceMoney().subtract(total);
                agent.setBalanceMoney(tmpBalanceMoney);
                agentMapper.updateById(agent);*/

                i = agentMapper.updateBalanceMoney(record.getUserId(), -total.doubleValue());
            }
            // 6-社群代理人
            if (RoleConstants.COMMUNITY_PARTENER_STRING.equals(record.getUserRole())) {
              /*  SysCommunityPartner sysCommunityPartner = sysCommunityPartnerMapper.selectByPrimaryKey(record.getUserId());
                BigDecimal tmpBalanceMoney = sysCommunityPartner.getBalanceMoney().subtract(total);
                sysCommunityPartner.setBalanceMoney(tmpBalanceMoney);
                sysCommunityPartnerMapper.updateByPrimaryKeySelective(sysCommunityPartner);*/

                i = sysCommunityPartnerMapper.updateBalanceMoney(record.getUserId(), -total.doubleValue());
            }
            if (i <= 0) {
                throw new YidaoException(ReturnData.BALANCE_MONEY_NOT_ENOUGH);
            }
            // 异步改同步
            /*String key = MDC.get("key");*/
           /* // 事物提交后处理打款请求
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    MDC.put("key", key);*/
            //  打款流程
            payToAccount(record);
            /*    }
            });*/
        }
        record.setOperationTime(LocalDateTime.now());
        return this.updateById(record);
    }

    private void payToAccount(WithdrawalRecord record) {
        logger.info("pay to account record :" + JSON.toJSONString(record));
        // 1:打款到银行卡 ,2:手动打款到银行卡，3：提现到零钱
        if (record.getWithdrawalType() == 1 && StringUtils.isBlank(record.getBankCode())) {
            //  更改状态为打款成功
            record.setStatus(1);
        } else {
            String partnerTradeNo = IdUtils.nextId();
            //  打款记录入库
            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.setId(partnerTradeNo);
            paymentRecord.setPaymentType(record.getWithdrawalType());
            paymentRecord.setPaymentMoney(record.getToAccountMoney());
            paymentRecord.setWithdrawalRecordId(record.getId());
            Map<String, String> result = payApi.payToAccount(record, partnerTradeNo);
            if ("success".equals(result.get("status"))) {
                //  更新成成功
                record.setStatus(1);
            } else if ("unknown".equals(result.get("status"))) {
                //  更新成未知
                record.setStatus(3);
            } else {
                //  更新成失败
                record.setStatus(4);
                record.setRefuseReason(result.get("msg"));
                //  打款金额回退
                rollbackWithdralMoney(record);
            }
            paymentRecord.setPaymentStatus(record.getStatus());
            paymentRecord.setPaymentReturn(result.get("data"));
            paymentRecordMapper.insertSelective(paymentRecord);
        }
        //  记录入库
        //updateById(record);
    }


    /**
     * app上提交提现申请
     *
     * @param dto
     * @return
     */
    @Override
    public boolean apply(WithdrawalApplyDTO dto, BigDecimal total, BigDecimal chargeFee) {
        logger.info("apply:" + JSON.toJSONString(dto) + "total:" + total + "chargeFee:" + chargeFee);
        WithdrawalRecord record = new WithdrawalRecord();
        BeanUtils.copyProperties(dto, record);
        record.setId(IdUtils.nextId());
        record.setApplyTime(LocalDateTime.now());
        record.setStatus(0);
        record.setToAccountMoney(total.subtract(chargeFee));
        record.setChargeFee(chargeFee);
        return this.save(record);
    }

    /**
     * app上查询用户发起的申请记录
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult<WithdrawalRecord> selectApplyRecord(AppWithdrawalRecordListDTO dto) {
        logger.info("selectApplyRecord:" + dto);
        QueryWrapper<WithdrawalRecord> wrapper = new QueryWrapper<>();
        logger.info("userId:" + dto.getUserId());
        if (!StringUtils.isEmpty(dto.getUserId())) {
            wrapper.eq("user_id", dto.getUserId());
        }
        wrapper.orderByDesc("apply_time");
        Pages pages = dto.getPages();
        IPage<WithdrawalRecord> page = this.page(new Page<>(pages.getPageIndex(), pages.getPageSize()), wrapper);
        List<WithdrawalRecord> list = null;
        try {
            list = BeanUtil.listBeanToList(page.getRecords(), WithdrawalRecord.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pages.setTotal((int) page.getTotal());
        return PageUtil.getPageResult(list, pages);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawalStatusHandler(PaymentRecord paymentRecord) {
        // 查询打款记录状态
        Map<String, String> queryResult = payApi.queryWithDrawalStatus(paymentRecord);
        WithdrawalRecord record = mapper.selectById(paymentRecord.getWithdrawalRecordId());
        String msg = "";
        if ("success".equals(queryResult.get("status"))) {
            //  更新成成功
            paymentRecord.setPaymentStatus(1);
        } else if ("unknown".equals(queryResult.get("status"))) {
            //  更新成未知
            paymentRecord.setPaymentStatus(3);
        } else {
            //  更新成失败
            paymentRecord.setPaymentStatus(4);
            msg = queryResult.get("msg");
            //  打款金额回退
            rollbackWithdralMoney(record);
        }
        paymentRecord.setLastQueryReturn(queryResult.get("data"));
        paymentRecord.setLastQueryTime(LocalDateTime.now());
        if (paymentRecordMapper.updateByPrimaryKeySelective(paymentRecord) <= 0) {
            throw new YidaoException(ReturnData.HAS_HANDLERED);
        }
        ;
        record.setStatus(paymentRecord.getPaymentStatus());
        record.setRefuseReason(msg);
        // 更新打款记录
        mapper.updateById(record);
    }

    // 回退打款金额
    private boolean rollbackWithdralMoney(WithdrawalRecord withdrawalRecord) {
        if (CommonConstants.THREE_STR.equals(withdrawalRecord.getUserRole())) {
           /* SysUserMerchant merchant = merchantMapper.selectById(withdrawalRecord.getUserId());
            BigDecimal tmpBalanceMoney = merchant.getBalanceMoney().add(withdrawalRecord.getApplyMoney());
            merchant.setBalanceMoney(tmpBalanceMoney);*/
            merchantMapper.updateBalanceMoney(withdrawalRecord.getUserId(), withdrawalRecord.getApplyMoney().doubleValue());
        }
        //4-代理
        if (CommonConstants.FOUR_STR.equals(withdrawalRecord.getUserRole())) {
           /* SysUserAgent agent = agentMapper.selectById(withdrawalRecord.getUserId());
            BigDecimal tmpBalanceMoney = agent.getBalanceMoney().add(withdrawalRecord.getApplyMoney());
            agent.setBalanceMoney(tmpBalanceMoney);
            agentMapper.updateById(agent);*/
            agentMapper.updateBalanceMoney(withdrawalRecord.getUserId(), withdrawalRecord.getApplyMoney().doubleValue());
        }
        // 6-社群代理人
        if (RoleConstants.COMMUNITY_PARTENER_STRING.equals(withdrawalRecord.getUserRole())) {
           /* SysCommunityPartner sysCommunityPartner = sysCommunityPartnerMapper.selectByPrimaryKey(withdrawalRecord.getUserId());
            BigDecimal tmpBalanceMoney = sysCommunityPartner.getBalanceMoney().add(withdrawalRecord.getApplyMoney());
            sysCommunityPartner.setBalanceMoney(tmpBalanceMoney);
            sysCommunityPartnerMapper.updateByPrimaryKeySelective(sysCommunityPartner);*/
            sysCommunityPartnerMapper.updateBalanceMoney(withdrawalRecord.getUserId(), withdrawalRecord.getApplyMoney().doubleValue());
        }
        return true;
    }

    @Override
    public WithdrawalRecord getLastWithdrawalToBankInfo(LastWithdrawalDTO lastWithdrawalDTO) {
        return mapper.getLastWithdrawalToBankInfo(lastWithdrawalDTO);
    }

    @Override
    public List<PaymentRecord> queryUnkown() {
        return paymentRecordMapper.queryUnkown();
    }
}
