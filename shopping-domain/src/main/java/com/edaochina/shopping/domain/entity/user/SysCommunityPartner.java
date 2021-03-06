package com.edaochina.shopping.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SysCommunityPartner implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.account
     *
     * @mbggenerated
     */
    private String account;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.password
     *
     * @mbggenerated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.salt
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.photo
     *
     * @mbggenerated
     */
    private String photo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.identity_no
     *
     * @mbggenerated
     */
    private String identityNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.payment
     *
     * @mbggenerated
     */
    private Integer payment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.status
     *
     * @mbggenerated
     */
    private Integer status;


    private BigDecimal balanceMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.member_time
     *
     * @mbggenerated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime memberTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.create_time
     *
     * @mbggenerated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.invitat_code
     *
     * @mbggenerated
     */
    private String invitatCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.update_time
     *
     * @mbggenerated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_community_partner.pay_money
     *
     * @mbggenerated
     */
    private BigDecimal payMoney;

    /**
     * 直播间code
     */
    private String liveCode;

    public String getLiveCode() {
        return liveCode;
    }

    public void setLiveCode(String liveCode) {
        this.liveCode = liveCode;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_community_partner
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.id
     *
     * @return the value of sys_community_partner.id
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.id
     *
     * @param id the value for sys_community_partner.id
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.account
     *
     * @return the value of sys_community_partner.account
     * @mbggenerated
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.account
     *
     * @param account the value for sys_community_partner.account
     * @mbggenerated
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.phone
     *
     * @return the value of sys_community_partner.phone
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.phone
     *
     * @param phone the value for sys_community_partner.phone
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.password
     *
     * @return the value of sys_community_partner.password
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.password
     *
     * @param password the value for sys_community_partner.password
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.salt
     *
     * @return the value of sys_community_partner.salt
     * @mbggenerated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.salt
     *
     * @param salt the value for sys_community_partner.salt
     * @mbggenerated
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.name
     *
     * @return the value of sys_community_partner.name
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.name
     *
     * @param name the value for sys_community_partner.name
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.photo
     *
     * @return the value of sys_community_partner.photo
     * @mbggenerated
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.photo
     *
     * @param photo the value for sys_community_partner.photo
     * @mbggenerated
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.identity_no
     *
     * @return the value of sys_community_partner.identity_no
     * @mbggenerated
     */
    public String getIdentityNo() {
        return identityNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.identity_no
     *
     * @param identityNo the value for sys_community_partner.identity_no
     * @mbggenerated
     */
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo == null ? null : identityNo.trim();
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

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_community_partner.balance_money
     *
     * @return the value of sys_community_partner.balance_money
     * @mbggenerated
     */
    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_community_partner.balance_money
     *
     * @param balanceMoney the value for sys_community_partner.balance_money
     * @mbggenerated
     */
    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public LocalDateTime getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(LocalDateTime memberTime) {
        this.memberTime = memberTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getInvitatCode() {
        return invitatCode;
    }

    public void setInvitatCode(String invitatCode) {
        this.invitatCode = invitatCode;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_community_partner
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysCommunityPartner other = (SysCommunityPartner) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
                && (this.getIdentityNo() == null ? other.getIdentityNo() == null : this.getIdentityNo().equals(other.getIdentityNo()))
                && (this.getPayment() == null ? other.getPayment() == null : this.getPayment().equals(other.getPayment()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getBalanceMoney() == null ? other.getBalanceMoney() == null : this.getBalanceMoney().equals(other.getBalanceMoney()))
                && (this.getMemberTime() == null ? other.getMemberTime() == null : this.getMemberTime().equals(other.getMemberTime()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getInvitatCode() == null ? other.getInvitatCode() == null : this.getInvitatCode().equals(other.getInvitatCode()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getPayMoney() == null ? other.getPayMoney() == null : this.getPayMoney().equals(other.getPayMoney()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_community_partner
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getIdentityNo() == null) ? 0 : getIdentityNo().hashCode());
        result = prime * result + ((getPayment() == null) ? 0 : getPayment().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBalanceMoney() == null) ? 0 : getBalanceMoney().hashCode());
        result = prime * result + ((getMemberTime() == null) ? 0 : getMemberTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getInvitatCode() == null) ? 0 : getInvitatCode().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getPayMoney() == null) ? 0 : getPayMoney().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_community_partner
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", phone=").append(phone);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", name=").append(name);
        sb.append(", photo=").append(photo);
        sb.append(", identityNo=").append(identityNo);
        sb.append(", payment=").append(payment);
        sb.append(", status=").append(status);
        sb.append(", balanceMoney=").append(balanceMoney);
        sb.append(", memberTime=").append(memberTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", invitatCode=").append(invitatCode);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", payMoney=").append(payMoney);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}