package com.edaochina.shopping.api.service.order;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.order.AppMerchantRefundOrderDTO;
import com.edaochina.shopping.domain.dto.trade.RefundApplyDTO;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.vo.order.AppRefundApplyVO;
import com.edaochina.shopping.domain.vo.order.AppRefundOrderDetailVO;
import com.github.binarywang.wxpay.exception.WxPayException;

import java.util.List;

/**
 * <p>
 * 退款申请表 服务类
 * </p>
 *
 * @since 2019-06-17
 */
public interface PayRefundApplyService {

    int insertRefundApply(RefundApplyDTO dto);

    AppRefundApplyVO refundApplyInfo(Integer id);

    int cancelApply(Integer id);

    PageResult merchantRefundOrders(AppMerchantRefundOrderDTO dto);

    int merchantRefundOrderCount(AppMerchantRefundOrderDTO dto);

    AppRefundOrderDetailVO RefundOrderDetail(Integer id);

    int refuseRefund(Integer id, String refuseReason);

    int agreeRefund(Integer id);

    void refundRemit(PayRefundApply payRefundApply) throws WxPayException;

    List<PayRefundApply> queryWaitRemit();

    List<PayRefundApply> queryMerchantUnHandler();

    void updateMerchantUnHandlerToUnConnect();

    AppRefundApplyVO queryRefundApplyInfoByOrderId(String id);

    void overTimeApplyRefund(OrderMain orderMain);
}
