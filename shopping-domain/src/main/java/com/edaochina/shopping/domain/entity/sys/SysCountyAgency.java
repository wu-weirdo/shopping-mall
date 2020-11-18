package com.edaochina.shopping.domain.entity.sys;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 区县代理查询表
 * </p>
 *
 * @since 2019-05-20
 */
public class SysCountyAgency extends Model<SysCountyAgency> {

    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户类型(1.管理员，2.商家，3.代理商)
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 查询信息
     */
    private String search;

    /**
     * 查询结果状态(0.无结果，1.有结果)
     */
    private Integer searchResultStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getSearchResultStatus() {
        return searchResultStatus;
    }

    public void setSearchResultStatus(Integer searchResultStatus) {
        this.searchResultStatus = searchResultStatus;
    }
}
