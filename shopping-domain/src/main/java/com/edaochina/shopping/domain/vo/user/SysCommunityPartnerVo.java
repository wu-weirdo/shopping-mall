package com.edaochina.shopping.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 群社合伙人视图
 *
 * @author jintian
 * @date 2019/7/24 14:46
 */
public class SysCommunityPartnerVo {

    private String id;

    private String account;

    private String phone;

    private String name;

    private String photo;

    private Integer userNum;

    private Integer merchantNum;

    private String invitatCode;

    private String liveCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime memberTime;

    private String identityNo;

    private Integer payment;

    public String getLiveCode() {
        return liveCode;
    }

    public void setLiveCode(String liveCode) {
        this.liveCode = liveCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(Integer merchantNum) {
        this.merchantNum = merchantNum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(LocalDateTime memberTime) {
        this.memberTime = memberTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getInvitatCode() {
        return invitatCode;
    }

    public void setInvitatCode(String invitatCode) {
        this.invitatCode = invitatCode;
    }
}
