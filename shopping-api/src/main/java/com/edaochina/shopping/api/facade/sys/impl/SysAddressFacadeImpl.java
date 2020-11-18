package com.edaochina.shopping.api.facade.sys.impl;

import com.edaochina.shopping.api.facade.sys.SysAddressFacade;
import com.edaochina.shopping.api.service.sys.SysAddressService;
import com.edaochina.shopping.api.service.user.CommunityService;
import com.edaochina.shopping.api.service.user.SysUserAgentService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.SysAddressDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 24h
 */
@Service
public class SysAddressFacadeImpl implements SysAddressFacade {

    @Resource
    SysAddressService sysAddressService;

    @Resource
    SysUserAgentService sysUserAgentService;

    @Resource
    CommunityService communityService;

    @Override
    public AjaxResult selectProvinceListV2() {
        return BaseResult.successResult(sysAddressService.selectProvinceListV2());
    }

    @Override
    public AjaxResult selectProvinceList() {
        return BaseResult.successResult(sysAddressService.selectProvinceList());
    }

    @Override
    public AjaxResult selectProvinceList(SysAddressDTO dto) {
        return BaseResult.successResult(sysAddressService.selectProvinceList(dto));
    }

    @Override
    public AjaxResult selectCityList(SysAddressDTO dto) {
        return BaseResult.successResult(sysAddressService.selectCityList(dto));
    }

    @Override
    public AjaxResult selectCityList(SysAddressDTO dto, String token) {
        return BaseResult.successResult(sysAddressService.selectCityList(dto, token));
    }

    @Override
    public AjaxResult selectAreaList(SysAddressDTO dto) {
        return BaseResult.successResult(sysAddressService.selectAreaList(dto));
    }

    @Override
    public AjaxResult selectAreaList(SysAddressDTO dto, String token) {
        return BaseResult.successResult(sysAddressService.selectAreaList(dto, token));
    }

    @Override
    public AjaxResult selectHasAgentAreaList() {
        return BaseResult.successResult(communityService.selectHasAgentAreaList());
    }

    @Override
    public AjaxResult queryHasCommunityList() {
        return BaseResult.successResult(communityService.queryHasCommunityList());
    }
}
