package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.user.MerchantDTO;
import com.edaochina.shopping.domain.dto.user.UpdMerchantDTO;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.user.MerchantVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商户表 by 张志侃 Mapper 接口
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Repository
public interface SysUserMerchantMapper extends BaseMapper<SysUserMerchant> {

    int queryMemberNumByCountId(@Param("countyId") String countyId);

    int updateMerchant(UpdMerchantDTO dto);

    List<SysUserMerchant> queryByPhone(@Param("phone") String phone);

    List<SysUserMerchant> queryNoQrCode();

    int updateUserLawId(@Param("userId") String userId, @Param("lawConsumerId") String lawConsumerId);

    int updateOpenid(@Param("account") String account, @Param("openid") String openid);

    String queryOpenid(@Param("id") String id);

    List<MerchantVO> merchantList(MerchantDTO merchantDTO);

    int merchantCount(MerchantDTO merchantDTO);

    List<SysUserMerchant> queryByCommunity(@Param("community") String community);

    int updateBalanceMoney(@Param("userId") String userId, @Param("money") double v);

    int addProfitMoney(@Param("shoperId") String shoperId, @Param("income") double income);

    Integer getMemberNumByAgentId(String id);

    @Select("select * from sys_user_merchant where account = #{account} and status = 0")
    SysUserMerchant queryByAccount(String account);
}
