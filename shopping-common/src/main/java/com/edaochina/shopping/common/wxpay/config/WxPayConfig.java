package com.edaochina.shopping.common.wxpay.config;


import com.edaochina.shopping.common.utils.RSACoder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.ssl.SSLContexts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PublicKey;

/**
 * 微信支付配置信息
 *
 * @author 24h
 */
public class WxPayConfig {

    /**
     * 获取token的地址
     */
    public static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
    /**
     * 打款到零钱
     */
    public static String PAY_TO_LOOSE_CHANGE = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    /**
     * 获取二维码地址
     */
    public static String QR_IMAGE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";

    public static String QR_IMAGE_URL_ONLY = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=";
    // 企业付款到零钱查询
    public static String PAY_TO_LOOSE_CHANGE_QUERY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";


    // 企业付款到银行卡查询
    public static String PAY_TO_BNAK_QUERY = "https://api.mch.weixin.qq.com/mmpaysptrans/query_bank";
    /**
     * 微信开放平台审核通过的应用APPID
     */
    public static String APP_ID = "wx032c7b34325225ae";
    /**
     * 应用秘钥
     */
    public static String AppSecret = "c67930d6327a8df2a7a2b2e8e7ce2113";


    public static String APP_APP_ID = "wx92bfab4d59a0e4a9";

    public static String APP_APP_SECRET = "fd8f68ecb69f83b1cdacda382cb4ee92";
    /**
     * 微信支付分配的商户号
     */
    public static String MCH_ID = "1524504971";
    /**
     * 支付秘钥
     */
    public static String KEY = "12345678123456781234567812cdwljs";
    /**
     * 统一下单URL
     */
    public static String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 退款URL
     */
    public static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public static String REFUND_CALL_BACK_URL = "https://app.chengd24h.com/app/wxpay/refundCallBack";
    /**
     * 打款到银行卡url
     */
    public static String WITHDRAWAL_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";

    /**
     * 获取uid
     */
    public static String GETUID_URL = "https://api.weixin.qq.com/wxa/getpaidunionid?access_token=";
    /**
     * 交易类型，小程序支付的固定值为JSAPI
     */
    public static String TRADETYPE_JSAPI = "JSAPI";

    /**
     * 交易类型，小程序支付的固定值为JSAPI
     */
    public static String TRADETYPE_APP = "APP";

    public static PublicKey PUBLIC_KEY = null;

    public static String publicKeyPath = "wx_public_key.pem";

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final int CONNECT_TIME_OUT = 5000; // 链接超时时间3秒

    public static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT)
            .build();

    public static SSLContext WX_SSL_CONTEXT = null; // 微信支付ssl证书

    static {
        Resource resource = new ClassPathResource("apiclient_cert.p12");
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            char[] keyPassword = WxPayConfig.getMchId().toCharArray(); // 证书密码
            keystore.load(resource.getInputStream(), keyPassword);
            WX_SSL_CONTEXT = SSLContexts.custom().loadKeyMaterial(keystore, keyPassword).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 签名方式，固定值
     */
    private static String SIGNTYPE = "MD5";

    /**
     * 设备信息
     */
    private static String DEVICE_INFO_WEB = "WEB";


    public static String getAppId() {
        return APP_ID;
    }

    public static String getMchId() {
        return MCH_ID;
    }

    public static String getKEY() {
        return KEY;
    }

    public static String getUnifiedorderUrl() {
        return UNIFIEDORDER_URL;
    }

    public static String getSIGNTYPE() {
        return SIGNTYPE;
    }

    public static String getTradetypeJsapi() {
        return TRADETYPE_JSAPI;
    }

    public static String getAppSecret() {
        return AppSecret;
    }

    public static String getDeviceInfoWeb() {
        return DEVICE_INFO_WEB;
    }

    public static String getRefundUrl() {
        return REFUND_URL;
    }

    public static void setRefundUrl(String refundUrl) {
        REFUND_URL = refundUrl;
    }

    public static String getWithdrawalUrl() {
        return WITHDRAWAL_URL;
    }

    public static void setWithdrawalUrl(String withdrawalUrl) {
        WITHDRAWAL_URL = withdrawalUrl;
    }

    public static String getPayToLooseChange() {
        return PAY_TO_LOOSE_CHANGE;
    }

    public static PublicKey getPublicKey() {
        if (PUBLIC_KEY == null) {
            try {
                Resource resource = new ClassPathResource(publicKeyPath);
                InputStream inputStream = resource.getInputStream();
                PUBLIC_KEY = RSACoder.getPublicKey(inputStream, "RSA");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return PUBLIC_KEY;
    }

    public static void main(String[] args) throws Exception {
        org.bouncycastle.asn1.pkcs.RSAPublicKey rsaPublicKey = org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(
                org.bouncycastle.util.encoders.Base64.decode("MIIBCgKCAQEArsFo0/nsFwIny3Daj52l4ps/mWcnEcJNwsJB3jAR/GijsWCOvUfF\n" +
                        "d/yJjdT6gZS9qGix0aDc92ikUFhwuHhhgWi9HQDN7O/jK0mrTfXdyCR9VT4Qxw9c\n" +
                        "veJoRTrvUurJ92voK0VlgAVT1hkjzvSyRDWC29DpgoCjMIIU0TARI01etwtn+++R\n" +
                        "XwOZ7s5cMszFvh5imZrML+CvAKTbRA+ewqXxSWG7mCiEvxlBzbaAnFNPA4IVEqkU\n" +
                        "CFDl0MYBmeQlamC6cej1pWaQDmnwa4ONfDUQ1HKYLBUROpGOtviZgiMI9Sv1WyRu\n" +
                        "tBW0ydWqFahwP8MJkjxao4m7NsBItDoj0QIDAQAB"));

        java.security.spec.RSAPublicKeySpec publicKeySpec = new java.security.spec.RSAPublicKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());

        java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");

        java.security.PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
    }

}
