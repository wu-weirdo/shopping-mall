package com.edaochina.shopping.domain.vo.user;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangpl
 * @since 2019/7/15
 */
public class CommunitySelectVO {

    private CommunityAppListVO currentCommunity;

    private List<CommunityAppListVO> history = new ArrayList<>();

    private List<CommunityAppListVO> nearby = new ArrayList<>();

    public List<CommunityAppListVO> getHistory() {
        return history;
    }

    public void setHistory(List<CommunityAppListVO> history) {
        this.history = history;
    }

    public List<CommunityAppListVO> getNearby() {
        return nearby;
    }

    public void setNearby(List<CommunityAppListVO> nearby) {
        this.nearby = nearby;
    }

    public CommunityAppListVO getCurrentCommunity() {
        return currentCommunity;
    }

    public void setCurrentCommunity(CommunityAppListVO currentCommunity) {
        this.currentCommunity = currentCommunity;
    }

    @Override
    public String toString() {
        return "CommunitySelectVO{" +
                "history=" + history +
                ", nearby=" + nearby +
                '}';
    }
}
