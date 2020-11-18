package com.edaochina.shopping.api.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.dao.order.PayRefundApplyMapper;
import com.edaochina.shopping.api.dao.trade.PayRefundImgsMapper;
import com.edaochina.shopping.api.service.sys.SysPayRefundApplyService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.StringUtil;
import com.edaochina.shopping.common.utils.excel.ExportExcelUtil;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.OrderStatusConstants;
import com.edaochina.shopping.domain.constants.OrderTypeConstants;
import com.edaochina.shopping.domain.constants.PayRefundApplyStatus;
import com.edaochina.shopping.domain.dto.sys.SysOrderRefundDTO;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.entity.trade.PayRefundImgs;
import com.edaochina.shopping.domain.vo.sys.ExportSysOrderRefundVo;
import com.edaochina.shopping.domain.vo.sys.SysOrderRefundVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @since : 2019/6/18 14:32
 */
@Service
public class SysPayRefundApplyServiceImpl implements SysPayRefundApplyService {
    private Logger logger = LoggerFactory.getLogger(SysPayRefundApplyServiceImpl.class);

    private final PayRefundApplyMapper mapper;
    private final PayRefundImgsMapper imgsMapper;
    private final OrderMainMapper orderMainMapper;

    public SysPayRefundApplyServiceImpl(PayRefundApplyMapper mapper, PayRefundImgsMapper imgsMapper, OrderMainMapper orderMainMapper) {
        this.mapper = mapper;
        this.imgsMapper = imgsMapper;
        this.orderMainMapper = orderMainMapper;
    }

    @Override
    public PageResult<SysOrderRefundVo> list(SysOrderRefundDTO dto, Pages pages) {
        JWTBean bean = JWTThreadLocalHelper.get();
        if (bean.isMerchant()) {
            dto.setShopId(bean.getId());
        }
        pages.setTotal(mapper.orderRefundCount(dto));
        dto.setPages(pages);
        List<SysOrderRefundVo> orderRefundVos = mapper.list(dto).parallelStream().peek(SysOrderRefundVo::computeStatus).collect(Collectors.toList());
        return PageUtil.getPageResult(orderRefundVos, pages);
    }

    @Override
    public SysOrderRefundVo selectById(int id) {
        QueryWrapper<PayRefundImgs> wrapper = new QueryWrapper<>();
        wrapper.eq("refund_apply_id", id);
        SysOrderRefundDTO dto = SysOrderRefundDTO.build().setId(id);
        Optional<SysOrderRefundVo> voOptional = mapper.list(dto)
                .stream()
                .findFirst();
        voOptional.ifPresent(sysOrderRefundVo -> sysOrderRefundVo.setImgs(imgsMapper.selectList(wrapper)).computeStatus());
        return voOptional.orElse(SysOrderRefundVo.build());
    }

    @Override
    public int update(SysOrderRefundDTO dto) {
        // 根据平台处理结果修改订单上的订单退款状态
        if (dto.getHandleStatus() == 30) {
            orderMainMapper.updateOrderRefundStatus(dto.getOrderId(), 2);
        } else if (dto.getHandleStatus() == 20) {
            orderMainMapper.updateOrderRefundStatus(dto.getOrderId(), 3);
        }
        return mapper.sysUpdate(dto);
    }

    @Override
    public PayRefundApply selectByOrderIdAndInRefundStatus(String orderId, List<Integer> status) {
        return mapper.selectByOrderIdAndInRefundStatus(orderId, status);
    }

