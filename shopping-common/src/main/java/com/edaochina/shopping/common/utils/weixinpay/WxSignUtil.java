package com.edaochina.shopping.common.utils.weixinpay;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信授权登录相关工具
 */
public class WxSignUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxSignUtil.class);

    /**
     * 组装 异步回调返回的参数
     *
     * @throws UnsupportedEncodingException
     * @author
     * @date 2016/6/15
     */
    public static Map<String, String> parseNotify(String content) throws UnsupportedEncodingException {
        LOGGER.info("微信 异步回调返回xml数据：" + content);
        Map<String, String> map = new HashMap<String, String>();
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
            document = saxReader.read(new ByteArrayInputStream(content.getBytes("UTF-8")));
            Element rootElt = document.getRootElement();
            String return_code = rootElt.elementText("return_code");// 返回状态码
            LOGGER.info("return_code" + return_code);
            if (return_code.equals("SUCCESS")) {
                String result_code = rootElt.elementText("result_code");// 业务结果
                LOGGER.info("result_code" + result_code);
                if (result_code.equals("SUCCESS")) {
                    map.put("appid", rootElt.elementText("appid"));// 接口提交的应用ID
                    map.put("mch_id", rootElt.elementText("mch_id"));// 接口提交的商户号
                    map.put("detail", rootElt.elementText("detail"));// detail
                    map.put("nonce_str", rootElt.elementText("nonce_str"));// 微信返回的随机字符串
                    map.put("sign", rootElt.elementText("sign"));// 微信返回的签名
                    map.put("result_code", rootElt.elementText("result_code"));// 业务结果
                    map.put("return_code", rootElt.elementText("return_code"));// 业务结果
                    map.put("openid", rootElt.elementText("openid"));// 用户标识
                    map.put("trade_type", rootElt.elementText("trade_type"));// 接口提交的交易类型
                    map.put("bank_type", rootElt.elementText("bank_type"));// 银行类型
                    map.put("total_fee", rootElt.elementText("total_fee"));// 总金额
                    map.put("cash_fee", rootElt.elementText("cash_fee"));// 现金支付金额
                    map.put("transaction_id", rootElt.elementText("transaction_id"));// 微信支付订单号
                    map.put("out_trade_no", rootElt.elementText("out_trade_no"));// 商户订单号
                    map.put("time_end", rootElt.elementText("time_end"));// 支付完成时间
                    map.put("sub_mch_id", rootElt.elementText("sub_mch_id"));
                    map.put("fee_type", rootElt.elementText("fee_type"));// 货币种类
                    map.put("is_subscribe", rootElt.elementText("is_subscribe"));// 是否关注公众账号
                    map.put("cash_fee_type", rootElt.elementText("cash_fee_type"));// 现金支付货币类型
                    map.put("coupon_fee", rootElt.elementText("coupon_fee"));// 代金券或立减优惠金额
                    map.put("coupon_count", rootElt.elementText("coupon_count"));// 代金券或立减优惠使用数量
                    map.put("attach", rootElt.elementText("attach"));// 商家数据包
                    map.put("device_info", rootElt.elementText("device_info"));
                } else {
                    LOGGER.info("result_code" + rootElt.elementText("result_code"));// 列表详见错误码列表
                    LOGGER.info("err_code_des" + rootElt.elementText("err_code_des"));// 结果信息描述
                }
            } else {
                LOGGER.info("组装 统一下单返回的参数失败");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> parseXmlStr(String content) throws UnsupportedEncodingException {
        LOGGER.info("微信 异步回调返回xml数据：" + content);
        Map<String, String> map = new HashMap<String, String>();
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
            document = saxReader.read(new ByteArrayInputStream(content.getBytes("UTF-8")));
            Element rootElt = document.getRootElement();
            List<Element> elementList = rootElt.elements();
            elementList.forEach(element -> {
                map.put(element.getName(), element.getText());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
