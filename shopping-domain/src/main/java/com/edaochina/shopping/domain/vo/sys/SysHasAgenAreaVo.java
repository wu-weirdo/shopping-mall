package com.edaochina.shopping.domain.vo.sys;

import com.edaochina.shopping.domain.entity.sys.SysAddress;

import java.util.List;

/**
 * 系统有代理商区域
 *
 * @author jintian
 * @date 2019/3/24 19:53
 */
public class SysHasAgenAreaVo {

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String countyCode;

    private String countyName;

    public static SysHasAgenAreaVo of(List<SysAddress> addresses) {
        SysHasAgenAreaVo vo = new SysHasAgenAreaVo();
        addresses.forEach(sysAddress -> {
            switch (sysAddress.getLevel()) {
                case "1":
                    vo.setProvinceCode(sysAddress.getCode());
                    vo.setProvinceName(sysAddress.getName());
                    return;
                case "2":
                    vo.setCityCode(sysAddress.getCode());
                    vo.setCityName(sysAddress.getName());
                    return;
                case "3":
                    vo.setCountyCode(sysAddress.getCode());
                    vo.setCountyName(sysAddress.getName());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sysAddress.getLevel());
            }
        });
        return vo;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
