package com.edaochina.shopping.common.result;

import com.edaochina.shopping.common.enums.ReturnData;

/**
 * 返回内容的封装
 */
public class BaseResult {

    private BaseResult() {
    }

    /**
     * 对返回数据进行包装，格式为：{"code":200,"msg":""}
     */
    public static AjaxResult successResult() {
        return new AjaxResult(ReturnData.SUCCESS.getCode(), ReturnData.SUCCESS.getMsg());
    }

    public static AjaxResult successResult(int code, String msg) {
        return new AjaxResult(code, msg);
    }

    public static AjaxResult successResult(Object obj) {
        return new AjaxResult(obj);
    }

    public static <T> AjaxResultGeneric<T> successGenericResult(T t) {
        return new AjaxResultGeneric<>(t);
    }

    public static AjaxResult successResult(ReturnData returnData, Object obj) {
        return new AjaxResult(returnData.getCode(), returnData.getMsg(), obj);
    }

    public static AjaxResult successResult(int code, String msg, Object result) {
        return new AjaxResult(code, msg, result);
    }

    /**
     * 对返回数据进行包装，格式为：{"code":输入的code,"msg":""}
     */
    public static AjaxResult failedResult() {
        return new AjaxResult(ReturnData.FAIL.getCode(), ReturnData.FAIL.getMsg());
    }

    public static AjaxResult failedResult(int code) {
        return new AjaxResult(code);
    }

    public static AjaxResult failedResult(int code, String msg) {
        return new AjaxResult(code, msg);
    }

    public static AjaxResult failedResult(ReturnData obj) {
        return new AjaxResult(obj.getCode(), obj.getMsg(), null);
    }

    public static AjaxResult failedResult(int code, String msg, Object result) {
        return new AjaxResult(code, msg, result);
    }

    public static <T> AjaxResultGeneric<T> failedGenericResult(int code, String msg, T t) {
        return new AjaxResultGeneric<>(code, msg, t);
    }

}
