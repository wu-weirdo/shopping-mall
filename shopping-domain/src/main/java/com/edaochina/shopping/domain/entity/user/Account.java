package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账户
 * @since : 2019/7/26
 */
public class Account {

    @TableId(type = IdType.AUTO)
    private int id;

    private String username;

    private String password;

    private String salt;

    private String phone;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String openId;

    private Integer status;

    private String nickname;

    public static Account copy2Account(SysUserMerchant sysUserMerchant) {
        Account account = new Account();
        account.setNickname(sysUserMerchant.getName());
        account.setPassword(sysUserMerchant.getPassword());
        account.setSalt(sysUserMerchant.getSalt());
        account.setPhone(StringUtils.isEmpty(sysUserMerchant.getPhone()) ? null : sysUserMerchant.getPhone());
        account.setUsername(sysUserMerchant.getAccount());
        account.setOpenId(sysUserMerchant.getOpenid());
        return account;
    }

    public static Account copy2Account(SysUserAdmin sysUserAdmin) {
        return getAccount(sysUserAdmin.getName(), sysUserAdmin.getPassword(), sysUserAdmin.getSalt(),
                sysUserAdmin.getPhone(), sysUserAdmin.getAccount());
    }

    public static Account copy2Account(SysUserAgent sysUserAgent) {
        Account account = getAccount(sysUserAgent.getName(), sysUserAgent.getPassword(), sysUserAgent.getSalt(),
                sysUserAgent.getPhone(), sysUserAgent.getAccount());
        account.setOpenId(sysUserAgent.getOpenid());
        return account;
    }

    public static Account copy2Account(SysCommunityPartner sysCommunityPartner) {
        return getAccount(sysCommunityPartner.getName(), sysCommunityPartner.getPassword(),
                sysCommunityPartner.getSalt(), sysCommunityPartner.getPhone(), sysCommunityPartner.getAccount());
    }

    private static Account getAccount(String name, String password, String salt, String phone, String account2) {
        Account account = new Account();
        account.setNickname(name);
        account.setPassword(password);
        account.setSalt(salt);
        account.setPhone(StringUtils.isEmpty(phone) ? null : phone);
        account.setUsername(account2);
        return account;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", openId='" + openId + '\'' +
                ", status=" + status +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
