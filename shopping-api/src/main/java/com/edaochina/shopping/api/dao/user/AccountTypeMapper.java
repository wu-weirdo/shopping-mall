package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.user.AccountType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账户类型dado
 * @since : 2019/7/26
 */
@Mapper
@Repository
public interface AccountTypeMapper extends BaseMapper<AccountType> {
}
