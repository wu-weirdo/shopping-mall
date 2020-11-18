package com.edaochina.shopping.domain.vo.sys;

import java.util.List;

/**
 * 代理商代理区县信息查询
 *
 * @author jintian
 * @date 2019/5/21 11:52
 */
public class SysAgentAreaVo {

    /**
     * 未被代理区县名
     */
    private List<String> unAgentAreaName;

    /**
     * 已被代理区县名
     */
    private List<String> agentedName;

    /**
     * 已代理但未付款的区县名
     */
    private List<String> protectedName;


    /**
     * 查询历史
     */
    private List<String> searchHistory;

    private List<String> recommend;

    /**
     * 查询状态
     */
    private Boolean searchStatus = false;

    public List<String> getProtectedName() {
        return protectedName;
    }

    public void setProtectedName(List<String> protectedName) {
        this.protectedName = protectedName;
    }

    public List<String> getUnAgentAreaName() {
        return unAgentAreaName;
    }

    public void setUnAgentAreaName(List<String> unAgentAreaName) {
        this.unAgentAreaName = unAgentAreaName;
    }

    public List<String> getAgentedName() {
        return agentedName;
    }

    public void setAgentedName(List<String> agentedName) {
        this.agentedName = agentedName;
    }

    public List<String> getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(List<String> searchHistory) {
        this.searchHistory = searchHistory;
    }

    public Boolean getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(Boolean searchStatus) {
        this.searchStatus = searchStatus;
    }

    public List<String> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<String> recommend) {
        this.recommend = recommend;
    }
}
