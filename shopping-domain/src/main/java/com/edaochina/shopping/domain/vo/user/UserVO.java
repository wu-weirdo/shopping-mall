package com.edaochina.shopping.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 普通用户表  by zzk
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
public class UserVO implements Serializable {

    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 0 未知 1 男 2 女
     */
    private Integer gender;

    /**
     * 绑定小区
     */
    private String bindCommunity;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 来源商家
     */
    private String origin;

    /**
     * 1 正常 2禁用
     */
    private Integer status;

    /**
     * 会员类型(0非会员，1会员）
     */
    private Integer memberType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime memberTime;

    /**
     * 成为会员日期，等于会员有效期减一年
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinMemberTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String userCityAddress;

    /**
     * 已优惠金额
     */
    private BigDecimal discountMoney;

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public void setJoinMemberTime(LocalDateTime joinMemberTime) {
        this.joinMemberTime = joinMemberTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBindCommunity() {
        return bindCommunity;
    }

    public void setBindCommunity(String bindCommunity) {
        this.bindCommunity = bindCommunity;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public LocalDateTime getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(LocalDateTime memberTime) {
        this.joinMemberTime = memberTime.plusYears(-1);
        this.memberTime = memberTime;
    }

    public LocalDateTime getJoinMemberTime() {
        return joinMemberTime;
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

    public String getUserCityAddress() {
        return userCityAddress;
    }

    public void setUserCityAddress(String userCityAddress) {
        this.userCityAddress = userCityAddress;
    }
}
