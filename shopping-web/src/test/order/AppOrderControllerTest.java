package order;

import base.TestSupport;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.order.*;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.web.order.AppOrderController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : AppOrderControllerTest
 * @since : 2019/7/11 15:18
 */
public class AppOrderControllerTest extends TestSupport {

    @Autowired
    AppOrderController appOrderController;

    @Before
    public void before() {
        fake.fakeOrderMain();
    }

    /**
     * 取消支付
     */
    @Test
    @Ignore
    public void cancelPay() {
        OrderPayDTO orderPayDTO = new OrderPayDTO();
        orderPayDTO.setOrderId(fake.getFakeOrderMain().getId());
        AjaxResult ajaxResult = appOrderController.cancelPay(orderPayDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }


    /**
     * 查询订单详情
     */
    @Test
    public void getOrderDetail() {
        QueryAppOrderDTO queryAppOrderDTO = new QueryAppOrderDTO();
        queryAppOrderDTO.setId(fake.getFakeOrderMain().getId());
        AjaxResult ajaxResult = appOrderController.getOrderDetail(queryAppOrderDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 2.8 小程序查询商家下的订单列表
     * (商家登陆后查看)
     */
    @Test
    public void getMerchantOrders() throws IllegalAccessException, InstantiationException {
        QueryMerchantOrderDTO dto = new QueryMerchantOrderDTO();
        dto.setPages(new Pages());
        AjaxResult ajaxResult = appOrderController.getMerchantOrders(dto);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 小程序核销商家下的订单
     * （我的-商家登陆-核销）
     */
    @Test
    public void writeOff() {
        WriteOffDTO writeOffDTO = new WriteOffDTO();
        writeOffDTO.setId(fake.getFakeOrderMain().getId());
        AjaxResult ajaxResult = appOrderController.writeOff(writeOffDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }


    /**
     * 小程序获取用户订单数
     */
    @Test
    public void getUserOrderCount() {
        AppTakeGoodsDTO dto = new AppTakeGoodsDTO();
        dto.setPages(new Pages());
        dto.setUserId(fake.getUser().getId());
        dto.setShoperId(fake.getSysUserMerchant().getId());
        AjaxResult ajaxResult = appOrderController.getUserOrderCount(dto);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 小程序获取用户提货单列表
     */
    @Test
    public void getUserOrderList() {
        AppTakeGoodsDTO dto = new AppTakeGoodsDTO();
        dto.setPages(new Pages());
        dto.setShoperId(fake.getSysUserMerchant().getId());
        dto.setUserId(fake.getUser().getId());
        AjaxResult ajaxResult = appOrderController.getUserOrderList(dto);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    @Test
    public void getTodayWriteOffCount() {
        String merchantId = fake.getSysUserMerchant().getId();
        AjaxResult ajaxResult = appOrderController.getTodayWriteOffCount(merchantId);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 小程序获取用户待提货订单数
     */
    @Test
    public void getWaitTakeGoodsCount() {
        String userId = fake.getUser().getId();
        AjaxResult ajaxResult = appOrderController.getWaitTakeGoodsCount(userId);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 拼团商品已下订单列表
     */
    @Test
    public void getCollectOrderList() {
        String goodsId = fake.getGoods().getId();
        AjaxResult ajaxResult = appOrderController.getCollectOrderList(goodsId);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 用户拼团订单列表
     */
    @Test
    public void userCollectOrderList() {
        AppCollectOrderDTO dto = new AppCollectOrderDTO();
        dto.setPages(new Pages());
        AjaxResult ajaxResult = appOrderController.userCollectOrderList(dto);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    @Test
    @Ignore
    public void collectOrderDetail() {
        OrderMain orderMain = fake.getFakeOrderMain();
        String orderId = orderMain.getId();
        orderMain.setCollectFlag(1);
        fake.getOrderMainMapper().updateById(orderMain);
        AjaxResult ajaxResult = appOrderController.collectOrderDetail(orderId);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 小程序用户退款订单数
     */
    @Test
    public void refundOrderCount() {
        String userId = fake.getUser().getId();
        AjaxResult ajaxResult = appOrderController.refundOrderCount(userId);
        Assert.assertEquals(200, ajaxResult.getCode());
    }


}
