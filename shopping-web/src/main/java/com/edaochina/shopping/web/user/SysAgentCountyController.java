package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.service.user.AgentCountyInfoService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys/agentCounty")
public class SysAgentCountyController {

    @Resource
    AgentCountyInfoService agentCountyInfoService;

    @RequestMapping("/provinceCityList")
    public AjaxResult provinceCityList() {
        return BaseResult.successResult(agentCountyInfoService.provinceCityList());
    }

    @RequestMapping("/getCountyByCity")
    public AjaxResult getCountyByCity(String cityCode) {
        return BaseResult.successResult(agentCountyInfoService.queryCountyByCity(cityCode));
    }

    @RequestMapping("/provinceCityListByAgentId")
    public AjaxResult provinceCityListByAgentId(String agentId) {
        return BaseResult.successResult(agentCountyInfoService.provinceCityListByAgentId(agentId));
    }
}
