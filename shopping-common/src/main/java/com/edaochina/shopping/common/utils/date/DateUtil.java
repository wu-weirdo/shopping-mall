package com.edaochina.shopping.common.utils.date;

import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.domain.constants.DateConstant;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类DateUtil.java的实现描述：时间日期处理工具。
 *
 * @author Jason
 */
@UtilityClass
public class DateUtil extends DateUtils {

    /**
     * 永久时间
     */
    private static Date FOREVER_TIME = null;


    private static String FOREVER_TIME_STR = "3000-01-01";

    /**
     * 格式化制定的date
     *
     * @param date             需要修改的时间
     * @param simpleDateFormat 时间转换格式
     * @return 转换后的时间字符串
     */
    public String formatDate(Date date, DateTimeFormatter simpleDateFormat) {
        if (date == null) {
            return null;
        }
        if (simpleDateFormat == null) {
            return null;
        }

        //此处使用simpleDateFormat会存在线程安全问题，所以使用jdk1.8加入的DateTimeFormatter（线程安全的）
        //将date类型的时间转换为精确到纳秒的Instant类型时间
        Instant instant = date.toInstant();
        //获取系统当前所在的时区id
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区由Instant生成的ZonedDateTime（带时区信息的时间）。
        //使用toLocalDate（）方法从ZonedDateTime获取LocalDate。
        LocalDateTime localDate = instant.atZone(zoneId).toLocalDateTime();

        return simpleDateFormat.format(localDate);
    }

    /**
     * 格式化制定的date
     *
     * @param date                需要修改的时间
     * @param simpleDateFormatStr 转换格式
     * @return 格式化后的时间字符串
     */
    public String formatDate(Date date, String simpleDateFormatStr) {
        if (date == null) {
            return null;
        }
        if (simpleDateFormatStr == null) {
            //设置默认格式化格式
            simpleDateFormatStr = DateConstant.DEFAULT_DATE_FORMAT;
        }
        return formatDate(date, DateTimeFormatter.ofPattern(simpleDateFormatStr));
    }

