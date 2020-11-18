package com.edaochina.shopping.domain.vo.sys;

import java.io.Serializable;
import java.util.List;

/**
 * @author jintian
 * @date 2019/4/2 16:11
 */
public class SysBackCommunityAreaInfo implements Serializable {

    private static final long serialVersionUID = 100L;

    private String areaCode;

    private String areaName;

    private List<SysBackCommunityAreaInfo> children;

    public SysBackCommunityAreaInfo() {
    }

    public SysBackCommunityAreaInfo(String areaCode, String areaName, List<SysBackCommunityAreaInfo> children) {
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.children = children;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<SysBackCommunityAreaInfo> getChildren() {
        return children;
    }

    public void setChildren(List<SysBackCommunityAreaInfo> children) {
        this.children = children;
    }
}
