package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.sys.SysAddress;

/**
 * 高德api服务
 *
 * @author wangpenglei
 * @since 2019/7/29 18:15
 */
public interface AMapService {

    /**
     * 逆地理编码
     *
     * @param longitude 经
     * @param latitude  纬
     * @return 县区信息
     */
    SysAddress geocode(double longitude, double latitude);

}
