package com.edaochina.shopping.api.service.trade.impl;

import com.edaochina.shopping.api.dao.trade.WithdrawalSpecialUserMapper;
import com.edaochina.shopping.api.service.trade.WithdrawalSpecialUserService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.domain.entity.trade.WithdrawalSpecialUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jintian
 * @date 2019/9/10 11:33
 */
@Service
public class WithdrawalSpecialUserServiceImpl implements WithdrawalSpecialUserService {

    @Resource
    private WithdrawalSpecialUserMapper mapper;

    @Override
    @Transactional
    public boolean addWithrawalUser(WithdrawalSpecialUser user) {
        WithdrawalSpecialUser user1 = mapper.queryByUser(user.getUserType() + "", user.getUserId());
        if (user1 != null) {
            throw new YidaoException(ReturnData.SPECIAL_USER_EXIT);
        }
        return mapper.insertSelective(user) > 0;
    }

    @Override
    public boolean editWithrawalUser(WithdrawalSpecialUser user) {
        return mapper.updateWithrawalUser(user) > 0;
    }

    @Override
    public boolean delWithrawalUser(Integer id) {
        return mapper.delWithrawalUser(id) > 0;
    }

    @Override
    public List<WithdrawalSpecialUser> queryWithrawalUsers() {
        return mapper.queryWithrawalUsers();
    }

    public WithdrawalSpecialUser queryByUser(String role, String userId) {
        return mapper.queryByUser(role, userId);
    }
}
