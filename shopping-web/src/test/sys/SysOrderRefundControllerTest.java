package sys;

import base.TestSupport;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.PayRefundApplyStatus;
import com.edaochina.shopping.domain.dto.sys.SysOrderRefundDTO;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.web.order.SysOrderRefundController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 合同退款控制器测试
 * @since : 2019/7/1 9:47
 */
public class SysOrderRefundControllerTest extends TestSupport {

    @Autowired
    SysOrderRefundController sysOrderRefundController;

    @Before
    public void before() {
        fake.fakePayRefundApply();
    }

    private PayRefundApply get() {
        return fake.getFakePayRefundApply();
    }

    @Test
    public void list() {
        SysOrderRefundDTO dto = new SysOrderRefundDTO();
        Pages pages = new Pages();
        int code = sysOrderRefundController.list(dto, pages).getCode();
        Assert.assertEquals(200, code);
    }

    @Test
    public void agree() throws UnsupportedEncodingException {
        JWTBean jwtBean = new JWTBean();
        jwtBean.setId(fake.getUser().getId());
        String token = JWTUtil.createToken(jwtBean, 100);
        SysOrderRefundDTO dto = new SysOrderRefundDTO();
        dto.setId(get().getId());

        get().setHandleStatus(PayRefundApplyStatus.HandleStatus.PENDING);
        get().setRefundStatus(PayRefundApplyStatus.RefundStatus.PROCESSING);
        get().setCollectMerchantStatus(10);
        get().setCollectUserStatus(10);
        get().setApplyRefundStatus(10);
        get().setApplyRefundTime(LocalDateTime.now().plusDays(-3));
        fake.getPayRefundApplyMapper().updateById(get());

        AjaxResult ajaxResult = sysOrderRefundController.agree(dto, token);
        Assert.assertEquals(200, ajaxResult.getCode());
        Assert.assertEquals(1, ajaxResult.getResults());
    }

    @Test
    public void reject() throws UnsupportedEncodingException {
        JWTBean jwtBean = new JWTBean();
        jwtBean.setId(fake.getUser().getId());
        String token = JWTUtil.createToken(jwtBean, 100);
        SysOrderRefundDTO dto = new SysOrderRefundDTO();
        dto.setId(get().getId());

        get().setHandleStatus(PayRefundApplyStatus.HandleStatus.PENDING);
        get().setRefundStatus(PayRefundApplyStatus.RefundStatus.PROCESSING);
        get().setCollectMerchantStatus(10);
        get().setCollectUserStatus(10);
        get().setApplyRefundStatus(10);
        get().setApplyRefundTime(LocalDateTime.now().plusDays(-3));
        fake.getPayRefundApplyMapper().updateById(get());

        AjaxResult ajaxResult = sysOrderRefundController.reject(dto, token);
        Assert.assertEquals(200, ajaxResult.getCode());
        Assert.assertEquals(1, ajaxResult.getResults());
    }

    @Test
    public void collectUser() {
        int code = sysOrderRefundController.collectUser(get().getId()).getCode();
        Assert.assertEquals(200, code);
    }

    @Test
    public void collectMerchant() {
        int code = sysOrderRefundController.collectMerchant(get().getId()).getCode();
        Assert.assertEquals(200, code);
    }


    @Test
    public void selectOneById() {
        int code = sysOrderRefundController.selectOneById(get().getId()).getCode();
        Assert.assertEquals(200, code);
    }

}
