package com.edaochina.shopping.domain.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.edaochina.shopping.domain.entity.user.Account;
import com.edaochina.shopping.domain.entity.user.AccountType;

import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账号视图类
 * @since : 2019/7/26
 */
public class AccountVO extends Account {

    @TableField(exist = false)
    private List<AccountType> types;

    public boolean hasType(int type) {
        return types.stream().anyMatch(accountType -> accountType.getMemberType() == type);
    }

    @Override
    public String toString() {
        return "AccountVO{" +
                "types=" + types +
                "} " + super.toString();
    }

    public List<AccountType> getTypes() {
        return types;
    }

    public void setTypes(List<AccountType> types) {
        this.types = types;
    }
}
