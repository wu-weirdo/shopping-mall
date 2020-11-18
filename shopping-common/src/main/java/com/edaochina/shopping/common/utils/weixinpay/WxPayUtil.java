package com.edaochina.shopping.common.utils.weixinpay;

import org.springframework.stereotype.Component;

/**
 * 小程序支付工具类
 *
 * @Author zzk
 * @Date 2018/12/12
 */
@Component
public class WxPayUtil {
//    /**
//     * 小程序appId
//     */
//    @Value("${wechat.appId}")
//    private String appId;
//    /**
//     * 小程序支付Id
//     */
//    @Value("${wechat.payId}")
//    private String payId;
//    /**
//     * 小程序支付密钥
//     */
//    @Value("${wechat.payKey}")
//    private String payKey;
//    /**
//     * 支付回调地址
//     */
//    @Value("${wechat.callBackUrl}")
//    private String callBackUrl;

//    public AjaxResult wxPrepay() {
//        //获取会费信息
//        SysConfig feeInfo = sysConfigService.getFeeInfo();
//        String feeStr = feeInfo.getSettingsValue();
//        //微信支付金额单位为分,所以要乘以100
//        BigDecimal fee = new BigDecimal(feeStr).multiply(valueOf(100));
//        //生成随机支付订单号
//        String orderId = IdUtils.nextId();
//        Map<String, String> resultMap = WechatPlatForm.getJSAPIPrepayId(appId, payId, payKey, callBackUrl,
//                member.getOpenId(), "微信支付", "微信支付",
//                orderId, fee.intValue(), "127.0.0.1", "");
//        if (resultMap == null) {
//            return BaseResult.failedResult(ReturnData.WECHAT_TRANS_ORDER_CREATE_ERROR.getCode(), ReturnData.WECHAT_TRANS_ORDER_CREATE_ERROR.getMsg());
//        }
//        if (!Objects.equal("SUCCESS", resultMap.get("result_code"))) {
//            return BaseResult.failedResult(ReturnData.WECHAT_TRANS_ORDER_CREATE_ERROR.getCode(), ReturnData.WECHAT_TRANS_ORDER_CREATE_ERROR.getMsg());
//        }
//        //插入支付记录表
//        PayRecord payRecord = new PayRecord();
//        payRecord.setId(orderId);
//        payRecord.setMemberId(member.getId());
//        payRecord.setPayMoney(new BigDecimal(feeStr));
//        payRecord.setStatus(0);
//        payRecord.setCreateTime(LocalDateTime.now());
//        payRecord.setMessage("待支付订单");
//        payRecordService.save(payRecord);
//        String prePayId = resultMap.get("prepay_id");
//        return BaseResult.successResult(new WxPayConfig(appId, prePayId, payKey));
//    }

}
