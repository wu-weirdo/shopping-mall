package com.edaochina.shopping.common.utils.date;

import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.domain.constants.DateConstant;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
 * @BelongsProject: customize
 * @BelongsPackage: com.edaochina.shopping.common.utils.date
 * @Author: Jason
 * @CreateTime: 2018-11
 * @Description: jdk8 LocalDateTime格式转换
 */
@UtilityClass
public class LocalDateUtil {
    /**
     * 字符串转换为LocalDateTime
     *
     * @param date       字符串日期
     * @param dateFormat 时间格式
     * @return
     */
    public LocalDateTime string2LocaDateTime(String date, String dateFormat) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
            return LocalDateTime.parse(date, df);
        } catch (Exception e) {
            LoggerUtils.error(LocalDateUtil.class, "错误的字符串日期格式：" + date);
            throw new YidaoException(ReturnData.DATE_ERROR);
        }
    }

    /**
     * LocalDateTime转换为字符串
     *
     * @param date       日期
     * @param dateFormat 时间格式
     * @return
     */
    public String localDateTime2String(LocalDateTime date, String dateFormat) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
            return df.format(date);
        } catch (Exception e) {
            LoggerUtils.error(LocalDateUtil.class, "错误的日期格式：" + date);
            throw new YidaoException(ReturnData.DATE_ERROR);
        }
    }

    /**
     * LocalDateTime转换为字符串
     *
     * @param date 日期
     * @return
     */
    public String localDateTime2String(LocalDateTime date) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(DateConstant.DATE_MODE_3);
            return df.format(date);
        } catch (Exception e) {
            LoggerUtils.error(LocalDateUtil.class, "错误的日期格式：" + date);
            throw new YidaoException(ReturnData.DATE_ERROR);
        }
    }

    //==============================================================

    /**
     * 当天的开始时间
     *
     * @return
     */
    public static LocalDateTime getStartTimeOfToday() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 当天的结束时间
     *
     * @return
     */
    public static LocalDateTime getEndTimeOfToday() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

}
