package com.edaochina.shopping.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.sys.SysAddressDTO;
import com.edaochina.shopping.domain.entity.sys.SysAddress;

import java.util.List;

/**
 * <p>
 * 地址数据表 by张志侃 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-11
 */
public interface SysAddressService extends IService<SysAddress> {

    /**
     * 查询省份列表
     *
     * @return
     */
    List<SysAddress> selectProvinceList();

    List<SysAddress> selectProvinceList(SysAddressDTO dto);

    List<SysAddress> selectProvinceListV2();

    /**
     * 查询市区列表
     *
     * @param dto
     * @return
     */
    List<SysAddress> selectCityList(SysAddressDTO dto);

    List<SysAddress> selectCityList(SysAddressDTO dto, String token);

    /**
     * 查询地区列表
     *
     * @param dto
     * @return
     */
    List<SysAddress> selectAreaList(SysAddressDTO dto);

    List<SysAddress> selectAreaList(SysAddressDTO dto, String token);

    List<String> selectByAdcode(String adcode, String token);

    SysAddress selectOneByAdcode(String adcode);

    void initAdcode();
}
