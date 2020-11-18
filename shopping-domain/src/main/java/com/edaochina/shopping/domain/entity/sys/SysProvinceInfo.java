package com.edaochina.shopping.domain.entity.sys;

/**
 * @author jintian
 * @date 2019/3/27 14:01
 */
public class SysProvinceInfo {
    private String name;

    private String id;

    public SysProvinceInfo(String name, String id) {
        this.name = name;
        this.id = id;
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
