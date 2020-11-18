package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账户类型
 * @since : 2019/7/26
 */
public class AccountType {

    @TableId(type = IdType.AUTO)
    private int id;

    private int accountId;

    private int memberType;

    private String memberId;

    @Override
    public String toString() {
        return "AccountType{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", memberType=" + memberType +
                ", memberId='" + memberId + '\'' +
                '}';
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }
}
