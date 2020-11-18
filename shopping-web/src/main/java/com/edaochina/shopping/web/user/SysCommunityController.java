package com.edaochina.shopping.web.user;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.api.facade.user.CommunityFacade;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.ValidUtil;
import com.edaochina.shopping.domain.dto.user.CommunityListDTO;
import com.edaochina.shopping.domain.dto.user.CommunityUpdDTO;
import com.edaochina.shopping.domain.entity.sys.CommunityDTO;
import com.edaochina.shopping.domain.entity.user.Community;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 社区
 */
@RestController
@RequestMapping("/sys/community")
public class SysCommunityController {

    @Resource
    CommunityFacade communityFacade;

    /**
     * 添加小区
     *
     * @param dto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/add")
    public AjaxResult add(@Valid @RequestBody CommunityDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        return communityFacade.addCommunity(dto);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @PostMapping(value = "/list")
    public AjaxResult list(@Valid @RequestBody CommunityListDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        return communityFacade.getSysList(dto);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping(value = "/listByCountyId")
    public AjaxResult listByCountyId(@RequestBody JSONObject reqJson) {
        if (StringUtils.isBlank(reqJson.getString("countyId"))) {
            return BaseResult.failedResult(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(),
                    ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg(),
                    reqJson.getString("countyId"));
        }
        return communityFacade.getSysListByCountyId(reqJson.getString("countyId"));
    }

    @GetMapping(value = "/detail")
    public AjaxResult detail(String id) {
        return communityFacade.getDetail(id);
    }

    @PostMapping(value = "/delete")
    @OperationLogMark("删除小区")
    public AjaxResult delete(@RequestBody Community community) {
        return communityFacade.delete(community.getId());
    }

    @PostMapping(value = "/update")
    public AjaxResult update(@RequestBody CommunityUpdDTO dto) {
        return communityFacade.updateComm(dto);
    }

    @GetMapping(value = "/all")
    public AjaxResult all() {
        return communityFacade.getSysAll();
    }

    @GetMapping("agent")
    public AjaxResult agent() {
        return communityFacade.getAgentList();
    }
}
