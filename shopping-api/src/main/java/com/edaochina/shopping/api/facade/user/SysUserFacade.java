package com.edaochina.shopping.api.facade.user;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.user.*;
import com.edaochina.shopping.domain.entity.user.SysCommunityPartner;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.vo.user.*;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @Author zzk
 * @Date 2018/12/28
 */
public interface SysUserFacade {
    @Transactional(rollbackFor = Exception.class)
    boolean update(AccountDTO entity) throws NoSuchAlgorithmException;

    AjaxResultGeneric<Integer> merchantCountByCounty(String countyId);

    PageResult<MerchantCustomerVo> merchantCustomer(SysMerchantCustomerDTO dto);

    AjaxResult agentRegister(AgentRegDTO dto);

    LoginVO sysLogin(LoginDTO dto);

    PageResult<AgentVO> agentList(AgentDTO agentDTO);

    AjaxResult agentUpdate(UpdAgentDTO updAgentDTO);

    AjaxResult agentDetail(String id);

    AjaxResult merchantRegister(MerchantRegDTO dto);

    PageResult merchantList(MerchantDTO merchantDTO);

    AjaxResult merchantDetail(String id);

    AjaxResultGeneric<Boolean> league(String id);

    AjaxResultGeneric<PromotionInfoVO> promotionInfo(String id);

    AjaxResultGeneric<Boolean> quitLeague(String id);

    AjaxResult merchantUpdate(UpdMerchantDTO dto);

    AjaxResult deleteMerchant(UpdMerchantDTO dto);

    AjaxResult userLogin(UserRegDTO dto);

    AjaxResult userList(UserDTO dto);

    AjaxResult userDetail(String id);

    AjaxResult updateUserInfo(UpdUserDTO updUserDTO);

    AjaxResult adminRegister(@Valid AdminDTO dto);

    PageResult adminList(AdminDTO adminDTO);

    AjaxResult adminDetail(String id);

    AjaxResult adminUpdate(AdminDTO dto);

    /**
     * 禁用用户账号
     *
     * @param dto
     * @return
     */
    AjaxResult disableAccount(UserDTO dto);

    /**
     * 启用用户账号
     *
     * @param dto
     * @return
     */
    AjaxResult enableAccount(UserDTO dto);

    /**
     * 删除账号
     *
     * @param dto
     * @return
     */
    AjaxResult deleteAccount(UserDTO dto);

    /**
     * 编辑账号
     *
     * @param sysUserUpdateDTO
     * @return
     */
    AjaxResult editAccount(SysUserUpdateDTO sysUserUpdateDTO);

    /**
     * 代理商登陆
     *
     * @param dto
     * @return
     */
    AjaxResult agentLogin(LoginDTO dto);

    /**
     * 商户登陆
     *
     * @param dto
     * @return
     */
    AjaxResult merchantLogin(LoginDTO dto);


    /**
     * 获取代理商详情
     *
     * @param dto
     * @return
     */
    AjaxResult getAgentInfo(AgentDTO dto);

    /**
     * 获取商家详情
     *
     * @param dto
     * @return
     */
    AjaxResult getMerchantInfo(MerchantDTO dto);


    AjaxResultGeneric<UserVO> getMemberInfo(MemberDTO memberDTO);

    AjaxResult getMerchantDetail(MemberDTO dto);

    AjaxResult appMerchantRegister(AppMerchantRegDTO dto);

    AjaxResult queryUserInfo(String userId, String openId);

    AjaxResult updateUserPhone(UpdUserPhoneDTO dto);

    int updateUserSessionKey(Map<String, String> mapObj);

    String export(UserDTO dto);

    String merchantExport(MerchantDTO dto);

    boolean communtyPartnerRegister(CommuntyPartnerRegisterDTO dto);

    PageResult<SysCommunityPartnerVo> communtyPartnerList(CommuntyPartnerDTO dto);

    SysCommunityPartnerDetailVO communtyPartnerDetail(String id);

    boolean communtyPartnerUpdate(CommuntyPartnerRegisterDTO dto);

    AjaxResult partenerLogin(LoginDTO dto);

    SysCommunityPartner getPartenerInfo(String partenerId);

    SysUser appLogin(String phone, String code);

    int updatePassword(String account, String password, String userType);
}
