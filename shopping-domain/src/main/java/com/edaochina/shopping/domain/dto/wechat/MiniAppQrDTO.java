package com.edaochina.shopping.domain.dto.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * 生成小程序码的参数
 */
@Data
public class MiniAppQrDTO implements Serializable {

    /**
     * 调用接口的凭证（必传）
     */
    private String access_token;
    /**
     * 场景参数（必传）
     */
    private String scene;
    /**
     * 页面（非必传，默认为主页）
     */
    private String page;
    /**
     * 二维码的宽度（非必传）
     */
    private int width;
    /**
     * 自动设置线条颜色（非必传）
     */
    private boolean auto_color;
    /**
     * 线条的rgb色（非必传）
     */
    private Object line_color;
    /**
     * 是否需要透明底色（非必传）
     */
    private boolean is_hyaline;

}
