package com.edaochina.shopping.web.order;


import com.edaochina.shopping.api.service.order.PayRefundApplyService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.order.AppMerchantRefundOrderDTO;
import com.edaochina.shopping.domain.dto.trade.RefundApplyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 退款申请表 前端控制器
 * </p>
 *
 * @since 2019-06-17
 */
@Api(description = "退款申请")
@RestController
@RequestMapping("/app/refundApply")
public class AppRefundApplyController {

    @Autowired
    private PayRefundApplyService payRefundApplyService;

    /**
     * 小程序用户申请退款
     *
     * @param dto
     * @return
     */
    @ApiOperation("小程序退款申请")
    @RequestMapping("/apply")
    @OperationLogMark("小程序用户申请退款")
    public AjaxResult apply(@RequestBody RefundApplyDTO dto) {
        return BaseResult.successResult(payRefundApplyService.insertRefundApply(dto));
    }

    /**
     * 小程序用户退款详情
     *
     * @param id 订单id
     * @return
     */
    @ApiOperation("小程序查看退款申请详情")
    @ApiImplicitParam(name = "id", value = "订单号")
    @RequestMapping("/refundApplyInfo")
    public AjaxResult refundApplyInfo(String id) {
        return BaseResult.successResult(payRefundApplyService.queryRefundApplyInfoByOrderId(id));
    }

    /**
     * 小程序用户取消退款申请
     *
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    @ApiOperation("小程序取消退款申请")
    @ApiImplicitParam(name = "id", value = "退款申请id")
    @OperationLogMark("小程序用户取消退款申请")
    public AjaxResult cancelApply(Integer id) {
        return BaseResult.successResult(payRefundApplyService.cancelApply(id));
    }

    /**
     * 小程序商家退款订单
     *
     * @param dto
     * @return
     */
    @ApiOperation("小程序商户查看退款订单列表")
    @RequestMapping("/refundOrders")
    public AjaxResult merchantRefundOrders(@RequestBody AppMerchantRefundOrderDTO dto) {
        return BaseResult.successResult(payRefundApplyService.merchantRefundOrders(dto));
    }

    /**
     * 小程序商家退款订单详情
     *
     * @param id
     * @return
     */
    @ApiOperation("商家退款订单详情")
    @ApiImplicitParam("退款申请id")
    @RequestMapping("/orderDetail")
    public AjaxResult merchantRefundOrderDetail(Integer id) {
        return BaseResult.successResult(payRefundApplyService.RefundOrderDetail(id));
    }

    /**
     * 小程序商家退款订单数量
     *
     * @param dto
     * @return
     */
    @ApiOperation("商家退款订单数，角标作用")
    @RequestMapping("/orderCount")
    public AjaxResult merchantRefundOrderCount(@RequestBody AppMerchantRefundOrderDTO dto) {
        return BaseResult.successResult(payRefundApplyService.merchantRefundOrderCount(dto));
    }

    /**
     * 小程序商家拒绝退款
     *
     * @param id
     * @param refuseReason
     * @return
     */
    @ApiOperation("小程序商家拒绝退款")
    @RequestMapping("/refuse")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "退款申请id"),
            @ApiImplicitParam(name = "refuseReason", value = "拒绝退款申请理由")})
    @OperationLogMark("小程序商家拒绝退款")
    public AjaxResult refuseRefund(Integer id, String refuseReason) {
        return BaseResult.successResult(payRefundApplyService.refuseRefund(id, refuseReason));
    }

    /**
     * 小程序商家同意退款
     *
     * @param id
     * @return
     */
    @ApiOperation("小程序同意退款申请")
    @RequestMapping("/agree")
    @ApiImplicitParam("同意退款申请id")
    @OperationLogMark("小程序商家同意退款")
    public AjaxResult agreeRefund(Integer id) {
        return BaseResult.successResult(payRefundApplyService.agreeRefund(id));
    }
}

