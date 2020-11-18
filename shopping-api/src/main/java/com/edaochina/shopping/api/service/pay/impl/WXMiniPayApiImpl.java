package com.edaochina.shopping.api.service.pay.impl;

import com.alibaba.fastjson.JSON;
import com.edaochina.shopping.api.service.pay.PayApi;
import com.edaochina.shopping.api.service.trade.PayRecordInfoService;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.weixinpay.WxSignUtil;
import com.edaochina.shopping.common.wxpay.config.WxPayConfig;
import com.edaochina.shopping.common.wxpay.model.OrderInfo;
import com.edaochina.shopping.common.wxpay.model.RefundInfo;
import com.edaochina.shopping.common.wxpay.util.WXConnectionUtil;
import com.edaochina.shopping.common.wxpay.util.WXHelp;
import com.edaochina.shopping.common.wxpay.util.WXHttpsReqUtil;
import com.edaochina.shopping.domain.entity.trade.PayRecordInfo;
import com.edaochina.shopping.domain.entity.trade.PaymentRecord;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 24h
 * @date 2018\8\25 0025
 */
@Service
public class WXMiniPayApiImpl implements PayApi {

    private static Logger logger = LoggerFactory.getLogger(WXMiniPayApiImpl.class);

    @Resource
    PayRecordInfoService payRecordInfoService;

    @Value("${server.ip}")
    private String ip;

    private final WxPayService wxPayService;

    public WXMiniPayApiImpl(WxPayService wxPayService) {
        this.wxPayService = wxPayService;
    }

