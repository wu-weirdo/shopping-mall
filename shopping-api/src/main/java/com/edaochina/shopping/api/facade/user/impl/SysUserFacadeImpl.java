package com.edaochina.shopping.api.facade.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.trade.TradeDetailShareProfitMapper;
import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.api.service.goods.SysGoodsService;
import com.edaochina.shopping.api.service.sys.SysPermissionService;
import com.edaochina.shopping.api.service.user.*;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.SignUtils;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.excel.ExportExcelUtil;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.constants.SmsConstant;
import com.edaochina.shopping.domain.dto.user.*;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.trade.TradeDetailShareProfit;
import com.edaochina.shopping.domain.entity.user.*;
import com.edaochina.shopping.domain.vo.sys.SysPermissionVO;
import com.edaochina.shopping.domain.vo.user.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * @Author zzk
 * @Date 2018/12/28
 */
@Service
public class SysUserFacadeImpl implements SysUserFacade {

    private final AccountService accountService;

    private final CommunityService communityService;
    @Resource
    SysUserAgentService sysUserAgentService;
    @Resource
    SysUserMerchantService sysUserMerchantService;
    @Resource
    UserService userService;
    @Resource
    SysUserAdminService sysUserAdminService;
    @Resource
    SysCommunityPartnerService sysCommunityPartnerService;

    private final SysPermissionService sysPermissionService;
    private final SysGoodsService goodsService;
    private final TradeDetailShareProfitMapper shareProfitMapper;
    private Logger logger = LoggerFactory.getLogger(SysUserFacadeImpl.class);

    public SysUserFacadeImpl(CommunityService communityService, SysPermissionService sysPermissionService,
                             AccountService accountService, SysGoodsService goodsService, TradeDetailShareProfitMapper shareProfitMapper) {
        this.communityService = communityService;
        this.sysPermissionService = sysPermissionService;
        this.accountService = accountService;
        this.goodsService = goodsService;
        this.shareProfitMapper = shareProfitMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(AccountDTO entity) throws NoSuchAlgorithmException {
        logger.info(String.valueOf(entity));
        JWTBean bean = JWTThreadLocalHelper.get();
        switch (bean.getRole()) {
            case "1":
                SysUserAdmin admin = sysUserAdminService.getById(bean.getId());
                admin.setPhone(entity.getPhone());
                checkPassword(entity.getOldPassword(), admin.getPassword(), entity.getPassword(), admin.getSalt());
                admin.setPassword(SignUtils.generateSign(entity.getPassword() + admin.getSalt()));
                sysUserAdminService.updateById(admin);
                break;
            case "3":
                SysUserMerchant merchant = sysUserMerchantService.getById(bean.getId());
                merchant.setPhone(entity.getPhone());
                checkPassword(entity.getOldPassword(), merchant.getPassword(), entity.getPassword(), merchant.getSalt());
                merchant.setPassword(SignUtils.generateSign(entity.getPassword() + merchant.getSalt()));
                sysUserMerchantService.updateById(merchant);
                break;
            case "4":
                SysUserAgent agent = sysUserAgentService.getById(bean.getId());
                agent.setPhone(entity.getPhone());
                checkPassword(entity.getOldPassword(), agent.getPassword(), entity.getPassword(), agent.getSalt());
                agent.setPassword(SignUtils.generateSign(entity.getPassword() + agent.getSalt()));
                sysUserAgentService.updateById(agent);
                break;
            case "6":
                SysCommunityPartnerDetailVO communityPartnerDetailVO = sysCommunityPartnerService.queryDetal(bean.getId());
                CommuntyPartnerRegisterDTO dto = new CommuntyPartnerRegisterDTO();
                dto.setPhone(entity.getPhone());
                checkPassword(entity.getOldPassword(), communityPartnerDetailVO.getPassword(), entity.getPassword(), communityPartnerDetailVO.getSalt());
                dto.setPassword(SignUtils.generateSign(entity.getPassword() + communityPartnerDetailVO.getSalt()));
                sysCommunityPartnerService.update(dto);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + bean.getRole());
        }
        RedisTool.delete(bean.getTokenKey());
        return accountService.updateById(entity);
    }

