package com.edaochina.shopping.common.wxpay.util;

import com.edaochina.shopping.common.utils.AES;
import com.edaochina.shopping.common.utils.StringUtil;
import com.edaochina.shopping.common.utils.WxPKCS7Encoder;
import com.edaochina.shopping.common.wxpay.config.WxPayConfig;
import com.edaochina.shopping.common.wxpay.model.OrderInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 微信服务帮助类
 * Created by Administrator on 2018\8\25 0025.
 */
public class WXHelp {

    private static final Logger LOGGER = LoggerFactory.getLogger(WXHelp.class);

    private static final String ALGORITHM = "AES";

    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * 转码
     *
     * @param content 内容
     * @param charset 字符集
     * @return
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }


    /**
     * 转换成xml参数
     *
     * @author
     * @date 2016/6/16
     */
    public static String creatxmlParam(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        String sign = creatSign_md5(params);
        // 拼成xml
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        xml.append("\r\n");
        Collections.sort(keys);
        for (String key : keys) {
            String value = params.get(key);
            if (value == null || "".equals(value)) {
                continue;
            }
            xml.append("<" + key + ">");
            xml.append(value);
            xml.append("</" + key + ">");
            xml.append("\r\n");
        }
        xml.append("<sign>");
        xml.append(sign);
        xml.append("</sign>");
        xml.append("\r\n");
        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 生成统一下单sign签名,忽略value为空的
     *
     * @author
     * @date 2016/6/15
     */
    public static String creatSign_md5(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        prestr = getPreStr(params, keys, prestr);
        String stringSignTemp = "";
        stringSignTemp = prestr + "key=" + WxPayConfig.getKEY();
        return DigestUtils.md5Hex(getContentBytes(stringSignTemp, "UTF-8")).toUpperCase();
    }

    /**
     * 生成统一下单sign签名,忽略value为空的
     *
     * @author
     * @date 2016/6/15
     */
    public static String creatSign(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (value == null || "".equals(value)) {
                continue;
            }
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        String stringSignTemp = "";
        stringSignTemp = prestr + "&" + "key=" + WxPayConfig.getKEY();
        return DigestUtils.md5Hex(getContentBytes(stringSignTemp, "UTF-8")).toUpperCase();
    }

    /**
     * 生成统一下单参数
     *
     * @author
     * @date 2016/6/15
     */
    public static String toUnifParam(OrderInfo orderInfo) {
        Map<String, String> params = new HashMap<String, String>();
        if (WxPayConfig.TRADETYPE_APP.equals(orderInfo.getTradeType())) {
            getAppParams(orderInfo, params);
        } else {
            getJSParams(orderInfo, params);
        }
        return creatxmlParam(params);
    }

    private static void getJSParams(OrderInfo orderInfo, Map<String, String> params) {
        params.put("appid", orderInfo.getAppId());// 微信开放平台审核通过的应用APPID
        params.put("mch_id", orderInfo.getMchId());// 微信支付分配的商户号
        params.put("device_info", orderInfo.getDeviceInfo());// 终端设备号(门店号或收银设备ID)，默认请传"WEB"
        params.put("nonce_str", orderInfo.getNonceStr());// 随机字符串，不长于32位。
        params.put("attach", orderInfo.getAttach());// 传递数据包
        params.put("body", orderInfo.getBody());
        params.put("out_trade_no", orderInfo.getOutTradeNo());// 商户系统内部的订单号,32个字符内、可包含字母,OrderUtil.getOrderNum()
        params.put("fee_type", orderInfo.getFeeType());// 货币类型
        params.put("total_fee", String.valueOf(orderInfo.getTotalFee()));
        params.put("spbill_create_ip", orderInfo.getSpbillCreateIp());// 用户端实际ip
        params.put("notify_url", orderInfo.getNotifyUrl());// 接收微信支付异步通知回调地址，
        params.put("trade_type", orderInfo.getTradeType());// 支付类型
        params.put("limit_pay", orderInfo.getLimitPay());// no_credit--指定不能使用信用卡支付
        params.put("openid", orderInfo.getOpenId());
    }

    private static void getAppParams(OrderInfo orderInfo, Map<String, String> params) {
        params.put("appid", orderInfo.getAppId());// 微信开放平台审核通过的应用APPID
        params.put("mch_id", orderInfo.getMchId());// 微信支付分配的商户号
        params.put("attach", orderInfo.getAttach());// 传递数据包
        params.put("body", orderInfo.getBody());
        params.put("nonce_str", orderInfo.getNonceStr());// 随机字符串，不长于32位。
        params.put("notify_url", orderInfo.getNotifyUrl());// 接收微信支付异步通知回调地址，
        params.put("out_trade_no", orderInfo.getOutTradeNo());// 商户系统内部的订单号,32个字符内、可包含字母,OrderUtil.getOrderNum()
        params.put("spbill_create_ip", orderInfo.getSpbillCreateIp());// 用户端实际ip
        params.put("total_fee", String.valueOf(orderInfo.getTotalFee()));
        params.put("trade_type", orderInfo.getTradeType());// 支付类型
    }

    /**
     * 生成随机字符串
     *
     * @return
     */
    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * 组装异步回调信息
     *
     * @param reader
     */
    public static StringBuilder getXmlStr(BufferedReader reader) {
        StringBuilder xmlStr = new StringBuilder();
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                xmlStr.append(line);
            }
        } catch (Exception e) {
            LOGGER.error("连接超时！");
        }
        return xmlStr;
    }

    /**
     * 验证签名
     *
     * @param map 通知返回来的参数
     * @return 验证结果
     */
    public static boolean cheakSign(Map<String, String> map) {
        if (map.get("sign") != null) {
            String sign1 = map.get("sign");
            map.remove("sign");
            String sign2 = creatSign(map);
            if (sign1.equals(sign2)) {
                return true;
            }
        }
        return false;
    }

    private static String getPreStr(Map<String, String> params, List<String> keys, String prestr) {
        for (String key : keys) {
            String value = params.get(key);
            if (value == null || "".equals(value)) {
                continue;
            }
            prestr = prestr + key + "=" + value + "&";
        }
        return prestr;
    }

    /**
     * 解密数据手机号
     *
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptedData,
                                 String sessionKey, String iv) {
        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(org.apache.commons.codec.binary.Base64.decodeBase64(encryptedData), org.apache.commons.codec.binary.Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if (null != resultByte && resultByte.length > 0) {
                result = new String(WxPKCS7Encoder.decode(resultByte));
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密微信退款回调内容
     *
     * @param reqInfo
     * @param mchKey
     * @return
     */
    public static String decryptRefundCallBack(String reqInfo, String mchKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        SecretKeySpec secretKey = new SecretKeySpec(StringUtil.MD51(mchKey).toLowerCase().getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decode = java.util.Base64.getDecoder().decode(reqInfo);
        byte[] doFinal = cipher.doFinal(decode);
        return new String(doFinal, "utf-8");
    }

}
