package order;

import base.TestSupport;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.domain.dto.order.AppMerchantRefundOrderDTO;
import com.edaochina.shopping.domain.dto.trade.RefundApplyDTO;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.web.order.AppRefundApplyController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : app退款控制器测试
 * @since : 2019/7/1 10:00
 */
public class AppRefundApplyControllerTest extends TestSupport {

    @Autowired
    AppRefundApplyController appRefundApplyController;

    @Before
    public void before() {
        fake.fakePayRefundApply();
    }

    private PayRefundApply get() {
        return fake.getFakePayRefundApply();
    }

    /**
     * 小程序用户申请退款
     */
    @Test
    public void apply() {
        RefundApplyDTO dto = new RefundApplyDTO();
        dto.setOrderId(fake.getFakeOrderMain().getId());
        dto.setUserId(fake.getUser().getId());
        dto.setImgs(new ArrayList<>());
        dto.setRefundMethod("test_test");
        dto.setRefundReason("");
        dto.setApplyFee("11");
        dto.setRefundExplain("");
        int code = appRefundApplyController.apply(dto).getCode();
        Assert.assertEquals(code, 200);
        QueryWrapper<PayRefundApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("refund_method", "test_test");
    }

    /**
     * 小程序用户退款详情
     */
    @Test
    public void refundApplyInfo() {
        PayRefundApply payRefundApply = get();
        int code = appRefundApplyController.refundApplyInfo(payRefundApply.getOrderId()).getCode();
        Assert.assertEquals(code, 200);
    }

    /**
     * 小程序用户取消退款申请
     */
    @Test
    public void cancelApply() {
        PayRefundApply payRefundApply = get();
        int code = appRefundApplyController.cancelApply(payRefundApply.getId()).getCode();
        Assert.assertEquals(code, 200);
    }

    /**
     * 小程序商家退款订单
     */
    @Test
    public void merchantRefundOrders() {
        PayRefundApply payRefundApply = get();
        AppMerchantRefundOrderDTO dto = new AppMerchantRefundOrderDTO();
        dto.setShoperId(payRefundApply.getShoperId());
        dto.setParamStatus("0");
        int code = appRefundApplyController.merchantRefundOrders(dto).getCode();
        Assert.assertEquals(code, 200);
    }

    /**
     * 小程序商家退款订单详情
     */
    @Test
    public void merchantRefundOrderDetail() {
        PayRefundApply payRefundApply = get();
        int code = appRefundApplyController.merchantRefundOrderDetail(payRefundApply.getId()).getCode();
        Assert.assertEquals(code, 200);
    }

    /**
     * 小程序商家退款订单数量
     */
    @Test
    public void merchantRefundOrderCount() {
        AppMerchantRefundOrderDTO dto = new AppMerchantRefundOrderDTO();
        PayRefundApply payRefundApply = get();
        dto.setShoperId(payRefundApply.getShoperId());
        dto.setParamStatus("0");
        int code = appRefundApplyController.merchantRefundOrderCount(dto).getCode();
        Assert.assertEquals(code, 200);
    }

    /**
     * 小程序商家拒绝退款
     */
    @Test
    public void refuseRefund() {
        PayRefundApply payRefundApply = get();
        int code = appRefundApplyController.refuseRefund(payRefundApply.getId(), "test").getCode();
        Assert.assertEquals(code, 200);
    }

    /**
     * 小程序商家同意退款
     */
    @Test
    public void agreeRefund() {
        PayRefundApply payRefundApply = get();
        int code = appRefundApplyController.agreeRefund(payRefundApply.getId()).getCode();
        Assert.assertEquals(code, 200);
    }


}
