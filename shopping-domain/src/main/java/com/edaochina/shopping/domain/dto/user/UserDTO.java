package com.edaochina.shopping.domain.dto.user;

import com.edaochina.shopping.domain.base.page.Pages;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author zzk
 * @Date 2019/1/2
 */
public class UserDTO implements Serializable {
    private String id;
    private String nickName;
    private String phone;
    private String community;
    private String origin;
    private String userCityAddress;
    private String isMember;
    private String userId;
    private String roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 成为会员开始时间，会增加一年
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginMemberTime;

    /**
     * 成为会员结束时间，会增加一年
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endMemberTime;

    @NotNull(message = "分页数据不能为空")
    private Pages pages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUserCityAddress() {
        return userCityAddress;
    }

    public void setUserCityAddress(String userCityAddress) {
        this.userCityAddress = userCityAddress;
    }

    public String getIsMember() {
        return isMember;
    }

    public void setIsMember(String isMember) {
        this.isMember = isMember;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getBeginMemberTime() {
        return beginMemberTime;
    }

    public void setBeginMemberTime(LocalDateTime beginMemberTime) {
        if (beginMemberTime != null) {
            this.beginMemberTime = beginMemberTime.plusYears(1);
        }
    }

    public LocalDateTime getEndMemberTime() {
        return endMemberTime;
    }

    public void setEndMemberTime(LocalDateTime endMemberTime) {
        if (endMemberTime != null) {
            this.endMemberTime = endMemberTime.plusYears(1);
        }
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

}
