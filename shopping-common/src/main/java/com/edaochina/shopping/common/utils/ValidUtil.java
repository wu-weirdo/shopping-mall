package com.edaochina.shopping.common.utils;

import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zzk
 * @Date 2018/12/27
 */
public class ValidUtil {
    public static AjaxResult check(BindingResult bindingResult) {
        List<String> result = new ArrayList<>();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            FieldError fieldError = (FieldError) error;
            result.add(fieldError.getDefaultMessage());
        }
        return BaseResult.failedResult(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(),
                ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg(),
                result);
    }

    public static AjaxResultGeneric checkGeneric(BindingResult bindingResult) {
        List<String> result = new ArrayList<>();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            FieldError fieldError = (FieldError) error;
            result.add(fieldError.getDefaultMessage());
        }
        return BaseResult.failedGenericResult(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(),
                ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg(),
                result);
    }
}
