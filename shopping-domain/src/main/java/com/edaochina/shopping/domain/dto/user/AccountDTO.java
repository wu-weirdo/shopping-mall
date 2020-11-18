package com.edaochina.shopping.domain.dto.user;

import com.edaochina.shopping.domain.entity.user.Account;

/**
 * AccountDTO
 *
 * @author wangpenglei
 * @since 2019/9/18 16:06
 */
public class AccountDTO extends Account {

    private String oldPassword;

    @Override
    public String toString() {
        return "AccountDTO{" +
                "oldPassword='" + oldPassword + '\'' +
                "} " + super.toString();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
