package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.facade.sys.SysAddressFacade;
import com.edaochina.shopping.api.service.sys.AMapService;
import com.edaochina.shopping.api.service.sys.SysAddressService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.SysAddressDTO;
import com.edaochina.shopping.domain.entity.sys.SysAddress;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 24h
 */
@RestController
@RequestMapping("/sys/address")
public class SysAddressController {

    @Resource
    SysAddressFacade facade;

    private final SysAddressService sysAddressService;
    private final AMapService aMapService;

    public SysAddressController(SysAddressService sysAddressService, AMapService aMapService) {
        this.sysAddressService = sysAddressService;
        this.aMapService = aMapService;
    }

    @GetMapping("amap")
    public AjaxResultGeneric<List<String>> selectByAdcode(String adcode, @RequestHeader("token") String token) {
        return BaseResult.successGenericResult(sysAddressService.selectByAdcode(adcode, token));
    }

    @GetMapping("geocode")
    public AjaxResultGeneric<SysAddress> geocode(double longitude, double latitude) {
        return BaseResult.successGenericResult(aMapService.geocode(longitude, latitude));
    }

    @RequestMapping("initAdcode")
    public void initAdCode() {
        sysAddressService.initAdcode();
    }

    /**
     * 查询省份列表
     *
     * @return
     */
    @RequestMapping(value = "/province/list", method = RequestMethod.GET)
    public AjaxResult selectProvinceList(SysAddressDTO dto) {
        return facade.selectProvinceList(dto);
    }

    /**
     * 查询省份列表,代理商只能看到自己的省份
     *
     * @return 省份数组
     */
    @RequestMapping(value = "v2/province/list", method = RequestMethod.GET)
    public AjaxResult selectProvinceList() {
        return facade.selectProvinceListV2();
    }

    /**
     * 查询城市列表,代理商只能看到自己的城市
     *
     * @param dto 参数
     * @return 城市数组
     */
    @RequestMapping(value = "v2/city/list", method = RequestMethod.GET)
    public AjaxResult selectCityListV2(SysAddressDTO dto, @RequestHeader("token") String token) {
        return facade.selectCityList(dto, token);
    }

    /**
     * 查询地区列表,代理商只能看到自己的地区
     *
     * @param dto 参数
     * @return 地区数组
     */
    @RequestMapping(value = "v2/area/list", method = RequestMethod.GET)
    public AjaxResult selectAreaList(SysAddressDTO dto, @RequestHeader("token") String token) {
        return facade.selectAreaList(dto, token);
    }

    /**
     * 查询城市列表
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/city/list", method = RequestMethod.GET)
    public AjaxResult selectCityList(SysAddressDTO dto) {
        return facade.selectCityList(dto);
    }

    /**
     * 查询地区列表
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/area/list", method = RequestMethod.GET)
    public AjaxResult selectAreaList(SysAddressDTO dto) {
        return facade.selectAreaList(dto);
    }

    /**
     * 查询地区列表
     *
     * @return
     */
    @RequestMapping(value = "/area/hasAgentAreaList")
    public AjaxResult selectHasAgentAreaList() {
        return facade.selectHasAgentAreaList();
    }

    @RequestMapping(value = "/area/hasCommunityList")
    public AjaxResult queryHasCommunityList() {
        return facade.queryHasCommunityList();
    }

}
