package order;

import base.TestSupport;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.goods.DeleteOrdersDTO;
import com.edaochina.shopping.domain.dto.order.QuerySysOrderDTO;
import com.edaochina.shopping.domain.dto.order.QuerySysWriteOffOrderDTO;
import com.edaochina.shopping.domain.dto.order.SysOrderDto;
import com.edaochina.shopping.domain.dto.order.WriteOffDTO;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.web.order.SysOrderController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SysOrderController测试
 *
 * @author wangpenglei
 * @since 2019/7/31 15:52
 */
public class SysOrderControllerTest extends TestSupport {

    @Autowired
    SysOrderController sysOrderController;

    @Before
    public void before() {
        fake.fakeOrderMain();
    }

    @Test
    public void apply() {
        PayRefundApply payRefundApply = new PayRefundApply();
        payRefundApply.setOrderId(fake.getFakeOrderMain().getId());
        payRefundApply.setRefundExplain("test");
        AjaxResult ajaxResult = sysOrderController.apply(payRefundApply);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 批量删除订单
     */
    @Test
    public void deleteOrders() {
        DeleteOrdersDTO deleteOrdersDTO = new DeleteOrdersDTO();
        deleteOrdersDTO.setIds(fake.getFakeOrderMain().getId());
        AjaxResult ajaxResult = sysOrderController.deleteOrders(deleteOrdersDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }


    /**
     * 核销
     */
    @Test
    public void writeOff() {
        WriteOffDTO writeOffDTO = new WriteOffDTO();
        writeOffDTO.setId(fake.getFakeOrderMain().getId());
        AjaxResult ajaxResult = sysOrderController.writeOff(writeOffDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 查询订单列表
     */
    @Test
    public void getOrderList() {
        SysOrderDto querySysOrderDTO = new SysOrderDto();
        querySysOrderDTO.setPages(new Pages());
        AjaxResult ajaxResult = sysOrderController.getOrderList(querySysOrderDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 查询获取核销列表
     */
    @Test
    public void getWriteOffList() {
        QuerySysWriteOffOrderDTO querySysOrderDTO = new QuerySysWriteOffOrderDTO();
        querySysOrderDTO.setPages(new Pages());
        AjaxResult ajaxResult = sysOrderController.getWriteOffList(querySysOrderDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 查询订单详情
     */
    @Test
    public void getOrderDetail() {
        QuerySysOrderDTO querySysOrderDTO = new QuerySysOrderDTO();
        querySysOrderDTO.setId(fake.getFakeOrderMain().getId());
        AjaxResult ajaxResult = sysOrderController.getOrderDetail(querySysOrderDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

}
