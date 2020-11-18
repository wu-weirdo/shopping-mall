package com.edaochina.shopping.domain.constants;

/**
 * @updateRemark: 修改内容
 * @description: aliyun 短信常量
 */
public interface AliyunSmsConstants {
    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    String PRODUCT = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    String DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 区域
     */
    String REGION_ID = "cn-hangzhou";

    String ACCESSKEY_ID = "LTAIYTPcE8uwnylv";

    String ACCESSKEY_SECRET = "yTBYqP6fA8MBih6z79TUPNPSfeDf3X";

    /**
     * 返回成功
     */
    String OK = "OK";
    /**
     * 非法手机号返回码
     */
    String ERROR_PHONE_CODE = "isv.MOBILE_NUMBER_ILLEGAL";
    /**
     * 业务停机
     */
    String OUT_OF_SERVICE_CODE = "isv.OUT_OF_SERVICE";

    /**
     * 短信注册验证码
     * <p>
     * 模板内容：您的验证码${code}，该验证码5分钟内有效，请勿泄漏于他人！
     */
    String REGISTERED_VALIDATE = "SMS_152510350";

    /**
     * 客户端
     * 已预约
     * 您的订单已经预约成功，请耐心等待测量师上门服务。
     */
    String CUST_SUBSCRIBE = "SMS_152510370";

    /**
     * 客户端
     * 已下单
     * 您的预约单已经下单成功，请耐心等待测量师上门服务。
     */
    String CUST_COMMIT = "SMS_152505494";

    /**
     * 客户端
     * 制作中
     * 你的订单工厂正在制作中，请耐心等待。
     */
    String CUST_MAKE = "119405581";

    /**
     * 客户端
     * 待合验
     * 您的订单已发货，请耐心等待测量师合验。
     */
    String CUST_CONFIRE = "SMS_152510361";
}
