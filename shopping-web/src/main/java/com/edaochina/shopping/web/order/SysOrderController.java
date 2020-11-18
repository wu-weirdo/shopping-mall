package com.edaochina.shopping.web.order;


import com.edaochina.shopping.api.facade.order.SysOrderFacade;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.annotation.PermissionsMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.goods.DeleteOrdersDTO;
import com.edaochina.shopping.domain.dto.order.QuerySysOrderDTO;
import com.edaochina.shopping.domain.dto.order.QuerySysWriteOffOrderDTO;
import com.edaochina.shopping.domain.dto.order.SysOrderDto;
import com.edaochina.shopping.domain.dto.order.WriteOffDTO;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/sys/order")
public class SysOrderController {

    private final SysOrderFacade sysOrderFacade;

    public SysOrderController(SysOrderFacade sysOrderFacade) {
        this.sysOrderFacade = sysOrderFacade;
    }

    @PostMapping("/refund")
    @OperationLogMark("申请退款")
    public AjaxResult apply(@RequestBody PayRefundApply payRefundApply) {
        return BaseResult.successResult(sysOrderFacade.refund(payRefundApply));
    }

    /**
     * 批量删除订单
     *
     * @return
     */
    @PostMapping("/deleteOrders")
    @OperationLogMark("批量删除订单")
    @PermissionsMark(RoleConstants.ADMIN_ROLE)
    public AjaxResult deleteOrders(@RequestBody DeleteOrdersDTO deleteOrdersDTO) {
        return BaseResult.successResult(sysOrderFacade.deleteOrders(deleteOrdersDTO));
    }


    /**
     * 核销
     *
     * @return
     */
    @PostMapping("/writeOff")
    @OperationLogMark("核销")
    public AjaxResult writeOff(@RequestBody WriteOffDTO writeOffDTO) {
        return BaseResult.successResult(sysOrderFacade.writeOff(writeOffDTO));
    }

    /**
     * 查询订单列表
     *
     * @return
     */
    @PostMapping("/getOrderList")
    public AjaxResult getOrderList(@RequestBody SysOrderDto querySysOrderDTO) {
        return BaseResult.successResult(sysOrderFacade.getOrderList(querySysOrderDTO));
    }

    /**
     * 查询获取核销列表
     *
     * @return
     */
    @ApiOperation("查询核销订单列表")
    @PostMapping("/getWriteOffList")
    public AjaxResult getWriteOffList(@RequestBody QuerySysWriteOffOrderDTO querySysOrderDTO) {
        return BaseResult.successResult(sysOrderFacade.getWriteOffList(querySysOrderDTO));
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    @PostMapping("/getOrderDetail")
    public AjaxResult getOrderDetail(@RequestBody QuerySysOrderDTO querySysOrderDTO) {
        return BaseResult.successResult(sysOrderFacade.getOrderDetail(querySysOrderDTO));
    }

    /**
     * 导出订单
     *
     * @return
     */
    @PostMapping("/exportOrder")
    public AjaxResult exportOrder(@RequestBody SysOrderDto querySysOrderDTO) {
        return BaseResult.successResult(sysOrderFacade.exportOrder(querySysOrderDTO));
    }

    @PostMapping("getShareOrder")
    public AjaxResult getShareOrder(@RequestBody SysShareGoodsProfitDTO dto) {
        return BaseResult.successResult(sysOrderFacade.getShareOrder(dto));
    }

    @GetMapping("getShareOrderDetail")
    public AjaxResult getShareOrderDetail(String id) {
        return BaseResult.successResult(sysOrderFacade.getShareOrderDetail(id));
    }

}

