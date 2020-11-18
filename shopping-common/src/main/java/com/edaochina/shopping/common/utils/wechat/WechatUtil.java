package com.edaochina.shopping.common.utils.wechat;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.http.HttpUtil;
import com.edaochina.shopping.domain.base.wechat.WxToken;
import com.edaochina.shopping.domain.constants.RedisConstants;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Jason
 */
@UtilityClass
public class WechatUtil {
    /**
     * 获取微信小程序授权凭证
     *
     * @param appId     小程序的唯一标识
     * @param appSecret 小程序的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public WxToken getOauth2AccessToken(String appId, String appSecret, String code) {
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取微信小程序授权凭证
        JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
        return getWxToken(jsonObject);
    }

    /**
     * 刷新获取access_token
     *
     * @param appId     小程序的唯一标识
     * @param appSecret refresh_token
     * @return access_token
     */
    public WxToken getAccessToken(String clientType, String appId, String appSecret) {
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("APPSECRET", appSecret);
        // 刷新获取access_token
        JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
        WxToken wxToken = getAccessToken(jsonObject);
        if (wxToken != null) {
            String value = JSONObject.toJSONString(wxToken.getAccessToken());
            String key = RedisConstants.WX_ACCESS_TOKEN + clientType;
            if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(key)) {
                RedisTool.set(key, value, wxToken.getExpiresIn());
            }
        }
        return wxToken;
    }

    /**
     * json对象转换为微信类
     *
     * @param jsonObject
     * @return
     */
    private WxToken getWxToken(JSONObject jsonObject) {
        WxToken wat = null;
        if (null != jsonObject) {
            try {
                wat = new WxToken().
                        setAccessToken(jsonObject.getString("access_token")).
                        setExpiresIn(jsonObject.getLong("expires_in")).setRefreshToken(jsonObject.getString("refresh_token")).setOpenId(jsonObject.getString("openid")).setScope(jsonObject.getString("scope")).setResCode("1");
            } catch (Exception e) {
                String errorCode = jsonObject.getString("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                wat = new WxToken().setResCode("0").setErrorMsg("");
                LoggerUtils.error(WechatUtil.class, "json对象转换为微信类 ,errcode：" + errorCode + ",errmsg：" + errorMsg);
            }
        }
        return wat;
    }

    /**
     * json对象转换为微信类
     *
     * @param jsonObject
     * @return
     */
    private WxToken getAccessToken(JSONObject jsonObject) {
        WxToken wat = null;
        if (null != jsonObject) {
            try {
                wat = new WxToken().
                        setAccessToken(jsonObject.getString("access_token")).
                        setExpiresIn(jsonObject.getLong("expires_in"));
            } catch (Exception e) {
                LoggerUtils.error(WechatUtil.class, "json对象转换为微信类 ,errcode：" + jsonObject.getString("errcode") + ",errmsg：" + jsonObject.getString("errmsg"));
            }
        }
        return wat;
    }

    /**
     * 发送微信小程序推送消息
     *
     * @param wxMessage 消息内容
     *                  eg：{
     *                  "touser": "OPENID",
     *                  "template_id": "TEMPLATE_ID",
     *                  "page": "index",
     *                  "form_id": "FORMID",
     *                  "data": {
     *                  "keyword1": {
     *                  "value": "339208499"
     *                  },
     *                  "keyword2": {
     *                  "value": "2015年01月05日 12:30"
     *                  },
     *                  "keyword3": {
     *                  "value": "腾讯微信总部"
     *                  } ,
     *                  "keyword4": {
     *                  "value": "广州市海珠区新港中路397号"
     *                  }
     *                  },
     *                  "emphasis_keyword": "keyword1.DATA"
     *                  }
     * @param type      发送对象（0为客户，1为测量师）
     * @return 返回结果

    public WxMessageResult sendMessage(WxMessage wxMessage, int type) {
    WxMessageResult wxMessageResult = new WxMessageResult();
    String accessToken;
    if (ClientType.CUST.getCode() == type) {
    accessToken = RedisTool.get(RedisConstants.WX_ACCESS_TOKEN + "cust");
    } else if (ClientType.MEASURING.getCode() == type) {
    accessToken = RedisTool.get(RedisConstants.WX_ACCESS_TOKEN + "measuring");
    } else {
    throw new YidaoException(ReturnData.ERROR_SERVLET_REQUEST_PARAMETER);
    }
    // 拼接请求地址
    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
    requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken.replaceAll("\"",""));
    // 发送微信小程序推送消息
    JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "POST", JSONObject.toJSONString(wxMessage));
    if (null != jsonObject) {
    Integer errorCode = jsonObject.getInteger("errcode");
    String errorMsg = jsonObject.getString("errmsg");
    if (errorCode == 0) {
    wxMessageResult.setTemplateId(jsonObject.getString("template_id"));
    }
    wxMessageResult.setErrCode(errorCode).setErrMsg(errorMsg);
    LoggerUtils.info(WechatUtil.class, "发送微信小程序推送消息 ,errcode：" + errorCode + ",errmsg：" + errorMsg);
    }
    return wxMessageResult;
    }*/
}
