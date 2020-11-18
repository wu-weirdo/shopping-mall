package com.edaochina.shopping.api.facade.trade.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.trade.WithdrawalSpecialUserMapper;
import com.edaochina.shopping.api.dao.user.SysCommunityPartnerMapper;
import com.edaochina.shopping.api.facade.trade.WithdrawalRecordFacade;
import com.edaochina.shopping.api.service.trade.WithdrawalRecordService;
import com.edaochina.shopping.api.service.user.SysUserAgentService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.constants.WithdrawalConstants;
import com.edaochina.shopping.domain.dto.trade.*;
import com.edaochina.shopping.domain.entity.trade.PaymentRecord;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import com.edaochina.shopping.domain.entity.trade.WithdrawalSpecialUser;
import com.edaochina.shopping.domain.entity.user.SysCommunityPartner;
import com.edaochina.shopping.domain.entity.user.SysUserAgent;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class WithdrawalRecordFacadeImpl implements WithdrawalRecordFacade {

    @Resource
    WithdrawalRecordService service;

    @Resource
    SysUserMerchantService merchantService;

    @Resource
    SysUserAgentService agentService;

    @Resource
    SysCommunityPartnerMapper sysCommunityPartnerMapper;

    @Resource
    WithdrawalSpecialUserMapper withdrawalSpecialUserMapper;

    private static String WITHDRAWAL_APPROVAL_KEY = "WITHDRAWAL_APPROVAL";

    final static BigDecimal auto_bank_rate = new BigDecimal(0.007);

    final static BigDecimal to_bank_rate = new BigDecimal(0.006);

    /**
     * 查询所有提现申请
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult list(SysWithdrawalRecordListDTO dto) {
        return BaseResult.successResult(service.selectWithdrawalList(dto));
    }

    /**
     * 审批提现申请
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult approval(WithdrawalRecordApprovalDTO dto) {
        //  审批加锁
        RedisTool.lock(WITHDRAWAL_APPROVAL_KEY + dto.getId());
        try {
            boolean flag = service.approval(dto);
            if (flag) {
                return BaseResult.successResult();
            } else {
                return BaseResult.failedResult();
            }
        } finally {
            //  审批完解锁
            RedisTool.unLock(WITHDRAWAL_APPROVAL_KEY + dto.getId());
        }


    }

    /**
     * app上提交提现申请
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult apply(WithdrawalApplyDTO dto) {
        //判断当前账户下是否存在未处理的提现申请，如有则不允许提交
        QueryWrapper<WithdrawalRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", dto.getUserId());
        wrapper.in("status", WithdrawalConstants.APPLYING, WithdrawalConstants.AGREE_WAIT_TO_ACCOUNT);
        if (dto.getApplyMoney().compareTo(new BigDecimal(0)) <= 0) {
            return BaseResult.failedResult(ReturnData.WITHDRAWAL_MONEY_ERROR.getCode(), ReturnData.WITHDRAWAL_MONEY_ERROR.getMsg());
        }
        List<WithdrawalRecord> tmpList = service.list(wrapper);
        if (tmpList.size() > 0) {
            return BaseResult.failedResult(ReturnData.WITHDRAWAL_APPLY_ALREADY_EXIST.getCode(), ReturnData.WITHDRAWAL_APPLY_ALREADY_EXIST.getMsg());
        }
        //判断当前账户余额是否可支持提现金额+手续费
        BigDecimal applyMoney = dto.getApplyMoney();
        BigDecimal chargeFee;
        BigDecimal antoBankRate = auto_bank_rate;
        BigDecimal toBankRate = to_bank_rate;
        WithdrawalSpecialUser withdrawalSpecialUser = withdrawalSpecialUserMapper.queryByUser(dto.getUserRole(), dto.getUserId());
        // 判断特殊提现用户，如果是的话将提现费率按特殊比例收取
        if (withdrawalSpecialUser != null) {
            if (withdrawalSpecialUser.getAutoBankRate() > 0 && withdrawalSpecialUser.getAutoBankRate() < 10000) {
                antoBankRate = new BigDecimal(withdrawalSpecialUser.getAutoBankRate()).divide(new BigDecimal(10000));
            }
            if (withdrawalSpecialUser.getToBankRate() > 0 && withdrawalSpecialUser.getToBankRate() < 10000) {
                toBankRate = new BigDecimal(withdrawalSpecialUser.getToBankRate()).divide(new BigDecimal(10000));
            }
        }
        // 提现到银行卡手续费为千分之七
        if (dto.getWithdrawalType() == 1 && StringUtils.isEmpty(dto.getBankCode())) {
            chargeFee = applyMoney.multiply(antoBankRate).setScale(2, RoundingMode.FLOOR);
        } else if (dto.getWithdrawalType() == 1) {
            chargeFee = applyMoney.multiply(toBankRate).setScale(2, RoundingMode.FLOOR);
        } else {
            chargeFee = applyMoney.multiply(toBankRate).setScale(2, RoundingMode.FLOOR);
        }
        if (!StringUtils.isEmpty(dto.getUserRole())) {
            //3-商家
            if (CommonConstants.THREE_STR.equals(dto.getUserRole())) {
                SysUserMerchant merchant = merchantService.getById(dto.getUserId());
                // 未审核不允许提现
                if (merchant.getCheckStatus() == 1) {
                    return BaseResult.failedResult(ReturnData.MERCHANT_CHECK_STATUS_ERROR.getCode(), ReturnData.MERCHANT_CHECK_STATUS_ERROR.getMsg());
                }
                // 判断金额是否允许提现
                if (merchant.getBalanceMoney().compareTo(applyMoney) < 0) {
                    return BaseResult.failedResult(ReturnData.BALANCE_MONEY_NOT_ENOUGH.getCode(), ReturnData.BALANCE_MONEY_NOT_ENOUGH.getMsg());
                }
            }
            //4-代理
            if (CommonConstants.FOUR_STR.equals(dto.getUserRole())) {
                SysUserAgent agent = agentService.getById(dto.getUserId());
                if (agent.getBalanceMoney().compareTo(applyMoney) < 0) {
                    return BaseResult.failedResult(ReturnData.BALANCE_MONEY_NOT_ENOUGH.getCode(), ReturnData.BALANCE_MONEY_NOT_ENOUGH.getMsg());
                }
            }
            // 6 - 群社合伙人
            if (RoleConstants.COMMUNITY_PARTENER_STRING.equals(dto.getUserRole())) {
                SysCommunityPartner sysCommunityPartner = sysCommunityPartnerMapper.selectByPrimaryKey(dto.getUserId());
                if (sysCommunityPartner.getBalanceMoney().compareTo(applyMoney) < 0) {
                    return BaseResult.failedResult(ReturnData.BALANCE_MONEY_NOT_ENOUGH.getCode(), ReturnData.BALANCE_MONEY_NOT_ENOUGH.getMsg());
                }
            }
        }
        boolean flag = service.apply(dto, applyMoney, chargeFee);
        if (flag) {
            return BaseResult.successResult();
        } else {
            return BaseResult.failedResult();
        }
    }

    /**
     * app上查询当前登录用户发起的提现申请记录
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult selectApplyRecord(AppWithdrawalRecordListDTO dto) {
        return BaseResult.successResult(service.selectApplyRecord(dto));
    }

    @Override
    public WithdrawalRecord getLastWithdrawalToBankInfo(LastWithdrawalDTO lastWithdrawalDTO) {
        //   查询最近一次提现到银行卡记录
        return service.getLastWithdrawalToBankInfo(lastWithdrawalDTO);
    }

    @Override
    public void withdrawalStatusHandler() {
        //  查询打款状态未知的记录
        List<PaymentRecord> paymentRecords = service.queryUnkown();
        for (PaymentRecord paymentRecord : paymentRecords) {
            service.withdrawalStatusHandler(paymentRecord);
        }
    }
}
