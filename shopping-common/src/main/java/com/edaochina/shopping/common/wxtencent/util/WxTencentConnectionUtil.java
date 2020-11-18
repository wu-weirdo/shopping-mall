package com.edaochina.shopping.common.wxtencent.util;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.http.HttpClientUtilsTool;
import com.edaochina.shopping.common.wxtencent.config.WxTencentConfig;
import org.apache.commons.lang3.StringUtils;

/**
 * 微信公众号连接处理类(无关业务逻辑的)
 *
 * @author jintian
 * @date 2019/7/31 11:35
 */
public class WxTencentConnectionUtil {

    private static String WX_TENCENT_TOKEN = "wx_tencent_token";

    /**
     * 获取微信accessToken
     *
     * @return
     */
    public static String getAccessToken() {
        String token;
        token = RedisTool.get(WX_TENCENT_TOKEN);
        if (token != null) {
            return token;
        } else {
            JSONObject response = HttpClientUtilsTool.httpGet(WxTencentConfig.WX_GET_ACCESS_TOKEN + WxTencentConfig.APP_ID + "&secret=" + WxTencentConfig.APP_SECRET);
            token = response.getString("access_token");
            RedisTool.set(WX_TENCENT_TOKEN, token, 6000);
        }
        return token;
    }

    /**
     * 发送模板消息
     *
     * @param reqJSON
     * @return
     */
    public static String sendMsg(JSONObject reqJSON) {
        return HttpClientUtilsTool.httpPost(WxTencentConfig.WX_SEND_MSG + getAccessToken(), reqJSON).toJSONString();
    }

    /**
     * 获取用户列表
     *
     * @param nextOpenId
     * @return
     */
    public static JSONObject getTencentUsers(String nextOpenId) {
        if (StringUtils.isNotBlank(nextOpenId)) {
            return HttpClientUtilsTool.httpGet(WxTencentConfig.GET_USERS_URL + getAccessToken());
        } else {
            return HttpClientUtilsTool.httpGet(WxTencentConfig.GET_USERS_URL + getAccessToken() + "&next_openid=" + nextOpenId);
        }
    }

    /**
     * 获取用户的uid
     *
     * @param openId
     * @return
     */
    public static String getUid(String openId) {
        JSONObject response = HttpClientUtilsTool.httpGet(WxTencentConfig.GET_USER_INFO + getAccessToken() + "&openid=" + openId + "&lang=zh_CN");
        return response.getString("unionid");
    }
}
