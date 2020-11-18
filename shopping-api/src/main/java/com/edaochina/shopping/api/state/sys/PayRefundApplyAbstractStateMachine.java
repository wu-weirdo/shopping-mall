package com.edaochina.shopping.api.state.sys;

import com.edaochina.shopping.api.service.sys.SysPayRefundApplyService;
import com.edaochina.shopping.common.state.AbstractStateMachine;
import com.edaochina.shopping.domain.constants.PayRefundApplyStatus;
import com.edaochina.shopping.domain.dto.sys.SysOrderRefundDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 退款订单状态机
 * @since : 2019/6/26 10:53
 */
@Component
public class PayRefundApplyAbstractStateMachine extends AbstractStateMachine<PayRefundApplyState, PayRefundApplyEvents> {

    private final SysPayRefundApplyService payRefundApplyService;

    public PayRefundApplyAbstractStateMachine(SysPayRefundApplyService payRefundApplyService) {
        this.payRefundApplyService = payRefundApplyService;
    }

    @Override
    protected void init() {
        withHandle()
                .setDefaultGetCurrentState(payRefundApplyEventsMessage -> {
                    SysOrderRefundDTO dto = payRefundApplyEventsMessage.getHeaders().get("dto", SysOrderRefundDTO.class);
                    assert dto != null;
                    return PayRefundApplyState.valueOf(payRefundApplyService.selectById(dto.getId()).getHandleStatus());
                })
                //同意事件
                .setCheckStates(Arrays.asList(PayRefundApplyState.PENDING, PayRefundApplyState.TO_BE_COORDINATE, PayRefundApplyState.COORDINATED))
                .setEvent(PayRefundApplyEvents.AGREE)
                .setAction(payRefundApplyEventsMessage -> {
                    SysOrderRefundDTO dto = payRefundApplyEventsMessage.getHeaders().get("dto", SysOrderRefundDTO.class);
                    assert dto != null;
                    dto.setHandleStatus(PayRefundApplyStatus.HandleStatus.AGREED);
                    dto.setRefundStatus(PayRefundApplyStatus.RefundStatus.TO_BE_REFUNDED);
                    return payRefundApplyService.update(dto);
                })
                .build().and()
                //拒绝事件
                .setCheckStates(Collections.singletonList(PayRefundApplyState.PENDING))
                .setEvent(PayRefundApplyEvents.REJECT)
                .setAction(payRefundApplyEventsMessage -> {
                    if (payRefundApplyEventsMessage != null && payRefundApplyEventsMessage.getHeaders().containsKey("dto")) {
                        SysOrderRefundDTO dto = payRefundApplyEventsMessage.getHeaders().get("dto", SysOrderRefundDTO.class);
                        assert dto != null;
                        dto.setHandleStatus(PayRefundApplyStatus.HandleStatus.REJECTED);
                        dto.setRefundStatus(PayRefundApplyStatus.RefundStatus.REJECTED);
                        return payRefundApplyService.update(dto);
                    }
                    return 0;
                })
                .build().and()
                //联系事件
                .setCheckStates(Arrays.asList(PayRefundApplyState.TO_BE_COORDINATE, PayRefundApplyState.PENDING))
                .setEvent(PayRefundApplyEvents.COORDINATE)
                .setAction(payRefundApplyEventsMessage -> {
                    if (payRefundApplyEventsMessage != null && payRefundApplyEventsMessage.getHeaders().containsKey("dto")) {
                        SysOrderRefundDTO dto = payRefundApplyEventsMessage.getHeaders().get("dto", SysOrderRefundDTO.class);
                        assert dto != null;
                        dto.setCollectMerchantStatus(20);
                        return payRefundApplyService.update(dto);
                    }
                    return 0;
                })
                .build();
    }
}
