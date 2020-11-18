package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.user.AccountDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * AccountController
 *
 * @author wangpenglei
 * @since 2019/9/18 16:03
 */
@RestController
@RequestMapping("/sys/account")
public class SysAccountController {

    private final SysUserFacade userFacade;

    public SysAccountController(SysUserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PutMapping
    public AjaxResultGeneric update(@RequestBody AccountDTO dto) throws NoSuchAlgorithmException {
        return BaseResult.successGenericResult(userFacade.update(dto));
    }

}
