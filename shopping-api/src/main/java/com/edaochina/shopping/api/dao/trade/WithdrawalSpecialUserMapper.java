package com.edaochina.shopping.api.dao.trade;

import com.edaochina.shopping.domain.entity.trade.WithdrawalSpecialUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithdrawalSpecialUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdrawal_special_user
     *
     * @mbggenerated
     */
    int insert(WithdrawalSpecialUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdrawal_special_user
     *
     * @mbggenerated
     */
    int insertSelective(WithdrawalSpecialUser record);

    int updateWithrawalUser(WithdrawalSpecialUser user);

    int delWithrawalUser(Integer id);

    List<WithdrawalSpecialUser> queryWithrawalUsers();

    WithdrawalSpecialUser queryByUser(@Param("role") String role, @Param("userId") String userId);
}