package com.edaochina.shopping.domain.entity.sys;

import java.io.Serializable;

/**
 * @author jintian
 * @date 2019/3/27 14:15
 */
public class SysCityInfo implements Serializable {

    private static final long serialVersionUID = 1001L;

    private String province;

    private String name;

    private String id;

    public SysCityInfo(String province, String name, String id) {
        this.province = province;
        this.name = name;
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
