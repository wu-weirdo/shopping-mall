package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author zzk
 * @Date 2019/1/9
 */
@Data
public class UserRegDTO implements Serializable {
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 0 未知 1 男 2 女
     */
    private Integer gender;

    /**
     * 绑定小区
     */
    private String bindCommunity;

    /**
     * 微信openId
     */
    @NotBlank
    private String openId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 来源商家
     */
    private String origin;

    /**
     * 短信验证码
     */
    private String verifyCode;


    /**
     * 1 正常 2禁用
     */
    private Integer status;

    /**
     * 用户微信session(有效期30天)
     */
    private String sessionKey;


    private String encryptedData;

    private String iv;
}
