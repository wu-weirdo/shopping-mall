package com.edaochina.shopping.common.wxtencent.config;

/**
 * 微信公众号配置
 *
 * @author jintian
 * @date 2019/7/31 11:33
 */
public class WxTencentConfig {

    public final static String APP_ID = "wx5c64c2d67b6e275b";

    public final static String APP_SECRET = "eae2c1f46bf1f402c9f8452b3cf0c484";

    /**
     * 获取accessToken的url
     */
    public final static String WX_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";

    /**
     * 发送信息的url
     */
    public final static String WX_SEND_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 获取公众号用户列表的url
     */
    public final static String GET_USERS_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";

    /**
     * 获取公众号用户信息的url
     */
    public final static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
}
