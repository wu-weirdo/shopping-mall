package com.edaochina.shopping.domain.entity.sys;

import java.io.Serializable;

/**
 * @author jintian
 * @date 2019/3/27 14:17
 */
public class SysCountyInfo implements Serializable {

    private static final long serialVersionUID = 1002L;

    private String city;

    private String name;

    private String id;

    public SysCountyInfo(String city, String name, String id) {
        this.city = city;
        this.name = name;
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
