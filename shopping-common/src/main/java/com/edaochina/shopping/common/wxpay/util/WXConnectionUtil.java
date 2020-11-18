package com.edaochina.shopping.common.wxpay.util;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.common.utils.http.HttpClientUtilsTool;
import com.edaochina.shopping.common.utils.weixinpay.HttpUtils;
import com.edaochina.shopping.common.wxpay.config.WxPayConfig;
import com.edaochina.shopping.common.wxpay.model.OrderInfo;
import com.edaochina.shopping.common.wxpay.model.OrderReturnInfo;
import com.edaochina.shopping.common.wxpay.model.RefundInfo;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.edaochina.shopping.common.wxpay.util.WXHelp.creatxmlParam;

/**
 * 微信连接请求工具类
 *
 * @author jintian
 * @date 2019/4/30 15:33
 */
public class WXConnectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WXConnectionUtil.class);

    private static String APP_TOKEN = "wx_app_token";

    /**
     * 获取微信token
     *
     * @return
     */
    public static String getToken() {
        String token = null;
        //token = RedisTool.get(APP_TOKEN);
        if (token != null) {
            return token;
        } else {
            String responsJson = HttpUtils.get(WxPayConfig.TOKEN_URL + "appid=" + WxPayConfig.getAppId() + "&secret=" + WxPayConfig.getAppSecret());
            JSONObject json = JSONObject.parseObject(responsJson);
            token = json.getString("access_token");
            // RedisTool.set(APP_TOKEN, token,6000);
        }
        return token;
    }

    /**
     * 下载微信二维码
     *
     * @param scene
     * @param page
     * @param fileName
     */
    public static void getQrImage(String scene, String page, String fileName) {
        JSONObject req = new JSONObject();
        req.put("scene", scene);
        req.put("page", page);
        req.put("width", 280);
        req.put("auto_color", true);
        JSONObject js = new JSONObject();
        js.put("r", 236);
        js.put("g", 136);
        js.put("b", 185);
        req.put("line_color", js);
        req.put("is_hyaline", true);
        HttpUtils.postJsonDownloadFile(WxPayConfig.QR_IMAGE_URL + WXConnectionUtil.getToken(),
                req.toJSONString(), fileName);
    }

    public static void getQrOnlyImage(String scene, String page, String fileName) {
        JSONObject req = new JSONObject();
        req.put("path", page + "?" + scene);
        req.put("width", 280);
        HttpUtils.postJsonDownloadFile(WxPayConfig.QR_IMAGE_URL + WXConnectionUtil.getToken(),
                req.toJSONString(), fileName);
    }

    /**
     * 调起微信支付发送 POST
     *
     * @throws UnsupportedEncodingException
     * @author
     * @date 2016/6/15
     */
    public static String pay(OrderInfo orderInfo) {
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(WxPayConfig.getUnifiedorderUrl());
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            String req = WXHelp.toUnifParam(orderInfo);
            LOGGER.info("req :" + req);
            // 发送请求参数
            out.print(req);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            LOGGER.error("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 组装 统一下单返回的参数
     *
     * @throws UnsupportedEncodingException
     * @author
     * @date 2016/6/15
     */
    public static OrderReturnInfo parseUnif(String content) throws UnsupportedEncodingException {
        LOGGER.info("微信统一下单返回xml数据：" + content);
        SAXReader saxReader = new SAXReader();
        Document document;
        OrderReturnInfo orderReturnInfo = null;
        try {
            document = saxReader.read(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            Element rootElt = document.getRootElement();
            String return_code = rootElt.elementText("return_code");// 返回状态码
            LOGGER.info("return_code" + return_code);
            if (return_code.equals("SUCCESS")) {
                orderReturnInfo = new OrderReturnInfo();
                String result_code = rootElt.elementText("result_code");// 业务结果
                LOGGER.info("result_code" + result_code);
                if (result_code.equals("SUCCESS")) {
                    orderReturnInfo.setAppId(WxPayConfig.getAppId());// 接口提交的应用ID
                    orderReturnInfo.setMchId(WxPayConfig.getMchId());// 接口提交的商户号
                    orderReturnInfo.setNonceStr(rootElt.elementText("nonce_str"));// 微信返回的随机字符串
                    orderReturnInfo.setSign(rootElt.elementText("sign"));// 微信返回的签名
                    orderReturnInfo.setPrepayId(rootElt.elementText("prepay_id"));// 微信生成的预支付回话标识,用于后续接口调用中使用，该值有效期为2小时
                    orderReturnInfo.setReturnCode(rootElt.elementText("return_code"));// SUCCESS/FAIL
                    orderReturnInfo.setTradeType(rootElt.elementText("trade_type"));// 接口提交的交易类型
                    orderReturnInfo.setReturnMsg(rootElt.elementText("return_msg"));// 返回信息
                    orderReturnInfo.setDeviceInfo(rootElt.elementText("device_info"));// 接口提交的终端设备号
                    orderReturnInfo.setWebUrl(rootElt.elementText("mweb_url"));
                } else {
                    LOGGER.info("result_code" + rootElt.elementText("result_code"));
                    LOGGER.info("err_code_des" + rootElt.elementText("err_code_des"));
                }
            } else {
                LOGGER.error("组装 统一下单返回的参数失败");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return orderReturnInfo;
    }

    /**
     * @param refundInfo 退款参数
     * @return 请求失败返回null
     * @description 功能描述: post https请求，服务器双向证书验证
     */
    public static String refund(RefundInfo refundInfo) {
        return WXHttpsReqUtil.httpsReq(WxPayConfig.getRefundUrl(), WXHttpsReqUtil.toUnifParamForRefund(refundInfo));
    }

    /**
     * 打款道银行卡
     *
     * @param withdrawalRecord
     * @return
     * @throws Exception
     */
    public static String withdrawal(WithdrawalRecord withdrawalRecord, String partnerTradeNo) throws Exception {
        return WXHttpsReqUtil.httpsReq(WxPayConfig.getWithdrawalUrl(), WXHttpsReqUtil.getWithdrawalRecordReq(withdrawalRecord, partnerTradeNo));
    }

    /**
     * 发起提现到零钱请求
     *
     * @param record
     * @return
     */
    public static String payToLooseChange(WithdrawalRecord record, String orderId, String ip) throws UnknownHostException {
        return WXHttpsReqUtil.httpsReq(WxPayConfig.getPayToLooseChange(), WXHttpsReqUtil.getLooseChangeReq(record, orderId, ip));
    }


    /**
     * 发起查询提现到零钱请求
     *
     * @param tradeNo
     * @return
     */
    public static String queryPayToLooseChange(String tradeNo) {
        return WXHttpsReqUtil.httpsReq(WxPayConfig.PAY_TO_LOOSE_CHANGE_QUERY, WXHttpsReqUtil.getQueryPayToLooseChangeReq(tradeNo));
    }

    /**
     * 获取公钥
     *
     * @return
     */
    public static String getPublicKey() {
        Map<String, String> parameters = new HashMap<String, String>();
        String nonce_str = WXHelp.CreateNoncestr();
        parameters.put("mch_id", WxPayConfig.getMchId());
        parameters.put("nonce_str", nonce_str);
        parameters.put("sign_type", "MD5");
        String req = creatxmlParam(parameters);
        String url = "https://fraud.mch.weixin.qq.com/risk/getpublickey";
        return WXHttpsReqUtil.httpsReq(url, req);
    }

    public static String queryPayToBank(String tradeNo) {
        return WXHttpsReqUtil.httpsReq(WxPayConfig.PAY_TO_BNAK_QUERY, WXHttpsReqUtil.getQueryPayToBankReq(tradeNo));
    }

    public static String getUid(String openId) {
        return HttpClientUtilsTool.httpGet(WxPayConfig.GETUID_URL + getToken() + "&openid=" + openId).getString("unionid");
    }

    public static void getGoodsQrImage(String goodsId, String goodsQr, String fileName) {
        JSONObject req = new JSONObject();
        req.put("scene", goodsId);
        req.put("page", goodsQr);
        req.put("width", 280);
        req.put("auto_color", false);
        JSONObject js = new JSONObject();
        js.put("r", 236);
        js.put("g", 136);
        js.put("b", 185);
        req.put("line_color", js);
        req.put("is_hyaline", false);
        HttpUtils.postJsonDownloadFile(WxPayConfig.QR_IMAGE_URL + WXConnectionUtil.getToken(),
                req.toJSONString(), fileName);
    }
}
