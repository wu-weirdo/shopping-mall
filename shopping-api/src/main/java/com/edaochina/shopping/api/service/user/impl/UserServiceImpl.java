package com.edaochina.shopping.api.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.sys.SmsVerifyMapper;
import com.edaochina.shopping.api.dao.user.SysCommunityPartnerMapper;
import com.edaochina.shopping.api.dao.user.SysUserAgentMapper;
import com.edaochina.shopping.api.dao.user.SysUserMerchantMapper;
import com.edaochina.shopping.api.dao.user.UserMapper;
import com.edaochina.shopping.api.facade.sys.CommonFacade;
import com.edaochina.shopping.api.service.user.UserService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.CharUtils;
import com.edaochina.shopping.common.utils.SignUtils;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.wxpay.util.WXConnectionUtil;
import com.edaochina.shopping.common.wxpay.util.WXHelp;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.constants.SmsConstant;
import com.edaochina.shopping.domain.dto.sys.SmsVerifyDTO;
import com.edaochina.shopping.domain.dto.user.*;
import com.edaochina.shopping.domain.entity.sys.SmsVerify;
import com.edaochina.shopping.domain.entity.user.SysCommunityPartner;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.entity.user.SysUserAgent;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.user.MerchantCustomerVo;
import com.edaochina.shopping.domain.vo.user.UserVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 普通用户表  by zzk 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SysUserMerchantMapper merchantMapper;

    @Autowired
    private CommonFacade commonFacade;

    @Resource
    private SysUserAgentMapper agentMapper;

    @Resource
    private SysCommunityPartnerMapper partnerMapper;

    @Resource
    private SmsVerifyMapper smsVerifyMapper;

    private static String USER_LOCK_HEAD = "USER_LOCK_HEAD_";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult userLogin(UserRegDTO dto) {
        logger.info("userLogin req:" + JSON.toJSONString(dto));
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        if (StringUtils.isNotBlank(dto.getName())) {
            sysUser.setName(CharUtils.decodeby10(dto.getName()));
        }
        if (StringUtils.isNotBlank(dto.getNickname())) {
            sysUser.setNickname(CharUtils.decodeby10(dto.getNickname()));
        }
        sysUser.setUpdateTime(LocalDateTime.now());

        //  设置uid
        try {
            String uidStr = WXHelp.decrypt(dto.getEncryptedData(), dto.getSessionKey(), dto.getIv());
            logger.info("uidStr :" + uidStr);
            sysUser.setWxUid(JSON.parseObject(uidStr).getString("unionId"));
        } catch (Exception e) {
            logger.info("set use uid has error,userId :" + sysUser.getId());
        }

        //判断用户是否已注册
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", dto.getOpenId());
        SysUser result = userMapper.selectOne(wrapper);
        logger.info("SysUser result:" + JSON.toJSONString(result));
        String id;
        if (result != null) {
            //判断是否为绑定手机号
            if (StringUtils.isNotBlank(dto.getPhone())) {
                checkVerifyCode(dto);
            }
            id = result.getId();
            sysUser.setId(id);
            userMapper.updateUserInfo(sysUser);
        } else {
            if (RedisTool.lock(USER_LOCK_HEAD + dto.getOpenId())) {
                throw new YidaoException(ReturnData.USER_IS_CREATING);
            }
            id = IdUtils.nextId();
            sysUser.setId(id);
            sysUser.setStatus(CommonConstants.ONE_INT);
            sysUser.setCreateTime(LocalDateTime.now());
            //  查询相应的uid
            updateUserUid(dto.getOpenId());
            try {
                userMapper.insert(sysUser);
                // 不解锁了，锁有限期就几分钟
            } catch (DuplicateKeyException e) {
                throw new YidaoException("重复注册!");
            }

        }
        return BaseResult.successResult(userMapper.selectById(id));
    }

    /**
     * 判断验证码是否有效
     */
    private void checkVerifyCode(UserRegDTO dto) {
        QueryWrapper<SmsVerify> verifyWrapper = new QueryWrapper<>();
        verifyWrapper.eq("phone", dto.getPhone());
        verifyWrapper.eq("code", dto.getVerifyCode());
        verifyWrapper.eq("return_msg", "OK");
        //验证码五分钟有效
        long time = 1000 * 60 * 5;
        Date oneMinuteAgo = new Date(System.currentTimeMillis() - time);
        verifyWrapper.ge("create_time", oneMinuteAgo);
        List<SmsVerify> smsVerifies = smsVerifyMapper.selectList(verifyWrapper);
        //如果查询不到对应的验证码记录,则返回验证码错误或已过期
        if (CollectionUtils.isEmpty(smsVerifies)) {
            throw new YidaoException(ReturnData.CODE_WORING);
        } else {
            //否则將msg改为已使用"USED"
            SmsVerify smsVerify = smsVerifies.get(0);
            smsVerify.setReturnMsg(CommonConstants.USED);
            smsVerifyMapper.updateById(smsVerify);
        }
    }

    @Override
    public PageResult<UserVO> userList(UserDTO dto) {
        logger.info("user list dto:" + JSON.toJSONString(dto));
        List<UserVO> vos;
        Integer total = 0;
        switch (dto.getRoleId().trim()) {
            case RoleConstants.ADMIN_ROLE_STRING:
            case RoleConstants.COMMUNITY_PARTENER_STRING:
                total = userMapper.userListCount(dto);
                vos = userMapper.userList(dto);
                break;
            case RoleConstants.AGENT_ROLE_STRING:
                total = userMapper.userListByRoleCount(dto);
                vos = userMapper.userListByRole(dto);
                break;
            case RoleConstants.MERCHANT_ROLE_STRING:
                total = userMapper.queryHasByGoodsInShopCount(dto);
                vos = userMapper.queryHasByGoodsInShop(dto);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dto.getRoleId().trim());
        }
        PageResult<UserVO> pageResult = new PageResult<>();
        dto.getPages().setTotal(total);
        pageResult.setList(vos);
        pageResult.setPages(dto.getPages());
        return pageResult;
    }

    @Override
    public AjaxResult userDetail(String id) {
        return BaseResult.successResult(userMapper.getUserById(id));
    }

    @Override
    public SysUser queryUserInfo(String openId, String userId) {
        if (org.springframework.util.StringUtils.isEmpty(openId)) {
            return null;
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getOpenId, openId).eq(SysUser::getStatus, 1);
        if (!org.springframework.util.StringUtils.isEmpty(userId)) {
            wrapper.lambda().eq(SysUser::getId, userId);
        }
        return userMapper.selectList(wrapper).stream().findFirst().orElse(null);
    }

    @Override
    public AjaxResult updateUserInfo(UpdUserDTO updUserDTO) {
        logger.info("updateUserInfo:" + JSON.toJSONString(updUserDTO));
        SysUser user = new SysUser();
        BeanUtils.copyProperties(updUserDTO, user);
        //更新手机号修改
        if (StringUtils.isNotBlank(updUserDTO.getUserId())) {
            user.setId(updUserDTO.getUserId());
        }
        user.setPhone(updUserDTO.getPhone());
        user.setName(updUserDTO.getName());
        user.setPhoneUpdateFlag(1);
        return BaseResult.successResult(userMapper.updateById(user));
    }

    @Override
    public void updateUserLawId(String userId, String lawId) {
        userMapper.updateUserLawId(userId, lawId);
    }

    /**
     * 禁用账号
     *
     * @param dto
     * @return
     */
    @Override
    public int disableAccount(UserDTO dto) {
        SysUser sysUser = userMapper.selectById(dto.getId());
        sysUser.setStatus(2);
        return userMapper.updateById(sysUser);
    }

    /**
     * 启用账号
     *
     * @param dto
     * @return
     */
    @Override
    public int enableAccount(UserDTO dto) {
        SysUser sysUser = userMapper.selectById(dto.getId());
        sysUser.setStatus(1);
        return userMapper.updateById(sysUser);
    }

    /**
     * 删除账号
     *
     * @param dto
     * @return
     */
    @Override
    public int deleteAccount(UserDTO dto) {
        return userMapper.deleteAccount(dto);
    }

    /**
     * 编辑账号
     *
     * @param sysUserUpdateDTO
     * @return
     */
    @Override
    public int editAccount(SysUserUpdateDTO sysUserUpdateDTO) {
        return userMapper.updateAccount(sysUserUpdateDTO);
    }


    @Override
    public List<SysUser> checkPhone(SmsVerifyDTO smsVerifyDTO) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", smsVerifyDTO.getPhone());
        return userMapper.selectList(wrapper);
    }

    @Override
    public void updateUserMemberType(String id, String s, String shopName, String partenerId) {
        userMapper.updateUserMemberType(id, s, shopName, partenerId);
    }

    @Override
    public int updateUserPhone(UpdUserPhoneDTO dto) {
        logger.info("updateUserPhone req:" + JSON.toJSONString(dto));
        if (StringUtils.isBlank(dto.getUserId())) {
            throw new YidaoException(ReturnData.NO_LOGIN_ERROR);
        }
        String sessionKey;
        if (StringUtils.isNotBlank(dto.getSessionKey())) {
            sessionKey = dto.getSessionKey();
        } else {
            SysUser user = userMapper.selectById(dto.getUserId());
            sessionKey = user.getSessionKey();
        }
        logger.info("sessionKey:" + sessionKey);
        String result = WXHelp.decrypt(dto.getEncryptedData(), sessionKey, dto.getIv());
        JSONObject resultJson = JSON.parseObject(result);
        return userMapper.updateUserPhone(dto.getUserId(), resultJson.getString("phoneNumber"));
    }

    @Override
    public int updateUserSessionKey(String openid, String session_key) {
        return userMapper.updateUserSessionKey(openid, session_key);
    }

    @Override
    public int updateUserTencetOpenIdByUid(String tencentOpenId, String uid) {
        return userMapper.updateUserTencetOpenIdByUid(tencentOpenId, uid);
    }

    @Override
    public void updateUsersUid() {
        int num = 0;
        int n = 0;
        do {
            List<SysUser> users = userMapper.selectUsers(n, 100);
            n += 100;
            for (SysUser user : users) {
                updateUserUid(user.getOpenId());
            }
        } while (num == 100);
    }

    @Override
    public List<SysUser> queryByPhone(String phone) {
        return userMapper.queryByPhone(phone);
    }

    @Override
    public List<SysUser> queryHasPhone(int i, int n) {
        return userMapper.queryHasPhone(i, n);
    }

    @Override
    public List<MerchantCustomerVo> merchantCustomer(SysMerchantCustomerDTO dto) {
        return userMapper.merchantCustomer(dto);
    }

    /**
     * 获取用户手机号
     *
     * @param account
     * @param userType
     * @return
     */
    @Override
    public String getUserPhone(String account, String userType) {
        // TODO
        return null;
    }

    /**
     * 更加账号密码发送短信验证码
     *
     * @param account
     * @param userType
     * @param phone
     * @return
     */
    @Override
    public int sendSafeMsg(String account, String userType, String phone) {
        // 判断手机号是否正确
        if (StringUtils.isBlank(userType)) {
            throw new YidaoException(ReturnData.PHONE_ERROR);
        }
        String userPhone = "";
        if (RoleConstants.MERCHANT_ROLE_STRING.equals(userType)) {
            SysUserMerchant merchant = merchantMapper.queryByAccount(account);
            if (merchant == null) {
                throw new YidaoException(ReturnData.UPDATE_PASSWOED_ACCOUNT_NOT_EXIST);
            }
            userPhone = merchant.getPhone();
        } else if (RoleConstants.AGENT_ROLE_STRING.equals(userType)) {
            SysUserAgent userAgent = agentMapper.queryByAccount(account);
            if (userAgent == null) {
                throw new YidaoException(ReturnData.UPDATE_PASSWOED_ACCOUNT_NOT_EXIST);
            }
            userPhone = userAgent.getPhone();
        } else if (RoleConstants.COMMUNITY_PARTENER_STRING.equals(userType)) {
            SysCommunityPartner communityPartner = partnerMapper.queryByAccount(account);
            if (communityPartner == null) {
                throw new YidaoException(ReturnData.UPDATE_PASSWOED_ACCOUNT_NOT_EXIST);
            }
            userPhone = communityPartner.getPhone();
        }

        if (!userPhone.equals(phone)) {
            throw new YidaoException(ReturnData.PHONE_ERROR);
        }
        // 发送验证码
        commonFacade.sendUpdatePasswordMsg(phone, userType);
        return 0;
    }

    @Override
    public int checkUpdatePasswordMsg(String msgCode, String userType, String phone) {
        String redisCode = RedisTool.get(SmsConstant.UPDATE_PASSWORD_REDIS_HEAD + userType + phone);
        if (msgCode.equals(redisCode)) {
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePassword(String account, String password, String userType) throws NoSuchAlgorithmException {
        switch (userType) {
            case "3":
                SysUserMerchant merchant = merchantMapper.queryByAccount(account);
                merchant.setPassword(SignUtils.generateSign(password + merchant.getSalt()));
                merchantMapper.updateById(merchant);
                break;
            case "4":
                SysUserAgent agent = agentMapper.queryByAccount(account);
                agent.setPassword(SignUtils.generateSign(password + agent.getSalt()));
                agentMapper.updateById(agent);
                break;
            case "6":
                SysCommunityPartner partner = partnerMapper.queryByAccount(account);
                partner.setPassword(SignUtils.generateSign(password + partner.getSalt()));
                partnerMapper.updateByPrimaryKey(partner);
                break;
            default:
                throw new YidaoException("不支持的用户类型: " + userType);
        }
        return 1;
    }

    private void updateUserUid(String openId) {
        try {
            logger.info("update user uid openId:" + openId);
            String uid = WXConnectionUtil.getUid(openId);
            logger.info("update user uid uid:" + uid);
            userMapper.updateUserUid(uid, openId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

