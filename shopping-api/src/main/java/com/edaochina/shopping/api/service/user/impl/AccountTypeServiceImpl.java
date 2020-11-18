package com.edaochina.shopping.api.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.AccountTypeMapper;
import com.edaochina.shopping.api.service.user.AccountTypeService;
import com.edaochina.shopping.domain.entity.user.AccountType;
import org.springframework.stereotype.Service;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账号服务实现类
 * @since : 2019/7/26
 */
@Service
public class AccountTypeServiceImpl extends ServiceImpl<AccountTypeMapper, AccountType> implements AccountTypeService {
}
