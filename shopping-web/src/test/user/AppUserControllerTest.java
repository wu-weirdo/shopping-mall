package user;

import base.TestSupport;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.user.AppMerchantRegDTO;
import com.edaochina.shopping.domain.dto.user.MemberDTO;
import com.edaochina.shopping.domain.dto.user.UpdUserDTO;
import com.edaochina.shopping.domain.dto.user.UserRegDTO;
import com.edaochina.shopping.web.user.AppUserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : AppUserControllerTest
 * @since : 2019/7/11 14:28
 */
public class AppUserControllerTest extends TestSupport {

    @Autowired
    AppUserController appUserController;
    @Autowired
    SysUserFacade sysUserFacade;

    @Before
    public void before() {
        fake.fakeUser();
    }

    @Test
    public void login() {
        UserRegDTO dto = new UserRegDTO();
        AjaxResult ajaxResult = sysUserFacade.userLogin(dto);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    @Test
    public void getMemberInfo() {
        MemberDTO memberDTO = new MemberDTO();
     /*   AjaxResult ajaxResult = appUserController.getMemberInfo(memberDTO);
        Assert.assertEquals(200, ajaxResult.getCode());*/
    }


    /**
     * 商家注册
     */
    @Test
    public void merchantRegister() {
        AppMerchantRegDTO dto = new AppMerchantRegDTO();
        dto.setAccount("testssss");
        dto.setPassword("123456");
        dto.setIdentityNo("330382199902203212");
        dto.setPhone("15922220101");
        dto.setImage("www.baidu.com");
        dto.setCommunity(fake.getCommunityMapper().selectList(new QueryWrapper<>()).get(0).getId());
        AjaxResult ajaxResult = sysUserFacade.appMerchantRegister(dto);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    @Test
    public void queryUserInfo() {
        JSONObject req = new JSONObject();
        AjaxResult ajaxResult = appUserController.queryUserInfo(req);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    @Test
    public void updateUserInfo() {
        UpdUserDTO updUserDTO = new UpdUserDTO();
        AjaxResult ajaxResult = appUserController.updateUserInfo(updUserDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

}
