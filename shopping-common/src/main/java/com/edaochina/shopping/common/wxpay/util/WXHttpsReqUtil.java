package com.edaochina.shopping.common.wxpay.util;


import com.edaochina.shopping.common.utils.RSACoder;
import com.edaochina.shopping.common.wxpay.config.WxPayConfig;
import com.edaochina.shopping.common.wxpay.model.RefundInfo;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.edaochina.shopping.common.wxpay.util.WXHelp.creatxmlParam;


/**
 * @author 24h
 */
public class WXHttpsReqUtil {

    private static Logger logger = LoggerFactory.getLogger(WXHttpsReqUtil.class);


    public static String getWithdrawalRecordReq(WithdrawalRecord withdrawalRecord, String partnerTradeNo) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("nonce_str", WXHelp.CreateNoncestr());// 随机字符串，不长于32位。
        params.put("mch_id", WxPayConfig.getMchId());// 微信支付分配的商户号
        params.put("partner_trade_no", partnerTradeNo);// 商户企业付款单号
        params.put("enc_bank_no", Base64.encode(RSACoder.encrypt(withdrawalRecord.getBankNumber().getBytes(StandardCharsets.UTF_8), WxPayConfig.getPublicKey(), 2048, 11, "RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING")));// 收款方银行卡号
        params.put("enc_true_name", Base64.encode(RSACoder.encrypt(withdrawalRecord.getUserName().getBytes(StandardCharsets.UTF_8), WxPayConfig.getPublicKey(), 2048, 11, "RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING")));//收款方用户名
        // 收款方开户行
        params.put("bank_code", withdrawalRecord.getBankCode());
        params.put("amount", withdrawalRecord.getToAccountMoney().multiply(new BigDecimal(100)).intValue() + "");// 付款金额
        params.put("desc", "提现到银行卡");//付款说明
        return creatxmlParam(params);
    }


    /**
     * 生成退款参数
     *
     * @author
     * @date 2016/6/15
     */
    public static String toUnifParamForRefund(RefundInfo refundInfo) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", refundInfo.getAppId());// 微信开放平台审核通过的应用APPID
        params.put("nonce_str", refundInfo.getNonceStr());// 随机字符串，不长于32位。
        params.put("mch_id", refundInfo.getMchId());// 微信支付分配的商户号
        params.put("transaction_id", refundInfo.getTransactionId());// 微信订单号
        params.put("out_trade_no", refundInfo.getOutTradeNo());// 商户订单号
        params.put("out_refund_no", refundInfo.getOutRefundNo());// 商户退款单号
        params.put("total_fee", refundInfo.getTotalFee());// 订单金额
        params.put("refund_fee", refundInfo.getRefundFee());// 退款金额
        params.put("sign", null);// 签名
        params.put("notify_url", refundInfo.getCallBackUrl());
        return creatxmlParam(params);
    }


    /**
     * 生成提现到零钱请求参数
     *
     * @param withdrawalRecord
     * @return
     */
    public static String getLooseChangeReq(WithdrawalRecord withdrawalRecord, String orderId, String ip) throws UnknownHostException {
        Map<String, String> params = new HashMap<>();
        params.put("mch_appid", WxPayConfig.getAppId());// 申请商户号的appid或商户号绑定的appid
        params.put("mchid", WxPayConfig.getMchId());// 微信支付分配的商户号
        params.put("nonce_str", WXHelp.CreateNoncestr());// 随机字符串，不长于32位。
        params.put("sign", "");// 签名
        params.put("partner_trade_no", orderId);// 商户订单号
        params.put("openid", withdrawalRecord.getOpenId());// 用户的openid
        params.put("check_name", "NO_CHECK");// 校验用户姓名
        params.put("amount", withdrawalRecord.getToAccountMoney().multiply(new BigDecimal(100)).intValue() + "");// 金额
        params.put("desc", "提现金额为:" + withdrawalRecord.getApplyMoney() + ",手续费为:" + withdrawalRecord.getChargeFee());// 企业付款备注
        params.put("spbill_create_ip", ip);// Ip地址
        return creatxmlParam(params);
    }

    /**
     * 生成查询企业零钱付款参数
     *
     * @param tradeNo
     * @return
     */
    public static String getQueryPayToLooseChangeReq(String tradeNo) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", WxPayConfig.getAppId());// 申请商户号的appid或商户号绑定的appid
        params.put("mch_id", WxPayConfig.getMchId());// 微信支付分配的商户号
        params.put("nonce_str", WXHelp.CreateNoncestr());// 随机字符串，不长于32位。
        params.put("sign", "");// 签名
        params.put("partner_trade_no", tradeNo);// 商户订单号
        return creatxmlParam(params);
    }


    /**
     * 获取ssl connection链接
     */
    private static SSLConnectionSocketFactory getSSLConnectionSocket() {
        return new SSLConnectionSocketFactory(WxPayConfig.WX_SSL_CONTEXT, new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    }

    public static String creatSign_md5(RefundInfo refundInfo) {
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("appid", refundInfo.getAppId()); // 公众账号appid
        parm.put("mch_id", refundInfo.getMchId()); // 商户号
        parm.put("nonce_str", refundInfo.getNonceStr()); // 随机字符串
        parm.put("transaction_id", refundInfo.getTransactionId()); // 微信订单号
        parm.put("out_trade_no", refundInfo.getOutTradeNo()); // 商户订单号
        parm.put("out_refund_no", refundInfo.getOutRefundNo()); // 商户退款单号
        parm.put("total_fee", refundInfo.getTotalFee()); // 订单金额
        parm.put("refund_fee", refundInfo.getRefundFee()); // 退款金额
        return WXHelp.creatSign_md5(parm);
    }


    public static String httpsReq(String url, String req) {
        logger.info("wxHttpsReq :url:" + url + ",req:" + req);
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = new HttpPost(url);
        String body = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.custom().setDefaultRequestConfig(WxPayConfig.REQUEST_CONFIG)
                    .setSSLSocketFactory(getSSLConnectionSocket()).build();
            httpPost.setEntity(new StringEntity(req, WxPayConfig.DEFAULT_CHARSET));
            response = httpClient.execute(httpPost);
            body = EntityUtils.toString(response.getEntity(), WxPayConfig.DEFAULT_CHARSET);
            logger.info("wxHttpsReq result" + body);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return body;
    }

    public static Map<String, String> parseWxResponseStr(String responseStr) throws UnsupportedEncodingException {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        Document document;
        try {
            document = saxReader.read(new ByteArrayInputStream(responseStr.getBytes(StandardCharsets.UTF_8)));
            Element rootElt = document.getRootElement();
            String return_code = rootElt.elementText("return_code");// 返回状态码
            if (return_code.equals("SUCCESS")) {
                map.put("mch_appid", WxPayConfig.getAppId());// 接口提交的应用ID
                map.put("mchid", WxPayConfig.getMchId());// 接口提交的商户号
                List<Element> elementList = rootElt.elements();
                // 解析数据
                for (Element element : elementList) {
                    map.put(element.getName(), element.getText());
                }
            } else {
                map.put("return_msg", rootElt.elementText("return_msg"));
                map.put("result_code", rootElt.elementText("result_code"));
                map.put("err_code", rootElt.elementText("err_code"));
                map.put("err_code_des", rootElt.elementText("err_code_des"));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String getQueryPayToBankReq(String tradeNo) {
        Map<String, String> params = new HashMap<>();
        params.put("mch_id", WxPayConfig.getMchId());// 微信支付分配的商户号
        params.put("nonce_str", WXHelp.CreateNoncestr());// 随机字符串，不长于32位。
        params.put("sign", "");// 签名
        params.put("partner_trade_no", tradeNo);// 商户订单号
        return creatxmlParam(params);
    }
}
