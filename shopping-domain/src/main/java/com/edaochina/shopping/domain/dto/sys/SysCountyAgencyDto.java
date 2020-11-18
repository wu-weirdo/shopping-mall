package com.edaochina.shopping.domain.dto.sys;

/**
 * @author jintian
 * @date 2019/5/21 15:30
 */
public class SysCountyAgencyDto {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 查询信息
     */
    private String search;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
