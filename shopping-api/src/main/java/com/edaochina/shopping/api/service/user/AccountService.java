package com.edaochina.shopping.api.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.user.Account;
import com.edaochina.shopping.domain.vo.user.AccountVO;

import java.util.Optional;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账号服务
 * @since : 14:30
 */
public interface AccountService extends IService<Account> {

    /**
     * 查询带类型的账户
     *
     * @param queryWrapper 查询参数
     * @return 带类型的账户
     */
    Optional<AccountVO> selectOneWithTypes(QueryWrapper<Account> queryWrapper);

    /**
     * 根据用户id和用户类型查找账户
     *
     * @param userId     用户id
     * @param memberType 用户类型
     * @return 用户的账户
     */
    Optional<AccountVO> selectOne(String userId, String memberType);

    /**
     * 添加账户与类型
     *
     * @param account    账户
     * @param memberType 用户类型
     * @param memberId   用户id
     * @return 记录数
     */
    int saveWithType(Account account, int memberType, String memberId);

    /**
     * 查询账户名是否存在
     *
     * @param account 账户名
     * @return 是否存在
     */
    boolean exist(String account);
}
