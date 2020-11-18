package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 小程序群社合伙人
 *
 * @author jintian
 * @date 2019/7/26 13:55
 */
@RestController
@RequestMapping("app/partener")
@Api(tags = "群社合伙人")
public class AppPartenerController {
    @Resource
    SysUserFacade sysUserFacade;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @OperationLogMark("小程序群社合伙人登陆")
    public AjaxResult login(@RequestBody LoginDTO dto) {
        dto.setAccount(dto.getAccount().trim());
        dto.setPassword(dto.getPassword().trim());
        return sysUserFacade.partenerLogin(dto);
    }

    // 获取基本信息
    @RequestMapping(value = "info")
    public AjaxResult getInfo(String partenerId) {
        return BaseResult.successResult(sysUserFacade.getPartenerInfo(partenerId));
    }

}
