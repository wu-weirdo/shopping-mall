package com.edaochina.shopping.domain.base.wechat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 微信token类
 *
 * @author Jason
 */
@Data
@Accessors(chain = true)
public class WxToken {
    /**
     * 网页授权接口调用凭证
     */
    private String accessToken;
    /**
     * 凭证有效时长
     */
    private Long expiresIn;
    /**
     * 用于刷新凭证
     */
    private String refreshToken;
    /**
     * 用户标识
     */
    private String openId;
    /**
     * 用户授权作用域
     */
    private String scope;

    private String resCode;

    private String errorMsg;
}
