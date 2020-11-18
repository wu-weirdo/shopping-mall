package com.edaochina.shopping.common.exception;

import com.edaochina.shopping.common.enums.ReturnData;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author :         Jason
 * @createDate :     2018/10/11 15:58
 * @description :
 */
@Data
@Accessors(chain = true)
public class YidaoException extends RuntimeException {

    /**
     * 异常错误码
     */
    private int errorCode;
    /**
     * 异常提示信息
     */
    private String errorMsg;

    public YidaoException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public YidaoException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public YidaoException(ReturnData returnData) {
        this.errorCode = returnData.getCode();
        this.errorMsg = returnData.getMsg();
    }
}