    @Override
    public WxPayAppOrderResult appPay(OrderInfo orderInfo) throws WxPayException {
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody("成典电商");
        orderRequest.setOutTradeNo(orderInfo.getOutTradeNo());
        //元转成分
        orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(String.valueOf(orderInfo.getTotalFee())));
        orderRequest.setOpenid(orderInfo.getOpenId());
        orderRequest.setSpbillCreateIp(orderInfo.getSpbillCreateIp());
        orderRequest.setTimeStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        orderRequest.setNotifyUrl(orderInfo.getNotifyUrl());
        orderRequest.setTradeType(WxPayConstants.TradeType.APP);
        orderRequest.setNonceStr(WXHelp.CreateNoncestr());
        orderRequest.setAttach(orderInfo.getAttach());
        WxPayAppOrderResult result = wxPayService.createOrder(orderRequest);
        savePayRecord(orderInfo, result.getPrepayId());
        return result;
    }

    @Override
    public WxPayMpOrderResult miniPay(OrderInfo orderInfo) {
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody("成典电商");
        orderRequest.setOutTradeNo(orderInfo.getOutTradeNo());
        orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(orderInfo.getTotalPrice().toString()));
        orderRequest.setOpenid(orderInfo.getOpenId());
        orderRequest.setSpbillCreateIp("127.0.0.1");
        orderRequest.setTimeStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        orderRequest.setNotifyUrl(orderInfo.getNotifyUrl());
        orderRequest.setTradeType(WxPayConstants.TradeType.JSAPI);
        orderRequest.setNonceStr(WXHelp.CreateNoncestr());
        orderRequest.setAttach(orderInfo.getAttach());
        WxPayMpOrderResult result;
        try {
            result = wxPayService.createOrder(orderRequest);
        } catch (WxPayException e) {
            logger.error("支付错误", e);
            throw new YidaoException(e.getReturnMsg());
        }
        savePayRecord(orderInfo, result.getPackageValue().substring(10));
        return result;
    }

    @Override
    public WxPayRefundResult refund(WxPayRefundRequest refundRequest) throws WxPayException {
        WxPayRefundResult result;
        return wxPayService.refund(refundRequest);
    }

    @Override
    public WxPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
        notifyResult.checkResult(wxPayService, WxPayConstants.SignType.MD5, true);
        return notifyResult;
    }

    /**
     * 微信支付--统一下单
     *
     * @return
     */
    @Override
    public Map<String, String> pay(Object obj) throws UnsupportedEncodingException {
        OrderInfo orderInfo = (OrderInfo) obj;
        // 起调微信接口
        String result = WXConnectionUtil.pay(orderInfo);
        /**
         * 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给小程序。
         * 参与签名的字段名为appId，partnerId，prepayId，nonceStr，
         * timeStamp， package。注意：package的值格式为Sign=WXPay
         **/
        result = new String(result.getBytes(), StandardCharsets.UTF_8);
        Map<String, String> parameterMap2 = new HashMap<String, String>();
        Map<String, String> orderReturnInfo = WXHttpsReqUtil.parseWxResponseStr(result);
        logger.info("pay wx result:" + JSON.toJSONString(orderReturnInfo));
        if (orderReturnInfo != null) {
            parameterMap2.put("appId", orderInfo.getAppId());
            parameterMap2.put("package", "prepay_id=" + orderReturnInfo.get("prepay_id"));
        }
        parameterMap2.put("nonceStr", orderInfo.getNonceStr());
        String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        parameterMap2.put("timeStamp", timeStamp);
        parameterMap2.put("signType", "MD5");
        String sign2 = "";
        // 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
        if (WxPayConfig.TRADETYPE_APP.equals(orderInfo.getTradeType())) {
            Map<String, String> appParamsMap = new HashMap<String, String>();
            appParamsMap.put("appid", orderInfo.getAppId());
            appParamsMap.put("noncestr", orderInfo.getNonceStr());
            appParamsMap.put("package", "Sign=WXPay");
            appParamsMap.put("partnerid", orderInfo.getMchId());
            appParamsMap.put("prepayid", orderReturnInfo.get("prepay_id"));
            appParamsMap.put("timestamp", timeStamp);

            logger.info("app pay sign params:" + JSON.toJSONString(appParamsMap));
            sign2 = WXHelp.creatSign_md5(appParamsMap);
            appParamsMap.put("sign", sign2);
            parameterMap2.put("orderInfo", JSON.toJSONString(appParamsMap));
        } else {
            sign2 = WXHelp.creatSign_md5(parameterMap2);
        }

        parameterMap2.put("paySign", sign2);
        parameterMap2.put("orderNumber", orderInfo.getOutTradeNo());
        if (orderReturnInfo != null) {
            parameterMap2.put("prepayId", orderReturnInfo.get("prepay_id"));
        }
        // 添加支付记录 pay_record中
        savePayRecord(orderInfo, orderReturnInfo.get("prepay_id"));
        return parameterMap2;
    }

    /**
     * 微信退款
     *
     * @return
     */
    @Override
    public Map<String, String> refund(Object obj) {
        RefundInfo refundInfo = (RefundInfo) obj;
        // 调起微信退款
        String result = WXConnectionUtil.refund(refundInfo);
        logger.info("refund result:" + result);
        Map<String, String> parameterMap2 = new HashMap<String, String>();
        try {
            parameterMap2 = WxSignUtil.parseXmlStr(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return parameterMap2;
    }


    /**
     * 提现到零钱
     *
     * @return
     */
    @Override
    public Map<String, String> payToLooseChange(WithdrawalRecord record, String orderId, String ip) throws UnknownHostException {
        String result = WXConnectionUtil.payToLooseChange(record, orderId, ip);
        Map<String, String> parameterMap2 = new HashMap<String, String>();
        try {
            parameterMap2 = WXHttpsReqUtil.parseWxResponseStr(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("pay to loose change result:" + JSON.toJSONString(parameterMap2));
        return parameterMap2;
    }

    /**
     * 提现到零钱查询
     *
     * @return
     */
    @Override
    public Map<String, String> queryPayToLooseChange(String tradeNo) {
        String result = WXConnectionUtil.queryPayToLooseChange(tradeNo);
        Map<String, String> parameterMap2 = new HashMap<String, String>();
        try {
            parameterMap2 = WXHttpsReqUtil.parseWxResponseStr(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return parameterMap2;
    }

    @Override
    public Map<String, String> payToBank(WithdrawalRecord record, String orderId) throws Exception {
        String result = WXConnectionUtil.withdrawal(record, orderId);
        Map<String, String> parameterMap2 = new HashMap<String, String>();
        try {
            parameterMap2 = WXHttpsReqUtil.parseWxResponseStr(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("pay to bank result:" + JSON.toJSONString(parameterMap2));
        return parameterMap2;
    }

    @Override
    public Map<String, String> payToAccount(WithdrawalRecord record, String orderId) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("data", "");
        try {
            if (record.getWithdrawalType() == 1) {
                Map<String, String> response = payToBank(record, orderId);
                if ("FAIL".equals(response.get("return_code"))) {
                    result.put("status", "fail");
                    result.put("msg", response.get("return_msg"));
                } else {
                    result.put("status", "unknown");
                }
                result.put("data", JSON.toJSONString(response));
            } else if (record.getWithdrawalType() == 2) {
                Map<String, String> response = payToLooseChange(record, orderId, ip);
                if ("FAIL".equals(response.get("return_code"))) {
                    result.put("status", "fail");
                    result.put("msg", response.get("return_msg"));
                } else if ("FAIL".equals(response.get("result_code")) && "SYSTEMERROR".equals(response.get("err_code"))) {
                    result.put("status", "unknown");
                } else if ("FAIL".equals(response.get("result_code"))) {
                    result.put("status", "fail");
                    result.put("msg", response.get("return_msg"));
                } else {
                    result.put("status", "success");
                }
                result.put("data", JSON.toJSONString(response));
            } else {
                result.put("status", "unknown");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "unknown");
        }
        return result;
    }

    @Override
    public Map<String, String> queryWithDrawalStatus(PaymentRecord paymentRecord) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("data", "");
        Map<String, String> response = new HashMap<>();
        try {
            if (paymentRecord.getPaymentType() == 1) {
                response = queryPayToBank(paymentRecord.getId());
            } else if (paymentRecord.getPaymentType() == 2) {
                response = queryPayToLooseChange(paymentRecord.getId());
            }
            if ("FAIL".equals(response.get("return_code")) || "FAIL".equals(response.get("result_code"))) {
                result.put("status", "fail");
                result.put("msg", response.get("return_msg"));
            } else {
                // 成功
                if ("SUCCESS".equals(response.get("result_code"))) {
                    result.put("status", "success");
                } else if ("PROCESSING".equals(response.get("result_code"))) {
                    result.put("status", "unknown");
                } else {
                    result.put("status", "fail");
                    result.put("msg", response.get("return_msg"));
                }
            }
            result.put("data", JSON.toJSONString(response));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "unknown");
        }
        return result;
    }

    @Override
    public Map<String, String> queryPayToBank(String tradeNo) {
        String result = WXConnectionUtil.queryPayToBank(tradeNo);
        Map<String, String> parameterMap2 = new HashMap<String, String>();
        try {
            parameterMap2 = WXHttpsReqUtil.parseWxResponseStr(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("query pay to bankk result:" + JSON.toJSONString(parameterMap2));
        return parameterMap2;
    }

    private void savePayRecord(OrderInfo orderInfo, String prepayId) {
        // 添加支付记录 pay_record中
        PayRecordInfo payRecordInfo = new PayRecordInfo();
        payRecordInfo.setId(orderInfo.getOutTradeNo());
        payRecordInfo.setPrepayId(prepayId);
        payRecordInfo.setPayUserId(orderInfo.getUserId());
        payRecordInfo.setPayType(orderInfo.getOrderType());
        payRecordInfo.setPayMoney(new BigDecimal(orderInfo.getTotalFee()));
        payRecordInfoService.insertPayRecord(payRecordInfo);
    }
}
