package com.edaochina.shopping.web.order;


import com.edaochina.shopping.api.facade.order.AppOrderFacade;
import com.edaochina.shopping.api.service.order.AppOrderService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.order.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Api(description = "订单")
@RestController
@RequestMapping("/app/order")
public class AppOrderController {

    @Autowired
    private AppOrderFacade appOrderFacade;

    @Resource
    private AppOrderService appOrderService;

    /**
     * 取消支付
     *
     * @return
     */
    @ApiOperation("用户取消支付")
    @PostMapping("/cancelPay")
    @OperationLogMark("取消支付")
    public AjaxResult cancelPay(@RequestBody OrderPayDTO orderPayDTO) {
        return BaseResult.successResult(appOrderFacade.cancelPay(orderPayDTO));
    }


    /**
     * 查询订单列表
     *
     * @return
     */
    @ApiOperation("用户获取订单列表")
    @PostMapping("/v2/getOrderList")
    public AjaxResult v2GetOrderList(@RequestBody QueryAppOrderDTO queryAppOrderDTO) {
        return BaseResult.successResult(appOrderFacade.v2GetOrderList(queryAppOrderDTO));
    }

    /**
     * 查询订单详情
     *
     * @return
     */
    @ApiOperation("获取订单详情")
    @PostMapping("/getOrderDetail")
    public AjaxResult getOrderDetail(@RequestBody QueryAppOrderDTO queryAppOrderDTO) {
        return BaseResult.successResult(appOrderFacade.getOrderDetail(queryAppOrderDTO));
    }


    /**
     * 2.8 小程序查询商家下的订单列表
     * (商家登陆后查看)
     *
     * @return
     */
    @ApiOperation("商户获取订单列表")
    @PostMapping("/getMerchantOrders")
    public AjaxResult getMerchantOrders(@RequestBody QueryMerchantOrderDTO dto) {
        return BaseResult.successResult(appOrderFacade.getMerchantOrders(dto));
    }

    /**
     * 小程序核销商家下的订单
     * （我的-商家登陆-核销）
     *
     * @return
     */
    @ApiOperation("用户核销")
    @PostMapping("/writeOffOrder")
    @OperationLogMark("小程序核销商家下的订单")
    public AjaxResult writeOff(@RequestBody WriteOffDTO writeOffDTO) {
        boolean flag = appOrderFacade.writeOff(writeOffDTO);
        if (flag) {
            return BaseResult.successResult(appOrderFacade.writeOff(writeOffDTO));
        } else {
            return BaseResult.failedResult();
        }
    }

    @ApiOperation("用户批量核销")
    @PostMapping("/writeOffOrder/batch")
    @OperationLogMark("小程序批量核销商家下的订单")
    public AjaxResult writeOffBatch(@RequestBody List<WriteOffDTO> dtoList) {
        return BaseResult.successResult(appOrderFacade.writeOff(dtoList));
    }


    /**
     * 小程序获取用户订单数
     *
     * @param dto
     * @return
     */
    @ApiOperation("用户获取提货单数据统计信息")
    @RequestMapping("/getUserOrderCount")
    public AjaxResult getUserOrderCount(@RequestBody AppTakeGoodsDTO dto) {
        return BaseResult.successResult(appOrderService.queryUserOrderCount(dto));
    }

    /**
     * 小程序获取用户提货单列表
     *
     * @param dto
     * @return
     */
    @ApiOperation("用户获取提货单列表")
    @RequestMapping("/getUserOrderList")
    public AjaxResult getUserOrderList(@RequestBody AppTakeGoodsDTO dto) {
        return BaseResult.successResult(appOrderService.queryUserOrderList(dto));
    }

    @ApiOperation("获取商家今日核销数统计")
    @RequestMapping("getTodayWriteOffCount")
    public AjaxResult getTodayWriteOffCount(String merchantId) {
        return BaseResult.successResult(appOrderService.getTodayWriteOffCount(merchantId));
    }

    /**
     * 小程序获取用户待提货订单数
     *
     * @param userId
     * @return
     */
    @ApiOperation("获取用户待提货订单数")
    @RequestMapping("/getWaitTakeGoodsCount")
    public AjaxResult getWaitTakeGoodsCount(String userId) {
        return BaseResult.successResult(appOrderService.waitTakeGoodsCount(userId));
    }

    /**
     * 拼团商品已下订单列表
     *
     * @param goodsId
     * @return
     */
    @ApiOperation("获取商品拼团订单列表")
    @RequestMapping("/getCollectOrderList")
    public AjaxResult getCollectOrderList(String goodsId) {
        return BaseResult.successResult(appOrderService.getCollectOrderList(goodsId));
    }

    /**
     * 用户拼团订单列表
     */
    @ApiOperation("获取用户拼团订单列表")
    @RequestMapping("/userCollectOrderList")
    public AjaxResult userCollectOrderList(@RequestBody AppCollectOrderDTO dto) {
        return BaseResult.successResult(appOrderService.queryUserCollectOrder(dto));
    }

    @ApiOperation("获取拼团订单详情")
    @RequestMapping("collectOrderDetail")
    public AjaxResult collectOrderDetail(String orderId) {
        return BaseResult.successResult(appOrderService.queryCollectOrderDetail(orderId));
    }

    /**
     * 小程序用户退款订单数
     *
     * @param userId 用户id
     * @return 退款订单数
     */
    @ApiOperation("获取用户今日退款订单数")
    @PostMapping("refundOrderCount")
    public AjaxResult refundOrderCount(String userId) {
        return BaseResult.successResult(appOrderService.refundOrderCount(userId));
    }
}

