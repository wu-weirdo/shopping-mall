package com.edaochina.shopping.domain.constants;

/**
 * 阿里云sms模板
 *
 * @Author zzk
 * @Date 2018/12/13
 */
public interface SmsConstant {
    /**
     * 模板类型 1、注册 2、登陆 3、信息变更
     */
    Integer REGISTER_TYPE = 1;

    Integer LOGIN_TYPE = 2;

    Integer UPD_TYPE = 3;
    /**
     * 用户注册验证码模板
     */
    String REGISTER_CODE = "SMS_152442059";
    /**
     * 登录确认验证码模板
     */
    String LOGIN_CODE = "SMS_152442061";

    /**
     * 登录短信模板
     */
    String LOGIN_CHECK_CODE = "SMS_153425299";

    /**
     * 调查问卷短信模板
     */
    String QUESTIONNAIRE_CODE = "SMS_172885655";

    /**
     * 用户登录短息发送的redis前段key
     */
    String LOGIN_CHECK_CODE_REDIS_HEAD = "LOGIN_CHECK_CODE_";

    /**
     * 修改密码
     */
    String UPDSTE_PASSWORD_CODE = "SMS_153425296";


    /**
     * 修改密码短息发送的redis前段key
     */
    String UPDATE_PASSWORD_REDIS_HEAD = "UPDATE_PASSWORD_";


    /**
     * 信息变更验证码模板
     */
    String UPD_CODE = "SMS_153425295";

    /**
     * 身份验证模板
     */
    String VERIFY_CODE = "SMS_153425300";

    /**
     * 业务限流：每分钟1条,每小时5条,每天10条
     */
    String LIMIT_CONTROL = "isv.BUSINESS_LIMIT_CONTROL";

    String MOBILE_NUMBER_ILLEGAL = "isv.MOBILE_NUMBER_ILLEGAL";


}
