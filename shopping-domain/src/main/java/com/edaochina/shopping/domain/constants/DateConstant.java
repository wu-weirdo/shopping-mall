package com.edaochina.shopping.domain.constants;

/**
 * @BelongsPackage: com.yidao.common.DateConstant
 * @Author: Jason
 * @CreateTime: 2018-10-10 11:02
 * @Description: 日期常用格式
 */
public interface DateConstant {
    /**
     * 时间格式 验证
     */
    String PATTERN_STR_YMD = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
    String PATTERN_STR_YM = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-((0[1-9])|(1[0-2])))";
    String PATTERN_STR_Y = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})";
    String DATE_MODE_1 = "yyyy-MM-dd";
    String DATE_MODE_2 = "yyyy-MM-dd HH:mm";
    String DATE_MODE_3 = "yyyyMMddHHmmssSSS";
    String DATE_MODE_4 = "yyyy-MM-dd HH:mm:ss";
    String DATE_MODE_5 = "HHmmssSS";
    String DATE_MODE_6 = "yyyyMMdd";
    String DATE_MODE_7 = "HHmmss";
    String DATE_MODE_8 = "yyyy";
    String DATE_MODE_9 = "yyyyMMddHHmmss";
    String DATE_MODE_10 = "HH:mm:ss";
    String DATE_MODE_11 = "yyyy-MM-dd HH:mm:ss.SSS";
    String DATE_MODE_12 = "yyyyMM";
    String DATE_REGEX = "\\d{4}-\\d{1,2}-\\d{1,2}";
    String DEFAULT_DATE_FORMAT = DATE_MODE_1;
    String EMPTY_STRING = "";
}
