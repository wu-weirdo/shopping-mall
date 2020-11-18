package com.edaochina.shopping.common.utils.weixinpay;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author zzk
 * @Date 2018/12/12
 */
public class WechatPlatForm {
    private static final Logger log = LoggerFactory.getLogger(WechatPlatForm.class);

    // code 换取 session_key
    private static final String CODE_APPLY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";

    /**
     * 统一下单接口
     */
    private static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * code 换取 session_key
     *
     * @param code
     * @param appId
     * @param appSecret
     * @return json string data
     * @author Calm
     * @created 2017年8月23日 下午6:59:09
     * @since v1.0
     */
    public static String codeApply(String code, String appId, String appSecret) {
        String codeUrl = MessageFormat.format(CODE_APPLY_URL, appId, appSecret, code);
        return get(codeUrl);
    }

    /**
     * GET方式向微信服务器请求数据
     *
     * @param url
     * @return
     * @author Calm
     * @created 2017年8月23日 下午6:58:24
     * @since v1.0
     */
    public static String get(String url) {
        String result = "";
        try {
            result = HttpUtils.get(url);
        } catch (Exception e) {
            log.error("Error occurred when post data to wechat", e);
        }
        return result;
    }

    /**
     * 调用微信统一下单API得到prepay_id<br/>
     * http://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
     *
     * @created 2018-12-12 18:31:50
     */
    public static Map<String, String> getJSAPIPrepayId(String appId, String mchId, String payKey, String notifyUrl, String openId, String title, String detail, String orderId, int totalFee, String serverIp, String attach) {
        Map<String, String> param = Maps.newHashMap();
        param.put("appid", appId);
        param.put("mch_id", mchId);
        // 随机字符串
        param.put("nonce_str", (Math.random() + "").replaceAll("\\.", ""));
        param.put("body", title);
        param.put("detail", detail);
        param.put("out_trade_no", orderId);
        param.put("total_fee", totalFee + "");
        param.put("spbill_create_ip", serverIp);
        param.put("notify_url", notifyUrl);
        param.put("trade_type", "JSAPI");
        param.put("attach", attach);
        param.put("openid", openId);
        param.put("sign", sign(param, payKey));
        // 准备发送数据
        String xmlPost = mapToXml(param);
        String respXmlData = post(UNIFIEDORDER, xmlPost);
        log.info("respXmlData: {}");
        if (Strings.isNullOrEmpty(respXmlData)) {
            log.error("payUnified");
            return null;
        }
        if (StringUtils.isBlank(respXmlData)) {
            return null;
        }
        Map<String, String> map = xmlToMap(respXmlData);
        // 这里可以校验一下签名
        String returnCode = map.get("return_code");
        if (!Objects.equal("SUCCESS", returnCode)) {
            log.error("payUnified, return_code=" + returnCode + ",return_msg=" + map.get("return_msg"));
            return null;
        }
        return map;
    }


    /**
     * 微信支付签名
     *
     * @param map
     * @param appPayKey
     * @return
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v1.0
     */
    public static String sign(Map map, String appPayKey) {
        String keyValPairs = concatQueryStringFromMap(map);
        keyValPairs = keyValPairs + "&key=" + appPayKey;
        String signStr = DigestUtils.md5Hex(keyValPairs).toUpperCase();
        return signStr;
    }

    private static String concatQueryStringFromMap(Map<String, String> map) {
        Validate.isTrue(!MixHelper.isEmpty(map), "Parameter map couldn't be blank");
        // key排序
        Map<String, String> sortMap = new TreeMap(map);
        sortMap = Maps.filterValues(sortMap, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                // 过滤Map中value值为空的元素
                return !Strings.isNullOrEmpty(input);
            }
        });
        String keyValPairs = Joiner.on("&").withKeyValueSeparator("=").join(sortMap);
        return keyValPairs;
    }

    public static String mapToXml(Map<String, String> map) {
        Validate.isTrue(!MixHelper.isEmpty(map), "Parameter map couldn't be blank");
        Map<String, String> sortMap = new TreeMap<String, String>(map);
        sortMap = Maps.filterValues(sortMap, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                // 过滤Map中value值为空的元素
                return !Strings.isNullOrEmpty(input);
            }
        });
        StringBuilder sb = new StringBuilder("<xml>");
        for (Iterator<Map.Entry<String, String>> iterator = sortMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            Joiner.on("").appendTo(sb, "<", entry.getKey(), "><![CDATA[", entry.getValue(), "]]></", entry.getKey(),
                    ">");
        }
        return sb.toString() + "</xml>";
    }

    public static Map<String, String> xmlToMap(String xml) {
        Validate.isTrue(!Strings.isNullOrEmpty(xml), "Parameter xml couldn't be blank");
        Element rootElment;
        try {
            rootElment = XmlUtils.getRootElementFromString(xml);
        } catch (Exception e) {
            return null;
        }
        if (null != rootElment) {
            NodeList nodes = rootElment.getChildNodes();
            if (null != nodes) {
                Map<String, String> mss = Maps.newHashMap();
                for (int i = 0; i < nodes.getLength(); i++) {
                    mss.put(nodes.item(i).getNodeName(), (nodes.item(i).getTextContent()));
                }
                return mss;
            }
        }
        return null;
    }

    /**
     * POST方式向微信服务器请求数据
     *
     * @param url
     * @param postData
     * @return
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v1.0
     */
    public static String post(String url, String postData) {
        String result = "";

        try {
            result = HttpUtils.postXml(url, postData);
        } catch (Exception e) {
            log.error("Error occurred when post data to wechat  :\n {}", e.getMessage());
        }
        return result;
    }
}
