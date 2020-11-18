package com.edaochina.shopping.domain.dto.user;

import com.edaochina.shopping.domain.entity.user.CommunityPartenerCountyInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 注册群社代理商
 *
 * @author jintian
 * @date 2019/7/24 14:18
 */
public class CommuntyPartnerRegisterDTO {

    private String id;

    private String account;

    private String phone;

    private String password;

    private String salt;

    private String name;

    private String photo;

    private String identityNo;

    private Integer payment;

    private Integer status;


    private BigDecimal balanceMoney;

    private BigDecimal payMoney;

    // 合作的区县
    private List<CommunityPartenerCountyInfo> areaInfos;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public List<CommunityPartenerCountyInfo> getAreaInfos() {
        return areaInfos;
    }

    public void setAreaInfos(List<CommunityPartenerCountyInfo> areaInfos) {
        this.areaInfos = areaInfos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
