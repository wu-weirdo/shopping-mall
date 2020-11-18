package com.edaochina.shopping.web.user;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.api.service.sys.SearchHistoryService;
import com.edaochina.shopping.api.service.user.UserService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.JsonUtils;
import com.edaochina.shopping.common.utils.ValidUtil;
import com.edaochina.shopping.common.utils.weixinpay.WechatPlatForm;
import com.edaochina.shopping.common.wxpay.config.WxPayConfig;
import com.edaochina.shopping.domain.dto.user.*;
import com.edaochina.shopping.domain.entity.sys.SearchHistory;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.vo.user.UserVO;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 商户表 by 张志侃 前端控制器
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/app/user")
@Api(tags = "用户")
public class AppUserController {


    @Resource
    SysUserFacade sysUserFacade;

    private final UserService userService;

    private final SearchHistoryService searchHistoryService;

    public AppUserController(UserService userService, SearchHistoryService searchHistoryService) {
        this.userService = userService;
        this.searchHistoryService = searchHistoryService;
    }

    /**
     * 获取openid
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/apply/openid")
    public AjaxResult getOpenid(String code) {
        String jsonData = WechatPlatForm.codeApply(code, WxPayConfig.getAppId(), WxPayConfig.getAppSecret());
        //openid  用户唯一标识
        Map<String, String> mapObj = JsonUtils.toBean(jsonData, Map.class);
        String openid = mapObj.get("openid");
        return BaseResult.successResult(openid);
    }

    /**
     * 获取v2.8.1版本openid
     *
     * @param code
     * @return
     */
    @GetMapping(value = "v2/apply/openid")
    public AjaxResult getV2Openid(String code) {
        String jsonData = WechatPlatForm.codeApply(code, WxPayConfig.getAppId(), WxPayConfig.getAppSecret());
        //openid  用户唯一标识
        Map<String, String> mapObj = JsonUtils.toBean(jsonData, Map.class);
        // 如果openId已存在则更新用户的sessionKey
        sysUserFacade.updateUserSessionKey(mapObj);
        return BaseResult.successResult(mapObj);
    }

    @PostMapping("/login")
    @OperationLogMark("小程序用户登陆")
    public AjaxResult login(@Valid @RequestBody UserRegDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        return sysUserFacade.userLogin(dto);
    }

    @RequestMapping("/updateUserPhone")
    public AjaxResult updateUserPhone(@RequestBody UpdUserPhoneDTO dto) {
        return sysUserFacade.updateUserPhone(dto);
    }

    @PostMapping("/getMemberInfo")
    public AjaxResultGeneric<UserVO> getMemberInfo(@RequestBody MemberDTO memberDTO) {
        return sysUserFacade.getMemberInfo(memberDTO);
    }


    /**
     * 商家注册
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/register/merchant")
    @OperationLogMark("商家注册")
    public AjaxResult merchantRegister(@Valid @RequestBody AppMerchantRegDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        return sysUserFacade.appMerchantRegister(dto);
    }

    @RequestMapping("queryUserInfo")
    public AjaxResult queryUserInfo(@RequestBody JSONObject req) {
        return sysUserFacade.queryUserInfo(req.getString("userId"), req.getString("openId"));
    }

    @RequestMapping("updateUserInfo")
    public AjaxResult updateUserInfo(@RequestBody UpdUserDTO updUserDTO) {
        return sysUserFacade.updateUserInfo(updUserDTO);
    }

    /**
     * app登录
     *
     * @param phone 手机号
     * @param code  验证码
     * @return SysUser
     */
    @RequestMapping("appLogin")
    public AjaxResultGeneric<SysUser> appLogin(String phone, String code) {
        return new AjaxResultGeneric<>(sysUserFacade.appLogin(phone, code));
    }

    /**
     * 查询用户搜索历史
     *
     * @param userId 用户id
     * @return 搜索历史
     */
    @GetMapping("search/history")
    public AjaxResultGeneric<IPage<SearchHistory>> searchHistory(String userId) {
        QueryWrapper<SearchHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct content", "date(create_time) as create_time").orderByDesc("date(create_time)");
        queryWrapper.lambda().eq(SearchHistory::getUid, userId);
        return new AjaxResultGeneric<>(searchHistoryService.page(new Page<>(1, 5), queryWrapper));
    }

    /**
     * 删除用户搜索历史
     *
     * @param userId 用户id
     * @return 是否成功
     */
    @PostMapping("del/history")
    public AjaxResultGeneric<Boolean> delSearchHistory(String userId) {
        QueryWrapper<SearchHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SearchHistory::getUid, userId);
        return new AjaxResultGeneric<>(searchHistoryService.remove(queryWrapper));
    }

    @PostMapping("updateInfo")
    public AjaxResultGeneric<Boolean> updateInfo(UserInfoDTO dto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getId, dto.getUserId());
        return new AjaxResultGeneric<>(userService.update(sysUser, queryWrapper));
    }

    /**
     * 通过用户账号和用户类型获取用户手机号
     *
     * @param account
     * @param userType
     * @return
     */
    @PostMapping("getUserPhone")
    public AjaxResult getUserPhone(String account, String userType) {
        return BaseResult.successResult(userService.getUserPhone(account, userType));
    }

    /**
     * 通过用户账号和用户类型获取用户手机号
     *
     * @param account
     * @param userType
     * @return
     */
    @GetMapping("sendSafeMsg")
    public AjaxResult sendSafeMsg(String account, String userType, String phone) {
        return BaseResult.successResult(userService.sendSafeMsg(account, userType, phone));
    }


    /**
     * 通过用户账号和用户类型获取用户手机号
     *
     * @param userType
     * @return
     */
    @GetMapping("checkUpdatePasswordMsg")
    public AjaxResult checkUpdatePasswordMsg(String userType, String phone, String msgCode) {
        return BaseResult.successResult(userService.checkUpdatePasswordMsg(msgCode, userType, phone));
    }
}

