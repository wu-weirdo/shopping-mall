package com.edaochina.shopping.api.service.trade;

import com.edaochina.shopping.domain.entity.trade.WithdrawalSpecialUser;

import java.util.List;

/**
 * @author jintian
 * @date 2019/9/10 11:33
 */
public interface WithdrawalSpecialUserService {
    boolean addWithrawalUser(WithdrawalSpecialUser user);

    boolean editWithrawalUser(WithdrawalSpecialUser user);

    boolean delWithrawalUser(Integer id);

    List<WithdrawalSpecialUser> queryWithrawalUsers();
}
