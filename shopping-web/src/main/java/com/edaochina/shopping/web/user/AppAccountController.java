package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * AccountController
 *
 * @author wangpenglei
 * @since 2019/9/18 16:03
 */
@RestController
@RequestMapping("/app/account")
public class AppAccountController {

    private final SysUserFacade userFacade;

    public AppAccountController(SysUserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("updatePassword")
    public AjaxResultGeneric updatePassword(String account, String password, String userType) {
        return BaseResult.successGenericResult(userFacade.updatePassword(account, password, userType));
    }

}
