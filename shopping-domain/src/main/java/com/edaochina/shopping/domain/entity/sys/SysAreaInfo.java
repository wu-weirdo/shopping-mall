package com.edaochina.shopping.domain.entity.sys;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author jintian
 * @date 2019/3/27 13:57
 */
public class SysAreaInfo implements Serializable {

    private static final long serialVersionUID = 1000L;

    private SysProvinceInfo[] provinces;


    private Map<String, List<SysCityInfo>> citys;


    private Map<String, List<SysCountyInfo>> areas;

    public SysProvinceInfo[] getProvinces() {
        return provinces;
    }

    public void setProvinces(SysProvinceInfo[] provinces) {
        this.provinces = provinces;
    }

    public Map<String, List<SysCityInfo>> getCitys() {
        return citys;
    }

    public void setCitys(Map<String, List<SysCityInfo>> citys) {
        this.citys = citys;
    }

    public Map<String, List<SysCountyInfo>> getAreas() {
        return areas;
    }

    public void setAreas(Map<String, List<SysCountyInfo>> areas) {
        this.areas = areas;
    }
}
