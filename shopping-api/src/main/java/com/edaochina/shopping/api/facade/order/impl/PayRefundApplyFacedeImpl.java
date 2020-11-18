package com.edaochina.shopping.api.facade.order.impl;

import com.edaochina.shopping.api.facade.order.PayRefundApplyFacede;
import com.edaochina.shopping.api.service.order.PayRefundApplyService;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.mail.MailService;
import com.edaochina.shopping.domain.constants.LockConstants;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jintian
 * @date 2019/6/24 14:55
 */
@Component
public class PayRefundApplyFacedeImpl implements PayRefundApplyFacede {

    private final PayRefundApplyService payRefundApplyService;
    private final MailService mailService;

    public PayRefundApplyFacedeImpl(PayRefundApplyService payRefundApplyService, MailService mailService) {
        this.payRefundApplyService = payRefundApplyService;
        this.mailService = mailService;
    }

    @Override
    public void refundsRemit() {
        int num = 0;
        do {
            //  查询退款待打款订单
            List<PayRefundApply> payRefundApplies = payRefundApplyService.queryWaitRemit();
            num = payRefundApplies.size();
            //  打款给用户
            for (PayRefundApply payRefundApply : payRefundApplies) {
                // 根据支付单号来加锁而不是订单号
                if (RedisTool.lock(LockConstants.ORDER_REFUND_KEY + payRefundApply.getOutOrderNo())) {
                    try {
                        payRefundApplyService.refundRemit(payRefundApply);
                    } catch (Exception e) {
                        e.printStackTrace();
                        mailService.report(e);
                    } finally {
                        RedisTool.unLock(LockConstants.ORDER_REFUND_KEY + payRefundApply.getOutOrderNo());
                    }
                }
            }
        } while (num == 100);
    }

    /**
     * 商家24小时未处理订单处理
     */
    @Override
    public void refundsMerchantUnHandler() {
        payRefundApplyService.updateMerchantUnHandlerToUnConnect();
    }
}