    private void checkPassword(String oldPassword, String password, String newPassword, String salt) throws NoSuchAlgorithmException {
        if (!password.equals(SignUtils.generateSign(oldPassword + salt))) {
            throw new YidaoException("旧密码错误！");
        }
        if (password.equals(SignUtils.generateSign(newPassword + salt))) {
            throw new YidaoException("旧密码与新密码重复！");
        }
        if (newPassword.length() < 6 || newPassword.length() > 10) {
            throw new YidaoException("请输入6-10位字符的密码！");
        }
    }

    @Override
    public AjaxResultGeneric<Integer> merchantCountByCounty(String countyId) {
        QueryWrapper<SysUserMerchant> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserMerchant::getStatus, 0).eq(SysUserMerchant::getCountyId, countyId);
        return BaseResult.successGenericResult(sysUserMerchantService.count(wrapper));
    }

    @Override
    public PageResult<MerchantCustomerVo> merchantCustomer(SysMerchantCustomerDTO dto) {
        JWTBean bean = JWTThreadLocalHelper.get();
        if (bean.isMerchant()) {
       /*     String key = "merchantCustomer:" + bean.getId();
            return CacheService.get(key, () -> {*/
            dto.setMerchantId(bean.getId());
            PageHelperUtils.setPageHelper(dto.getPages());
            List<MerchantCustomerVo> merchantCustomerVos = userService.merchantCustomer(dto);
            return PageHelperUtils.parseToPageResult(merchantCustomerVos);
            /* }, redisTemplate, 30);*/
        }
        return PageHelperUtils.parseToPageResult(new ArrayList<>());
    }

    @Override
    public AjaxResult agentRegister(AgentRegDTO dto) {
        return sysUserAgentService.agentRegister(dto);
    }


    @Override
    public PageResult<AgentVO> agentList(AgentDTO agentDTO) {
        return sysUserAgentService.agentList(agentDTO);
    }

    @Override
    public AjaxResult agentUpdate(UpdAgentDTO updAgentDTO) {
        return sysUserAgentService.agentUpdate(updAgentDTO);
    }

    @Override
    public AjaxResult agentDetail(String id) {
        return sysUserAgentService.agentDetail(id);
    }

    @Override
    public AjaxResult merchantRegister(MerchantRegDTO dto) {
        return sysUserMerchantService.merchantRegister(dto);
    }

    @Override
    public PageResult merchantList(MerchantDTO merchantDTO) {
        return sysUserMerchantService.merchantList(merchantDTO);
    }

    @Override
    public AjaxResult merchantDetail(String id) {
        return sysUserMerchantService.merchantDetail(id);
    }

    @Override
    public AjaxResultGeneric<Boolean> league(String id) {
        return sysUserMerchantService.league(id);
    }

    @Override
    public AjaxResultGeneric<PromotionInfoVO> promotionInfo(String id) {
        PromotionInfoVO promotionInfoVO = new PromotionInfoVO();

        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(SysUser::getOrigin, id);
        promotionInfoVO.setUserCount(userService.count(userQueryWrapper));

        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.lambda().eq(Goods::getShopId, id).eq(Goods::getDeleteFlag, 10);
        promotionInfoVO.setGoodsCount(goodsService.count(goodsQueryWrapper));

        QueryWrapper<TradeDetailShareProfit> shareProfitQueryWrapper = new QueryWrapper<>();
        shareProfitQueryWrapper.lambda().eq(TradeDetailShareProfit::getUserType, 3).eq(TradeDetailShareProfit::getUserId, id);
        promotionInfoVO.setPromotionCount(shareProfitMapper.selectCount(shareProfitQueryWrapper));

        promotionInfoVO.setPromotionPriceSum(shareProfitMapper.promotionPriceSum(3, id));
        return BaseResult.successGenericResult(promotionInfoVO);
    }

    @Override
    public AjaxResultGeneric<Boolean> quitLeague(String id) {
        return sysUserMerchantService.quitLeague(id);
    }

    @Override
    public AjaxResult merchantUpdate(UpdMerchantDTO dto) {
        dto.setCommunitys(dto.getCommunity().split(","));
        return sysUserMerchantService.merchantUpdate(dto);
    }

    @Override
    public AjaxResult deleteMerchant(UpdMerchantDTO dto) {
        return sysUserMerchantService.deleteMerchant(dto);
    }

    @Override
    public AjaxResult userLogin(UserRegDTO dto) {
        return userService.userLogin(dto);
    }

    @Override
    public AjaxResult userList(UserDTO dto) {
        return BaseResult.successResult(userService.userList(dto));
    }

    @Override
    public AjaxResult userDetail(String id) {
        return userService.userDetail(id);
    }

    @Override
    public AjaxResult updateUserInfo(UpdUserDTO updUserDTO) {
        return userService.updateUserInfo(updUserDTO);
    }

    @Override
    public AjaxResult adminRegister(AdminDTO dto) {
        return sysUserAdminService.adminRegister(dto);
    }

    @Override
    public PageResult adminList(AdminDTO adminDTO) {
        return sysUserAdminService.adminList(adminDTO);
    }

    @Override
    public AjaxResult adminDetail(String id) {
        return sysUserAdminService.adminDetail(id);
    }

    @Override
    public AjaxResult adminUpdate(AdminDTO dto) {
        return sysUserAdminService.adminUpdate(dto);
    }

    /**
     * 禁用账号
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult disableAccount(UserDTO dto) {
        return BaseResult.successResult(userService.disableAccount(dto));
    }

    /**
     * 启用账号
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult enableAccount(UserDTO dto) {
        return BaseResult.successResult(userService.enableAccount(dto));
    }

    /**
     * 删除账号
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult deleteAccount(UserDTO dto) {
        return BaseResult.successResult(userService.deleteAccount(dto));
    }

    /**
     * 编辑账号
     *
     * @param sysUserUpdateDTO
     * @return
     */
    @Override
    public AjaxResult editAccount(SysUserUpdateDTO sysUserUpdateDTO) {
        return BaseResult.successResult(userService.editAccount(sysUserUpdateDTO));
    }

    @Override
    public AjaxResult agentLogin(LoginDTO dto) {
        return BaseResult.successResult(sysUserAgentService.agentCheck(dto));
    }

    @Override
    public AjaxResult merchantLogin(LoginDTO dto) {
        return BaseResult.successResult(sysUserMerchantService.merchantCheck(dto));
    }

    /**
     * 获取代理商详情
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult getAgentInfo(AgentDTO dto) {
        return BaseResult.successResult(sysUserAgentService.getById(dto));
    }

    /**
     * 获取商家详情
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult getMerchantInfo(MerchantDTO dto) {
        return BaseResult.successResult(sysUserMerchantService.getById(dto));
    }

    @Override
    public AjaxResultGeneric<UserVO> getMemberInfo(MemberDTO memberDTO) {
        SysUser sysUser = userService.getById(memberDTO.getUserId());
        UserVO userVO = new UserVO();
        if (sysUser != null) {
            BeanUtils.copyProperties(sysUser, userVO);
        }
        return BaseResult.successGenericResult(userVO);
    }

    @Override
    public AjaxResult getMerchantDetail(MemberDTO dto) {
        return BaseResult.successResult(sysUserMerchantService.getById(dto.getUserId()));
    }

    @Override
    public AjaxResult appMerchantRegister(AppMerchantRegDTO dto) {
        return sysUserMerchantService.appMerchantRegister(dto);
    }

    @Override
    public AjaxResult queryUserInfo(String userId, String openId) {
        return BaseResult.successResult(userService.queryUserInfo(openId, userId));
    }

    @Override
    public AjaxResult updateUserPhone(UpdUserPhoneDTO dto) {
        return BaseResult.successResult(userService.updateUserPhone(dto));
    }

    @Override
    public int updateUserSessionKey(Map<String, String> mapObj) {
        return userService.updateUserSessionKey(mapObj.get("openid"), mapObj.get("session_key"));
    }

    @Override
    public String export(UserDTO dto) {
        dto.setPages(new Pages());
        PageResult<UserVO> userVOS = userService.userList(dto);
        ExportExcelUtil.Builder builder = ExportExcelUtil.Builder.newInstance()
                .createSheet("用户导出")
                .createHeader(Arrays.asList("用户ID", "昵称", "头像", "性别", "当前小区", "姓名",
                        "手机号", "绑定区县", "授权时间", "授权来源", "是否为会员", "成为会员时间", "状态"))
                .setFields(Arrays.asList("getId", "getNickname", "getAvatar", "getGender", "getBindCommunity",
                        "getName", "getPhone", "getUserCityAddress", "getCreateTime", "getOrigin", "getMemberType",
                        "getJoinMemberTime", "getStatus"))
                .putTranslateFunction("getGender", i -> {
                    switch ((int) i) {
                        case 1:
                            return "男";
                        case 2:
                            return "女";
                        default:
                            return "未知";
                    }
                })
                .putTranslateFunction("getBindCommunity", id -> {
                    if (id != null) {
                        return communityService.getDetail(String.valueOf(id)).getName();
                    }
                    return "";
                })
                .putTranslateFunction("getMemberType", i -> {
                    switch ((int) i) {
                        case 1:
                            return "会员";
                        case 0:
                            return "非会员";
                        default:
                            return "未知";
                    }
                }).putTranslateFunction("getStatus", i -> {
                    switch ((int) i) {
                        case 1:
                            return "正常";
                        case 2:
                            return "禁用";
                        default:
                            return "未知";
                    }
                });
        int count = userVOS.getPages().getTotal();
        dto.getPages().setPageSize(1000);
        dto.getPages().setPageIndex(1);
        do {
            PageResult<UserVO> voPageResult = userService.userList(dto);
            builder.put(voPageResult.getList());
            count = count - 1000;
            dto.getPages().setPageIndex(dto.getPages().getPageIndex() + 1);
        } while (count > 0);
        return builder.export();
    }

    @Override
    public String merchantExport(MerchantDTO dto) {
        dto.setPages(new Pages());
        PageResult<MerchantVO> merchantList = sysUserMerchantService.merchantList(dto);
        ExportExcelUtil.Builder builder = ExportExcelUtil.Builder.newInstance()
                .createSheet("商家导出")
                .createHeader(Arrays.asList("商家ID", "商家名称", "商家定位", "绑定小区", "姓名",
                        "手机号", "商家状态", "会员有效期", "商品数", "总收入"))
                .setFields(Arrays.asList("getId", "getTitle", "getAddress", "getCommunities", "getName",
                        "getPhone", "getStatus", "getMemberTime", "getOrderCount", "getTotalProfit"))
                .putTranslateFunction("getStatus", i -> {
                    switch ((int) i) {
                        case 0:
                            return "使用中";
                        case 1:
                            return "禁用";
                        case 2:
                            return "删除";
                        default:
                            return "未知";
                    }
                })
                .putTranslateFunction("getCommunities", list -> {
                    if (list == null) {
                        return "";
                    }
                    List<Community> l = (List<Community>) list;
                    if (CollectionUtils.isNotEmpty(l)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        l.forEach(community -> stringBuilder.append(community.getName()).append(","));
                        return stringBuilder.toString();
                    } else {
                        return "";
                    }
                });
        int count = merchantList.getPages().getTotal();
        dto.getPages().setPageSize(1000);
        dto.getPages().setPageIndex(1);
        do {
            PageResult<MerchantVO> merchantVOPageResult = sysUserMerchantService.merchantList(dto);
            builder.put(merchantVOPageResult.getList());
            count = count - 1000;
            dto.getPages().setPageIndex(dto.getPages().getPageIndex() + 1);
        } while (count > 0);
        return builder.export();
    }

    @Override
    public boolean communtyPartnerRegister(CommuntyPartnerRegisterDTO dto) {
        return sysCommunityPartnerService.register(dto);
    }

    @Override
    public PageResult<SysCommunityPartnerVo> communtyPartnerList(CommuntyPartnerDTO dto) {
        JWTBean jwtBean = JWTThreadLocalHelper.get();
        if (RoleConstants.AGENT_ROLE_STRING.equals(jwtBean.getRole())) {
            dto.setAgentId(jwtBean.getId());
        }
        return sysCommunityPartnerService.querySysCommunityPartners(dto);
    }

    @Override
    public SysCommunityPartnerDetailVO communtyPartnerDetail(String id) {
        return sysCommunityPartnerService.queryDetal(id);
    }

    @Override
    public boolean communtyPartnerUpdate(CommuntyPartnerRegisterDTO dto) {
        return sysCommunityPartnerService.update(dto);
    }

    @Override
    public AjaxResult partenerLogin(LoginDTO dto) {
        return BaseResult.successResult(sysCommunityPartnerService.partenerCheck(dto));
    }

    @Override
    public SysCommunityPartner getPartenerInfo(String partenerId) {
        return sysCommunityPartnerService.queryDetal(partenerId);
    }

    @Override
    public SysUser appLogin(String phone, String code) {
        // mock掉
        if ("18000000000".equals(phone) && "1234".equals(code)) {

        } else {
            String savedCode = RedisTool.get(SmsConstant.LOGIN_CHECK_CODE_REDIS_HEAD + phone);
            if (!code.equals(savedCode)) {
                throw new YidaoException(ReturnData.CODE_WORING);
            }
        }
        List<SysUser> users = userService.queryByPhone(phone);
        if (users.size() > 1) {
            throw new YidaoException(ReturnData.OVER_ONE_USER);
        }
        if (users.size() == 0) {
            throw new YidaoException(ReturnData.NOT_USER);
        }
        return users.get(0);
    }

    @Override
    public int updatePassword(String account, String password, String userType) {
        try {
            return userService.updatePassword(account, password, userType);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public LoginVO sysLogin(LoginDTO dto) {
        LoginVO loginVO = new LoginVO();
        SysUserVO sysUserVO;
        String roleId = dto.getRoleId();
        //根据roleId判断用户类型: 1 管理员 2团长 3 商家 4 代理商 5普通用户 6群社代理商
        switch (roleId) {
            case CommonConstants.ONE_STR:
                sysUserVO = sysUserAdminService.adminCheck(dto);
                break;
            case CommonConstants.THREE_STR:
                sysUserVO = sysUserMerchantService.merchantCheck(dto);
                break;
            case CommonConstants.FOUR_STR:
                sysUserVO = sysUserAgentService.agentCheck(dto);
                break;
            case RoleConstants.COMMUNITY_PARTENER_STRING:
                sysUserVO = sysCommunityPartnerService.partenerCheck(dto);
                break;
            default:
                throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }
        List<MenuVO> menuVOList = new ArrayList<>();
        /* redis失效后从数据库里面取 */
        /* 获取系统用户与菜单的关联 */
        sysUserVO.setRoleId(roleId);
        String token = loginToken(sysUserVO, menuVOList, parseInt(dto.getRoleId()));
        SysPermissionVO sysPermissionVO = sysPermissionService.refresh(token);
        BeanUtils.copyProperties(sysUserVO, loginVO);
        loginVO.setRoleId(roleId);
        loginVO.setMenuVOList(sysPermissionVO.getMenuList());
        loginVO.setToken(token);
        // TODO 添加页面版本号
        loginVO.setVersion("1111");

        loginVO.setButtonAliasList(sysPermissionVO.getButtonAliasList());
        return loginVO;
    }

    /**
     * 获取登录token
     */
    private String loginToken(SysUserVO sysUserVO, List<MenuVO> menuVOList, int type) {
        JWTBean jwtBean = new JWTBean();
        jwtBean.setId(sysUserVO.getId());
        jwtBean.setRole(sysUserVO.getRoleId());
        jwtBean.setName(sysUserVO.getName());
        jwtBean.setMenu(menuVOList);
        String token;
        try {
            token = JWTUtil.setJwtBean(jwtBean, type);
        } catch (Exception e) {
            LoggerUtils.error(SysUserFacadeImpl.class, "登录获取token异常！");
            throw new YidaoException(ReturnData.ERROR_GET_TOKEN);
        }
        return token;
    }

}
