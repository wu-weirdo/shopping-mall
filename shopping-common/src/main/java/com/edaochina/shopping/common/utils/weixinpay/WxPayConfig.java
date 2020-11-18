package com.edaochina.shopping.common.utils.weixinpay;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.RandomUtils;

import java.util.Map;

/**
 * 微信JS发起支付配置数据结构
 * 参与签名的参数有appId, timeStamp, nonceStr, package, signType
 *
 * @author zzk
 * @created 2018-12-12 17:06:19
 * @since v1.0
 */
public class WxPayConfig {
    /**
     * 公众号ID
     */
    public String appId;
    /**
     * 随机字符串
     */
    public String nonceStr;
    /**
     * 时间戳
     */
    public String timeStamp;

    /**
     * 当前页面的URL地址
     */
    public String packAge;

    /**
     * 加密方式
     */
    public String signType = "MD5";

    /**
     * 签名
     */
    public String paySign;

    /**
     * 预支付id
     */
    public String prePayId;

    /**
     * Constructs a <code>WxPayConfig</code>
     *
     * @since v1.0.7
     */
    public WxPayConfig(String appId, String prepayId, String appPayKey) {
        this.setNonceStr(String.valueOf(RandomUtils.nextInt(1000, 9999)));
        this.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
        this.setPrePayId(prepayId);
        this.setPackAge("prepay_id=" + prepayId);
        this.setAppId(appId);
        Map<String, String> params = Maps.newHashMap();
        params.put("appId", this.appId);
        params.put("nonceStr", this.nonceStr);
        params.put("timeStamp", this.timeStamp);
        params.put("package", this.packAge);
        params.put("signType", this.signType);
        this.setPaySign(paySign = WechatPlatForm.sign(params, appPayKey));
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackAge() {
        return packAge;
    }

    public void setPackAge(String packAge) {
        this.packAge = packAge;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }
}
