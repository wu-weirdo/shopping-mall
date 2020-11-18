package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.facade.sys.SysAddressFacade;
import com.edaochina.shopping.api.service.sys.AMapService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.SysAddressDTO;
import com.edaochina.shopping.domain.entity.sys.SysAddress;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 24h
 */
@RestController
@RequestMapping("/app/address")
@Api(tags = "地址相关")
public class AppAddressController {

    @Resource
    SysAddressFacade facade;

    private final AMapService aMapService;

    public AppAddressController(AMapService aMapService) {
        this.aMapService = aMapService;
    }

    /**
     * 查询省份列表
     *
     * @return
     */
    @RequestMapping(value = "/province/list", method = RequestMethod.GET)
    public AjaxResult selectProvinceList() {
        return facade.selectProvinceList();
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

    @GetMapping("/geocode")
    public AjaxResultGeneric<SysAddress> geocode(double longitude, double latitude) {
        return BaseResult.successGenericResult(aMapService.geocode(longitude, latitude));
    }
}
