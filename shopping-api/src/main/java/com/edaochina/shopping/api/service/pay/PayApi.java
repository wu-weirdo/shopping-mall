package com.edaochina.shopping.api.service.pay;


import com.edaochina.shopping.common.wxpay.model.OrderInfo;
import com.edaochina.shopping.domain.entity.trade.PaymentRecord;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by Administrator on 2018\8\25 0025.
 */
public interface PayApi {

    /**
     * 支付
     *
     * @return
     */
    Map<String, String> pay(Object obj) throws UnsupportedEncodingException;

    /**
     * 退款
     *
     * @return
     */
    Map<String, String> refund(Object obj);

    /**
     * 提现到零钱
     *
     * @return
     */
    Map<String, String> payToLooseChange(WithdrawalRecord record, String orderId, String ip) throws UnknownHostException;

    Map<String, String> queryPayToLooseChange(String tradeNo);

    Map<String, String> payToBank(WithdrawalRecord record, String orderId) throws Exception;

    Map<String, String> payToAccount(WithdrawalRecord record, String orderId);

    Map<String, String> queryWithDrawalStatus(PaymentRecord paymentRecord);

    Map<String, String> queryPayToBank(String tradeNo);


    WxPayAppOrderResult appPay(OrderInfo orderInfo) throws WxPayException;

    WxPayMpOrderResult miniPay(OrderInfo orderInfo);

    WxPayRefundResult refund(WxPayRefundRequest refundRequest) throws WxPayException;

    /**
     * 解析支付结果通知.
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
     *
     * @param xmlData the xml data
     * @return the wx pay order notify result
     * @throws WxPayException the wx pay exception
     */
    WxPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws WxPayException;

}
