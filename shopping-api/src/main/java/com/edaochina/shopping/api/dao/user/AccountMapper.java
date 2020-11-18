package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.user.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账户dao
 * @since : 11:07
 */
@Mapper
@Repository
public interface AccountMapper extends BaseMapper<Account> {
}
