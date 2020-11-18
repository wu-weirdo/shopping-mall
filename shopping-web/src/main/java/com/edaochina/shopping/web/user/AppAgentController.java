package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.user.AgentDTO;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/agent")
@Api(tags = "区县代理商")
public class AppAgentController {

    @Resource
    SysUserFacade sysUserFacade;

    /**
     * 小程序代理登陆
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @OperationLogMark("小程序代理登陆")
    public AjaxResult login(@RequestBody LoginDTO dto) {
        dto.setAccount(dto.getAccount().trim());
        dto.setPassword(dto.getPassword().trim());
        return sysUserFacade.agentLogin(dto);
    }

    /**
     * 小程序获取代理商详情
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public AjaxResult info(@RequestBody AgentDTO dto) {
        return sysUserFacade.getAgentInfo(dto);
    }
}