    @Override
    public String export(SysOrderRefundDTO dto) {
        ExportExcelUtil.Builder builder = ExportExcelUtil.Builder.newInstance()
                .createSheet("退款订单导出")
                .createHeader(Arrays.asList("绑定区县", "订单id", "商户交易号", "商家名称", "商品名称", "商品类型名称",
                        "订单金额", "订单状态", "用户昵称", "用户ID", "用户手机号", "下单时间", "申请退款时间",
                        "申请退款金额", "退款原因", "申请状态", "退款处理人", "处理结果", "退款金额", "拒绝原因",
                        "处理时间"))
                .setFields(Arrays.asList("getAddress", "getOrderId", "getOrderNo", "getMerchantTitle", "getGoodsName",
                        "getGoodsTypeName", "getTotalFee", "getOrderStatus", "getNickname", "getUserId", "getUserPhone",
                        "getOrderCreateTime", "getApplyRefundTime", "getApplyFee", "getRefundReason",
                        "getApplyRefundStatus", "getHandleUserName", "getRefundStatus", "getRefundFee",
                        "getShoperRefuseReason", "getHandleRefundTime"));
        int count = mapper.orderRefundCount(dto);
        dto.getPages().setPageSize(5000);
        dto.getPages().setPageIndex(1);
        do {
            List<ExportSysOrderRefundVo> exportSysOrderRefundVos = mapper.exportList(dto);
            builder.put(exportSysOrderRefundVos);
            count = count - 5000;
            dto.getPages().setPageIndex(dto.getPages().getPageIndex() + 1);
        } while (count > 0);
        return builder.export();
    }

    @Override
    public int cancelApply(Integer id) {
        return mapper.updateApplyStatus(id, 20);
    }

    @Override
    public int collectUser(int id) {
        return mapper.updateCollectUserStatus(id);
    }

    @Override
    public int collectMerchant(int id) {
        return mapper.updateCollectMerchantStatus(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int autoRefund(String orderId, PayRefundApply payRefundApply) {
        OrderMain orderMain = orderMainMapper.selectById(orderId);
        if (payRefundApply == null) {
            payRefundApply = new PayRefundApply();
        }
        checkRefundApply(orderMain, payRefundApply);
        payRefundApply.setOrderId(orderMain.getId());
        payRefundApply.setHandleStatus(PayRefundApplyStatus.HandleStatus.AGREED);
        payRefundApply.setRefundStatus(PayRefundApplyStatus.RefundStatus.TO_BE_REFUNDED);
        payRefundApply.setShoperHandleStatus(30);
        // 设置支付单号
        payRefundApply.setOutOrderNo(orderMain.getOrderNo());
        // 设置订单类型
        payRefundApply.setOrderType(OrderTypeConstants.SECKILL_ORDER.getCode());
        payRefundApply.setApplyRefundTime(LocalDateTime.now());
        payRefundApply.setUserPhone(orderMain.getPhone());
        payRefundApply.setUserId(orderMain.getUserId());
        payRefundApply.setTotalFee(orderMain.getActualPrice());
        if (StringUtils.isEmpty(payRefundApply.getRefundMethod())) {
            payRefundApply.setRefundMethod("拼团失败自动退款");
        }
        if (StringUtils.isEmpty(payRefundApply.getRefundReason())) {
            payRefundApply.setRefundReason("拼团失败");
        }
        payRefundApply.setApplyFee(orderMain.getActualPrice().toString());
        if (StringUtils.isEmpty(payRefundApply.getRefundFee())) {
            payRefundApply.setRefundFee(orderMain.getActualPrice());
        }
        payRefundApply.setShoperId(orderMain.getShoperId());
        orderMainMapper.updateOrderRefundStatus(orderMain.getId(), 1);
        return mapper.insertRefundApply(payRefundApply);
    }

    private void checkRefundApply(OrderMain orderMain, PayRefundApply payRefundApply) {
        if (!orderMain.getStatus().equals(OrderStatusConstants.WAIT_USE) &&
                !orderMain.getStatus().equals(OrderStatusConstants.WAIT_CALLBACK)) {
            throw new YidaoException(ReturnData.REFUND_ERROR.getCode(), ReturnData.REFUND_ERROR.getMsg());
        }
        // 判断是否有退款
        if (0 != orderMain.getHasRefund()) {
            throw new YidaoException(ReturnData.REFUND_HAS_ERROR);
        }
        if (StringUtil.containsEmoji(payRefundApply.getRefundExplain())) {
            throw new YidaoException(ReturnData.ERROR_ORDER_REASON_ERROR);
        }
    }


}
