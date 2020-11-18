package com.edaochina.shopping.web.order;

import com.edaochina.shopping.api.service.sys.SysPayRefundApplyService;
import com.edaochina.shopping.api.state.sys.PayRefundApplyAbstractStateMachine;
import com.edaochina.shopping.api.state.sys.PayRefundApplyEvents;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.sys.SysOrderRefundDTO;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @since : 2019/6/18 13:51
 */
@RestController
@RequestMapping("sys/orderRefund")
public class SysOrderRefundController {

    private final SysPayRefundApplyService sysPayRefundApplyService;
    private final PayRefundApplyAbstractStateMachine stateMachine;

    public SysOrderRefundController(SysPayRefundApplyService sysPayRefundApplyService, PayRefundApplyAbstractStateMachine stateMachine) {
        this.sysPayRefundApplyService = sysPayRefundApplyService;
        this.stateMachine = stateMachine;
    }

    @GetMapping
    public AjaxResult list(SysOrderRefundDTO dto, Pages pages) {
        return BaseResult.successResult(sysPayRefundApplyService.list(dto, pages));
    }

    @PutMapping("/agree")
    @OperationLogMark("退款订单-同意")
    public AjaxResult agree(@RequestBody SysOrderRefundDTO dto, @RequestHeader("token") String token) {
        dto.setHandleRefundTime(LocalDateTime.now());
        dto.setHandleUserId(JWTUtil.verifyToken(token).getId());
        Object result = stateMachine.handleEvent(MessageBuilder.withPayload(PayRefundApplyEvents.AGREE).setHeader("dto", dto).build());
        return BaseResult.successResult(result);
    }

    @PutMapping("/reject")
    @OperationLogMark("退款订单-拒绝")
    public AjaxResult reject(@RequestBody SysOrderRefundDTO dto, @RequestHeader("token") String token) {
        dto.setHandleRefundTime(LocalDateTime.now());
        dto.setHandleUserId(JWTUtil.verifyToken(token).getId());
        Object result = stateMachine.handleEvent(MessageBuilder.withPayload(PayRefundApplyEvents.REJECT).setHeader("dto", dto).build());
        return BaseResult.successResult(result);
    }

    @GetMapping(value = "collectUser/{id}")
    @OperationLogMark("退款订单-联络用户")
    public AjaxResult collectUser(@PathVariable("id") int id) {
        return BaseResult.successResult(sysPayRefundApplyService.collectUser(id));
    }

    @GetMapping(value = "collectMerchant/{id}")
    @OperationLogMark("退款订单-联络商家")
    public AjaxResult collectMerchant(@PathVariable("id") int id) {
        return BaseResult.successResult(sysPayRefundApplyService.collectMerchant(id));
    }


    @GetMapping(value = "/{id}")
    public AjaxResult selectOneById(@PathVariable("id") int id) {
        return BaseResult.successResult(sysPayRefundApplyService.selectById(id));
    }

    @PostMapping(value = "/export")
    @OperationLogMark("退款订单导出")
    public AjaxResult export(@RequestBody SysOrderRefundDTO dto) {
        return BaseResult.successResult(sysPayRefundApplyService.export(dto));
    }

}
