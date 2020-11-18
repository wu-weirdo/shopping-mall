package com.edaochina.shopping.common.result;

import com.edaochina.shopping.common.utils.JsonUtils;

import java.io.Serializable;

/**
 * ajax 结果返回 泛型版
 *
 * @author wangpl
 */

public class AjaxResultGeneric<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code = 200;

    private String msg = "success";

    private T results;

    public AjaxResultGeneric() {
    }

    public AjaxResultGeneric(int code) {
        this.code = code;
    }

    public AjaxResultGeneric(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AjaxResultGeneric(T results) {
        this.results = results;
    }

    public AjaxResultGeneric(int code, String msg, T results) {
        this.code = code;
        this.msg = msg;
        this.results = results;
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

}