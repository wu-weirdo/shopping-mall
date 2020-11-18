package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.user.SysMerchantCustomerDTO;
import com.edaochina.shopping.domain.dto.user.SysUserUpdateDTO;
import com.edaochina.shopping.domain.dto.user.UserDTO;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.vo.user.MerchantCustomerVo;
import com.edaochina.shopping.domain.vo.user.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 普通用户表  by zzk Mapper 接口
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Repository
public interface UserMapper extends BaseMapper<SysUser> {

    List<UserVO> userList(UserDTO dto);

    List<UserVO> userListByRole(UserDTO dto);

    int updateUserMemberType(@Param("id") String id, @Param("s") String s, @Param("shopName") String shopName, @Param("partenerId") String partenerId);

    int deleteAccount(UserDTO dto);

    int updateAccount(SysUserUpdateDTO dto);

    SysUser getUserById(String id);

    int updateUserInfo(SysUser sysUser);

    int updateUserPhone(@Param("userId") String userId, @Param("phone") String phone);

    int updateUserSessionKey(@Param("openId") String openid, @Param("sessionKey") String session_key);

    int updateNickAndCoumnityInfo(SysUser sysUser);

    int updateUserLawId(@Param("userId") String userId, @Param("lawId") String lawId);

    int updateUserTencetOpenIdByUid(@Param("tencentOpenId") String tencentOpenId, @Param("uid") String uid);

    int updateUserUid(@Param("uid") String uid, @Param("openId") String openId);

    List<SysUser> selectUsers(@Param("startIndex") int n, @Param("num") int i);

    List<SysUser> queryByPhone(String phone);

    List<SysUser> queryHasPhone(@Param("startNum") int i, @Param("num") int n);

    List<UserVO> queryHasByGoodsInShop(UserDTO dto);

    Integer getMemberNumByAgentId(String id);

    List<MerchantCustomerVo> merchantCustomer(SysMerchantCustomerDTO dto);

    Integer userListCount(UserDTO dto);

    Integer userListByRoleCount(UserDTO dto);

    Integer queryHasByGoodsInShopCount(UserDTO dto);
}
