package com.edaochina.shopping.api.service.sys.impl;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.api.service.sys.AMapService;
import com.edaochina.shopping.api.service.sys.SysAddressService;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.http.HttpClientUtilsTool;
import com.edaochina.shopping.domain.entity.sys.SysAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 高德api服务实现类
 *
 * @author wangpenglei
 * @since 2019/7/29 18:27
 */
@Service
public class AMapServiceImpl implements AMapService {

    private final SysAddressService sysAddressService;
    @Value("${amap.key}")
    private String amapKey;

    public AMapServiceImpl(SysAddressService sysAddressService) {
        this.sysAddressService = sysAddressService;
    }

    /**
     * 逆地理编码
     *
     * @param longitude 经
     * @param latitude  纬
     * @return 区县
     */
    @Override
    public SysAddress geocode(double longitude, double latitude) {
        String url = "https://restapi.amap.com/v3/geocode/regeo?key=" + amapKey + "&location=" + longitude + "," + latitude;
        JSONObject jsonObject = HttpClientUtilsTool.httpGet(url);
        if ("1".equals(jsonObject.getString("status"))) {
            String adcode = jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent").getString("adcode");
            SysAddress sysAddress = sysAddressService.selectOneByAdcode(adcode);
            if (sysAddress == null) {
                sysAddress = new SysAddress();
            }
            return sysAddress;
        } else {
            throw new YidaoException(jsonObject.getString("info"));
        }
    }

}
