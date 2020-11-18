package com.edaochina.shopping.api.facade.sys;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.sys.SysAddressDTO;

public interface SysAddressFacade {

    AjaxResult selectProvinceListV2();

    AjaxResult selectProvinceList();

    /**
     * 返回代理商对应省份
     *
     * @return
     */
    AjaxResult selectProvinceList(SysAddressDTO dto);

    /**
     * 查询市区列表
     *
     * @param dto
     * @return
     */
    AjaxResult selectCityList(SysAddressDTO dto);

    AjaxResult selectCityList(SysAddressDTO dto, String token);

    /**
     * 查询地区列表
     *
     * @param dto
     * @return
     */
    AjaxResult selectAreaList(SysAddressDTO dto);

    AjaxResult selectAreaList(SysAddressDTO dto, String token);

    AjaxResult selectHasAgentAreaList();

    AjaxResult queryHasCommunityList();
}
