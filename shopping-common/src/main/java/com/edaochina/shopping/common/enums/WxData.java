package com.edaochina.shopping.common.enums;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author Jason
 * @since 2018-11
 */
public enum WxData {
    /**
     * 微信小程序推送消息返回结果
     */
    TEMPLATE_ERROR(40037, "template_id不正确"),
    FORM_ERROR(41028, "form_id不正确，或者过期"),
    FORM_USED(41029, "form_id已被使用"),
    PAGE_ERROR(41030, "page不正确"),
    MAX_USE(45009, "接口调用超过限额（目前默认每个帐号日调用限额为100万）"),;

    /**
     * 错误码
     */
    private int errCode;
    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 微信返回结果
     *
     * @param errCode 错误码
     * @param errMsg  错误信息
     */
    WxData(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
