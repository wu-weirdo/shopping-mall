package com.edaochina.shopping.domain.dto.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zzk
 * @Date 2018/12/13
 */
@Data
public class SmsVerifyDTO implements Serializable {
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String code;
    /**
     * 模板id
     */
    private String templateCode;

    /**
     * 短信类型
     */
    private Integer type;
}
