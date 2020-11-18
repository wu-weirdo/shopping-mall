package com.edaochina.shopping.api.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.sys.SmsVerifyDTO;
import com.edaochina.shopping.domain.dto.user.*;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.vo.user.MerchantCustomerVo;
import com.edaochina.shopping.domain.vo.user.UserVO;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 * 普通用户表  by zzk 服务类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
public interface UserService extends IService<SysUser> {

    /**
     * 小程序端用户注册
     *
     * @param dto
     * @return
     */
    AjaxResult userLogin(UserRegDTO dto);

    PageResult<UserVO> userList(UserDTO dto);

    /**
     * 禁用账户
     *
     * @param dto
     * @return
     */
    int disableAccount(UserDTO dto);

    /**
     * 启用账户
     *
     * @param dto
     * @return
     */
    int enableAccount(UserDTO dto);

    List<SysUser> checkPhone(SmsVerifyDTO smsVerifyDTO);

    void updateUserMemberType(String id, String s, String shopName, String partenerId);

    /**
     * 删除账户
     *
     * @param dto
     * @return
     */
    int deleteAccount(UserDTO dto);

    /**
     * 编辑账户
     *
     * @param sysUserUpdateDTO
     * @return
     */
    int editAccount(SysUserUpdateDTO sysUserUpdateDTO);

    AjaxResult userDetail(String id);

    SysUser queryUserInfo(String openId, String userId);

    AjaxResult updateUserInfo(UpdUserDTO updUserDTO);

    /**
     * 更新用戶法大大id
     *
     * @param userId 用戶id
     * @param lawId  法大大id
     */
    void updateUserLawId(String userId, String lawId);

    int updateUserPhone(UpdUserPhoneDTO dto);

    int updateUserSessionKey(String openid, String session_key);

    int updateUserTencetOpenIdByUid(String tencentOpenId, String uid);

    void updateUsersUid();

    List<SysUser> queryByPhone(String phone);

    List<SysUser> queryHasPhone(int i, int n);

    List<MerchantCustomerVo> merchantCustomer(SysMerchantCustomerDTO dto);

    String getUserPhone(String account, String userType);

    int sendSafeMsg(String account, String userType, String phone);

    int checkUpdatePasswordMsg(String msgCode, String userType, String phone);

    int updatePassword(String account, String password, String userType) throws NoSuchAlgorithmException;
}