    /**
     * 字符串转时间
     *
     * @param dateStr 时间字符串
     * @return 转换后的时间类
     */
    public Date parseStdDate(String dateStr, String simpleDateFormatStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(simpleDateFormatStr)) {
            return null;
        }
        try {
            //返回date格式的时间
            return new SimpleDateFormat(simpleDateFormatStr).parse(dateStr);
        } catch (Exception ex) {
            LoggerUtils.error(DateUtil.class, "parseDate error ", ex);
        }
        return null;
    }

    /**
     * 字符串转时间
     *
     * @param dateStr    时间字符串
     * @param dateFormat 转化时间格式
     * @return 根据格式转换后的时间类
     */
    public Date string2Date(String dateStr, DateFormat dateFormat) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (dateFormat == null) {
            return null;
        }
        try {
            //返回date格式的时间
            return dateFormat.parse(dateStr);
        } catch (Exception ex) {
            LoggerUtils.error(DateUtil.class, "parseDate error ", ex);
        }
        return null;
    }

    /**
     * Date转化为YYYYMMDDHH24MISS格式的字符串
     *
     * @param date Date
     * @return YYYYMMDDHH24MISS格式的字符串
     */
    public String date2Str(Date date) {
        return formatDate(date, DateConstant.DATE_MODE_9);
    }

    /**
     * 日期格式化
     *
     * @param date 需要格式化的日期
     * @return
     */
    public String formatDate(Date date) {
        if (date == null) {
            return DateConstant.EMPTY_STRING;
        }
        //返回“yyyy-MM-dd”格式的时间字符串
        return new SimpleDateFormat(DateConstant.DATE_MODE_1).format(date);
    }

    /**
     * 全时间格式化
     *
     * @param date 需要格式化的日期
     * @return
     */
    public String formatDateTime(Date date) {
        if (date == null) {
            return DateConstant.EMPTY_STRING;
        }
        //返回"yyyy-MM-dd HH:mm:ss"格式的时间字符串
        return new SimpleDateFormat(DateConstant.DATE_MODE_4).format(date);
    }

    /**
     * 日期加
     *
     * @param date 日期
     * @param days 加的天数
     * @return
     */
    public Date addDayFroDate(Date date, int days) {
        //创建一个Calendar类
        Calendar c = Calendar.getInstance();
        if (date != null) {
            //设置时间
            c.setTime(date);
            //时间的天数加一
            c.add(Calendar.DAY_OF_MONTH, days);
        }
        //返回时间
        return c.getTime();
    }

    /**
     * 日期减
     *
     * @param date 日期
     * @param days 减的天数
     * @return
     */
    public Date minusDayForDate(Date date, int days) {
        //创建一个Calendar类
        Calendar c = Calendar.getInstance();
        if (date != null) {
            //设置时间
            c.setTime(date);
            //时间的日期数减一
            c.add(Calendar.DAY_OF_MONTH, -days);
        }
        //返回时间
        return c.getTime();
    }

    /**
     * 小时加
     *
     * @param date  日期
     * @param hours 加的小时数
     * @return
     */
    public Date addHourFroDate(Date date, int hours) {
        //创建一个Calendar类
        Calendar c = Calendar.getInstance();
        if (date != null) {
            //设置时间
            c.setTime(date);
            //时间的小时数加一
            c.add(Calendar.HOUR_OF_DAY, hours);
        }
        //返回时间
        return c.getTime();
    }

    /**
     * 小时减
     *
     * @param date  日期
     * @param hours 减的小时数
     * @return
     */
    public Date minusHourFroDate(Date date, int hours) {
        //创建一个Calendar类
        Calendar c = Calendar.getInstance();
        if (date != null) {
            //设置时间
            c.setTime(date);
            //时间的小时数减一
            c.add(Calendar.HOUR_OF_DAY, -hours);
        }
        //返回时间
        return c.getTime();
    }

    /**
     * 添加月
     *
     * @param date  日期
     * @param month 月
     * @return
     */
    public Date addMonthFroDate(Date date, int month) {
        //创建一个Calendar类
        Calendar c = Calendar.getInstance();
        //设置时间
        c.setTime(date);
        //时间的月数增加month个月
        c.add(Calendar.MONTH, month);
        //返回时间
        return c.getTime();
    }

    /**
     * 添加年
     *
     * @param date 日期
     * @param year 年
     * @return
     */
    public Date addYearFroDate(Date date, int year) {
        //创建一个Calendar类
        Calendar c = Calendar.getInstance();
        //设置时间
        c.setTime(date);
        //时间的年数加year年
        c.add(Calendar.YEAR, year);
        //返回时间
        return c.getTime();
    }

    /**
     * 添加年,月
     *
     * @param date  日期
     * @param year  年
     * @param month 月
     * @return
     */
    public Date addYearAndMonthFroDate(Date date, int year, int month) {
        //创建一个Calendar类
        Calendar c = Calendar.getInstance();
        //设置时间
        c.setTime(date);
        //时间的年数加year年
        c.add(Calendar.YEAR, year);
        //时间的月数增加month个月
        c.add(Calendar.MONTH, month);
        //返回时间
        return c.getTime();
    }

    /**
     * 获取某天的最小时间
     *
     * @param date 日期
     * @return
     */
    public Date getDateMin(Date date) {
        try {
            //返回某天的最小时间
            return new SimpleDateFormat(DateConstant.DATE_MODE_4).parse(formatDate(date) + " 00:00:00");
        } catch (Exception ex) {
            LoggerUtils.error(DateUtil.class, "getDateMin error ", ex);
        }
        return date;
    }

    /**
     * 获取某天的最大时间
     *
     * @param date 日期
     * @return
     */
    public Date getDateMax(Date date) {
        try {
            //返回某天的最大时间
            return new SimpleDateFormat(DateConstant.DATE_MODE_4).parse(formatDate(date) + " 23:59:59");
        } catch (Exception ex) {
            LoggerUtils.error(DateUtil.class, "getDateMax error ", ex);
        }
        return date;
    }

    /**
     * startDate加上days是否小于endate，超过返回true，否则返回false
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param days      天数
     * @return
     */
    public boolean compareDate(Date startDate, Date endDate, int days) {
        if (startDate == null || endDate == null) {
            return false;
        }
        return addDays(startDate, days).before(endDate);
    }

    /**
     * startDate加上days是否小于等于endDate，满足返回true，否则返回false
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param days      天数
     * @return
     */
    public boolean compareDateWithEqual(Date startDate, Date endDate, int days) {
        if (startDate == null || endDate == null) {
            return false;
        }
        //startDate加上days
        Date d = addDays(startDate, days);
        //是否小于等于endDate
        return d.compareTo(endDate) <= 0;
    }

    /**
     * 获取一周中的第几天
     *
     * @param date 日期
     * @return
     */
    public int getWeekByDate(Date date) {
        //创建一个Calendar类
        Calendar c = Calendar.getInstance();
        if (date != null) {
            //设置时间
            c.setTime(date);
        }
        //获取当前时间处于一周中的第几天
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取相差几天
     *
     * @param sd 日期1
     * @param ed 日期2
     * @return
     */
    public int getIntervalDayCount(Date sd, Date ed) {
        return (int) ((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000) + 1);
    }

    /**
     * 两个时间戳相差多少天
     *
     * @param sd 时间戳1
     * @param ed 时间戳2
     * @return
     */
    public int getIntervalDayCount(Long sd, Long ed) {
        return (int) ((ed - sd) / (3600 * 24 * 1000) + 1);
    }

    /**
     * long型日期转String型
     *
     * @param sd            时间戳
     * @param isIncludeTime Long型日期是否包含时分秒
     * @param dateFormat    日期格式
     * @return
     */
    public String long2String(Long sd, boolean isIncludeTime, DateFormat dateFormat) {
        //判断Long型日期是否包含时分秒
        if (isIncludeTime) {
            //包含
            return dateFormat.format(new Date(sd));
        } else {
            //不包含
            return dateFormat.format(new Date(sd * 1000));
        }
    }

    /**
     * 获取明日的字符串
     *
     * @param formater 格式
     * @return
     */
    public String getNextDayString(SimpleDateFormat formater) {
        if (formater == null) {
            formater = new SimpleDateFormat();
        }
        //获取当前的时间并加一天
        Date nextDate = addDayFroDate(new Date(), 1);
        //返回格式化后的字符串
        return formater.format(nextDate);
    }

    /**
     * 获取后一天的字符串
     *
     * @param dateStr 时间字符串
     * @param format  时间格式
     * @return
     */
    public String getNextDayString(String dateStr, SimpleDateFormat format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (format == null) {
            format = new SimpleDateFormat();
        }
        Date date = null;
        try {
            //将字符串格式的日期转换成date格式
            date = format.parse(dateStr);
        } catch (ParseException e) {
            LoggerUtils.error(DateUtil.class, "getNextDayString error ", e);
        }
        //将date日期加一天
        Date nextDate = addDayFroDate(date, 1);
        //返回格式化过后的时间字符串
        return format.format(nextDate);
    }

    /**
     * 获取昨日的字符串
     *
     * @param format 时间格式
     * @return
     */
    public String getAheadDayString(SimpleDateFormat format) {
        if (format == null) {
            format = new SimpleDateFormat();
        }
        //获取当前时间并且日期数减一
        Date nextDate = addDayFroDate(new Date(), -1);
        //返回格式化过的时间字符串
        return format.format(nextDate);
    }

    /**
     * 获取前一天的字符串
     *
     * @param dateStr 时间字符串
     * @param format  时间格式
     * @return
     */
    public String getAheadDayString(String dateStr, SimpleDateFormat format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (format == null) {
            format = new SimpleDateFormat(DateConstant.DATE_MODE_6);
        }
        Date date = null;
        try {
            //将时间字符串转换为date类型
            date = format.parse(dateStr);
        } catch (ParseException e) {
            LoggerUtils.error(DateUtil.class, "getAheadDayString error ", e);
        }
        //将时间的日期减一天
        Date nextDate = addDayFroDate(date, -1);
        //返回格式化过后的时间字符串
        return format.format(nextDate);
    }

    /**
     * 将字符串src按format指定的格式转化为日期对象，本地中文
     *
     * @param src    String
     * @param format String 格式，如yy/MM/dd hh:mm:ss
     * @return Date对象
     * @throws ParseException
     */
    public Date string2Date(String src, String format) throws ParseException {
        //创建一个本地中文格式的SimpleDateFormat对象
        SimpleDateFormat sdf = new SimpleDateFormat(format, java.util.Locale.CHINESE);
        Date d = null;
        if (StringUtils.isNotBlank(src)) {
            //格式化date
            d = sdf.parse(src);
        }
        return d;
    }

    /**
     * 判断一个字符串是否为符合yyyy-MM-dd格式的时间字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public boolean isDate(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }

        //创建一个符合"\\d{4}-\\d{1,2}-\\d{1,2}"正则的Pattern模式
        Pattern p = Pattern.compile(DateConstant.DATE_REGEX);
        //判断字符串是否匹配
        if (!p.matcher(str).matches()) {
            return false;
        }

        try {
            //将字符串格式的时间转化为Date格式的时间
            string2Date(str, DateConstant.DEFAULT_DATE_FORMAT);
            return true;
        } catch (Exception e) {
            LoggerUtils.error(DateUtil.class, " isDate error :{0}", e);
            return false;
        }
    }

    /**
     * 获取年
     *
     * @param data 时间
     * @return
     * @throws ParseException
     */
    public int getYear(String data) throws ParseException {
        if (StringUtils.isBlank(data)) {
            return 0;
        }
        //将"yyyy-MM-dd"字符串格式的时间转化为date格式的date
        Date startDate = new SimpleDateFormat(DateConstant.DATE_MODE_1).parse(data);
        //创建一个Calendar对象
        Calendar start = Calendar.getInstance();
        //设置时间
        start.setTime(startDate);
        //获取时间中的年
        return start.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @param data 时间
     * @return
     * @throws ParseException
     */
    public int getMonth(String data) throws ParseException {
        if (StringUtils.isBlank(data)) {
            return 0;
        }
        //将"yyyy-MM-dd"字符串格式的日期转换为date格式的日期
        Date endDate = new SimpleDateFormat(DateConstant.DATE_MODE_1).parse(data);
        //创建一个Calendar对象
        Calendar end = Calendar.getInstance();
        //设置时间
        end.setTime(endDate);
        //获取时间中的月
        return end.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回两个时间之间的天数
     *
     * @param startDay 开始时间
     * @param endDay   结束时间
     * @return
     */
    public Long getDaysBetweenTwoDays(Date startDay, Date endDay) {
        return (endDay.getTime() - startDay.getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * 返回两个时间之间的天数，endDate - startDate +1
     *
     * @param startDay 开始时间
     * @param endDay   结束时间
     * @return
     */
    public Long getDaysMaxBetweenTwoDays(Date startDay, Date endDay) {
        return (endDay.getTime() - startDay.getTime()) / (1000 * 60 * 60 * 24) + 1;
    }

    /**
     * 获取某个时间段的时间信息
     *
     * @param dBegin 开始时间
     * @param dEnd   结束时间
     * @return
     */
    public List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        //将时间添加到列表中
        lDate.add(dBegin);
        //创建一个Calendar对象
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 字符串转时间
     *
     * @param dateStr 时间字符串
     * @return
     */
    public Date getDate(String dateStr) {
        //获取当前时间
        Date date = new Date();
        try {
            //判断dateStr是否是时间格式的字符串
            if (Pattern.matches(DateConstant.PATTERN_STR_YMD, dateStr)) {
                //将yyyy-MM-dd字符串形式的时间转换为date类型的时间
                SimpleDateFormat sf1 = new SimpleDateFormat(DateConstant.DATE_MODE_1);
                date = sf1.parse(dateStr);
            } else if (Pattern.matches(DateConstant.PATTERN_STR_YM, dateStr)) {
                //将yyyy-MM字符串形式的时间转换为date类型的时间
                SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM");
                date = sf1.parse(dateStr);
            } else if (Pattern.matches(DateConstant.PATTERN_STR_Y, dateStr)) {
                //将yyyy字符串形式的时间转换为date形式的时间
                SimpleDateFormat sf1 = new SimpleDateFormat("yyyy");
                date = sf1.parse(dateStr);
            }
            //返回date格式的时间
            return date;
        } catch (ParseException e) {
            LoggerUtils.error(DateUtil.class, "转换时间失败", e);
        }
        return null;
    }

    /**
     * 判断日期格式:yyyy-mm-dd
     *
     * @param sDate 时间字符串
     * @return
     */
    public boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            return checkDate(sDate, datePattern1, datePattern2);
        }
        return false;
    }

    /**
     * 是否年月
     *
     * @param sDate 时间字符串
     * @return
     */

    public boolean isValidYMDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}";
        String datePattern2 = DateConstant.PATTERN_STR_YM;
        if ((sDate != null)) {
            return checkDate(sDate, datePattern1, datePattern2);
        }
        return false;
    }

    /**
     * 是否年份
     *
     * @param sDate 时间字符串
     * @return
     */
    public boolean isValidYDate(String sDate) {
        String datePattern1 = "\\d{4}";
        String datePattern2 = DateConstant.PATTERN_STR_Y;
        if ((sDate != null)) {
            //检查时间是否符合制定格式
            return checkDate(sDate, datePattern1, datePattern2);
        }
        return false;
    }


    /*public static void main(String[] args) throws ParseException {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        String resultDateFormat = "yyyy-MM-dd HH:mm:ss";
        String dataStr = "2019-1-30 17:14:22";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat(dateFormat).parse(dataStr));
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);//
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        // 所在周开始日期
        String data1 = new SimpleDateFormat(resultDateFormat).format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        // 所在周结束日期
        String data2 = new SimpleDateFormat(resultDateFormat).format(cal.getTime());
        System.out.println(data1 + "***" + data2);
    }*/


    /**
     * 获取本周第一天
     *
     * @return
     */
    public Date getStartDateOfCurrWeek() {
        Calendar cal = Calendar.getInstance();
        int d;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取本周最后一天
     *
     * @return
     */
    public Date getEndDateOfCurrWeek() {
        Calendar cal = Calendar.getInstance();
        cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }


    /**
     * 获取某个时间所在月的最后一天
     *
     * @param date 时间
     * @return
     */
    public Date getLastDayOfMonth(Date date) {
        Date lastDayOfMonth = null;
        //创建一个Calendar对象
        Calendar cal = Calendar.getInstance();
        //设置时间
        cal.setTime(date);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //获取时间
        lastDayOfMonth = cal.getTime();
        return lastDayOfMonth;
    }

    /**
     * 获取某个时间所在月的第一天
     *
     * @param date 时间
     * @return
     */
    public Date getFirstDayOfMonth(Date date) {
        Date firstDayOfMonth = null;
        //创建一个Calender对象
        Calendar cal = Calendar.getInstance();
        //设置时间
        cal.setTime(date);
        //设置日历中月份的第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //获取时间
        firstDayOfMonth = cal.getTime();
        return firstDayOfMonth;
    }

    /**
     * 获取某年的最后一天
     *
     * @param date 时间
     * @return
     */
    public Date getLastDayOfYear(Date date) {
        Date lastDayOfMonth = null;
        //创建一个Calender对象
        Calendar cal = Calendar.getInstance();
        //设置时间
        cal.setTime(date);
        //获取某年最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        //设置日历中年份的最大天数
        cal.set(Calendar.DAY_OF_YEAR, lastDay);
        //获取时间
        lastDayOfMonth = cal.getTime();
        return lastDayOfMonth;
    }

    /**
     * 获取某年的第一天
     *
     * @param date 时间
     * @return
     */
    public Date getFirstDayOfYear(Date date) {
        Date lastDayOfMonth = null;
        //创建一个Calender对象
        Calendar cal = Calendar.getInstance();
        //设置时间
        cal.setTime(date);
        //设置日历中年份的最大天数
        cal.set(Calendar.DAY_OF_YEAR, 1);
        //获取时间
        lastDayOfMonth = cal.getTime();
        return lastDayOfMonth;
    }

    /**
     * 计算两个时间点之间的时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public String getDifferenceBetweenTwoDate(Date startTime, Date endTime) {
        //除以1000是为了转换成秒，计算两个时间之间的秒数
        long between = (endTime.getTime() - startTime.getTime()) / 1000;
        //计算天数
        long day1 = between / (24 * 3600);
        //计算小时数
        long hour1 = between % (24 * 3600) / 3600;
        //计算分钟数
        long minute1 = between % 3600 / 60;
        //计算秒数
        long second1 = between % 60 / 60;
        return day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
    }

    /**
     * 计算两个时间点之间的毫秒数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public String getMillisecondBetweenTwoDate(Date startTime, Date endTime) {
        //计算两个时间之间的毫秒数
        long between = (endTime.getTime() - startTime.getTime());
        return "相差" + between + "毫秒";
    }

    /**
     * 判断一个时间是否在指定的时间内
     *
     * @param date         时间
     * @param strDateBegin 指定的开始时间  时分秒
     * @param strDateEnd   指定的结束时间，时分秒
     * @return
     */
    public boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
        //创建一个"yyyy-MM-dd HH:mm:ss"格式的SimpleDateFormat对象
        SimpleDateFormat sdf = new SimpleDateFormat(DateConstant.DATE_MODE_4);
        //对date日期进行格式化，转换成"yyyy-MM-dd HH:mm:ss"格式的字符串格式时间
        String strDate = sdf.format(date);

        // 截取当前时间时分秒
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        // 截取开始时间时分秒
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
        // 截取结束时间时分秒
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
        if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
            // 当前时间小时数在开始时间和结束时间小时数之间
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM
                    && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                return true;
            }
            // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
            else if (strDateH == strDateEndH && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
            } else {
                return strDateM == strDateEndM && strDateS <= strDateEndS;
            }
        } else {
            return false;
        }
    }

    /**
     * @return 返回昨日时间
     */
    public Date yesterday() {
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //设置为前一天
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回Integer格式的时间
     *
     * @param dateStr 时间字符串
     * @return
     */
    public Integer getDateInteger(String dateStr) {
        //创建一个"yyyy/MM/dd"格式的SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            //将字符串格式的时间转换成date格式的时间
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            LoggerUtils.error(DateUtil.class, "字符日期转换失败:{0}", e);
        }
        //返回Integer格式的时间
        return Integer.valueOf(new SimpleDateFormat(DateConstant.DATE_MODE_6).format(date));
    }

    /**
     * 获取上一个月
     *
     * @return
     */
    public String getLastMonth() {
        //创建一个Calender对象
        Calendar cal = Calendar.getInstance();
        //对日期中的月数减一
        cal.add(Calendar.MONTH, -1);
        //创建一个"yyyyMM"格式的SimpleDateFormat
        SimpleDateFormat dft = new SimpleDateFormat(DateConstant.DATE_MODE_12);
        //返回格式化过后的时间字符串
        return dft.format(cal.getTime());
    }

    /**
     * 检测时间是否符合制定格式
     *
     * @param date         检查的时间
     * @param datePattern1 时间模板
     * @param datePattern2 时间模板
     * @return 是否符合
     */
    private boolean checkDate(String date, String datePattern1, String datePattern2) {
        //创建一个指定格式的Pattern模式
        Pattern pattern = Pattern.compile(datePattern1);
        //创建了一个匹配对象并对时间字符串进行正则匹配
        Matcher match = pattern.matcher(date);
        //判断是否匹配成功
        //匹配成功
        if (match.matches()) {
            //创建另一个指定格式的Pattern模式
            pattern = Pattern.compile(datePattern2);
            //对时间字符串进行正则匹配
            match = pattern.matcher(date);
            //返回匹配结果
            return match.matches();
        } else {
            return false;
        }
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     */
    public LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();//A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
//        System.out.println(localDateTime.toString());//2018-03-27T14:07:32.668
//        System.out.println(localDateTime.toLocalDate() + " " +localDateTime.toLocalTime());//2018-03-27 14:48:57.453
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//This class is immutable and thread-safe.@since 1.8
//        System.out.println(dateTimeFormatter.format(localDateTime));//2018-03-27 14:52:57
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    public Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        return date;
//        System.out.println(date.toString());//Tue Mar 27 14:17:17 CST 2018
    }

    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2017-10-15 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getForeverTime() {
        if (FOREVER_TIME == null) {
            FOREVER_TIME = getDate(FOREVER_TIME_STR);
        }
        return FOREVER_TIME;
    }

}