package com.edaochina.shopping.api.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.AccountMapper;
import com.edaochina.shopping.api.dao.user.AccountTypeMapper;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.domain.entity.user.Account;
import com.edaochina.shopping.domain.entity.user.AccountType;
import com.edaochina.shopping.domain.vo.user.AccountVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账号服务实现类
 * @since : 2019/7/26
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private final AccountTypeMapper accountTypeMapper;

    public AccountServiceImpl(AccountTypeMapper accountTypeMapper) {
        this.accountTypeMapper = accountTypeMapper;
    }

    @Override
    public boolean exist(String account) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Account::getUsername, account).ne(Account::getStatus, 2);
        return this.count(queryWrapper) > 0;
    }

    /**
     * 查询带类型的账户
     *
     * @param queryWrapper 查询参数
     * @return 带类型的账户
     */
    @Override
    public Optional<AccountVO> selectOneWithTypes(QueryWrapper<Account> queryWrapper) {
        Account account = baseMapper.selectOne(queryWrapper);
        if (account == null) {
            return Optional.empty();
        }
        AccountVO accountVO = entity2Vo(account);
        BeanUtils.copyProperties(account, accountVO);
        QueryWrapper<AccountType> accountTypeQueryWrapper = new QueryWrapper<>();
        accountTypeQueryWrapper.lambda().eq(AccountType::getAccountId, account.getId());
        accountVO.setTypes(accountTypeMapper.selectList(accountTypeQueryWrapper));
        return Optional.of(accountVO);
    }

    /**
     * 根据用户id和用户类型查找账户
     *
     * @param userId     用户id
     * @param memberType 用户类型
     * @return 用户的账户
     */
    @Override
    public Optional<AccountVO> selectOne(String userId, String memberType) {
        QueryWrapper<AccountType> accountTypeQueryWrapper = new QueryWrapper<>();
        accountTypeQueryWrapper.lambda()
                .eq(AccountType::getMemberId, userId)
                .eq(AccountType::getMemberType, memberType);
        AccountType accountType = accountTypeMapper.selectOne(accountTypeQueryWrapper);
        if (accountType == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(entity2Vo(baseMapper.selectById(accountType.getAccountId())));
    }

    @Override
    public int saveWithType(Account account, int memberType, String memberId) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Account::getUsername, account.getUsername()).ne(Account::getStatus, 2);
        if (this.count(queryWrapper) == 0) {
            this.save(account);
        }
        Account accountDb = this.getOne(queryWrapper);
        AccountType accountType = new AccountType();
        accountType.setMemberType(memberType);
        accountType.setAccountId(accountDb.getId());
        accountType.setMemberId(memberId);
        return accountTypeMapper.insert(accountType);
    }

    private AccountVO entity2Vo(Account account) {
        if (account == null) {
            return null;
        }
        AccountVO accountVO = new AccountVO();
        BeanUtils.copyProperties(account, accountVO);
        QueryWrapper<AccountType> accountTypeQueryWrapper = new QueryWrapper<>();
        accountTypeQueryWrapper.lambda().eq(AccountType::getAccountId, account.getId());
        accountVO.setTypes(accountTypeMapper.selectList(accountTypeQueryWrapper));
        return accountVO;
    }

}
