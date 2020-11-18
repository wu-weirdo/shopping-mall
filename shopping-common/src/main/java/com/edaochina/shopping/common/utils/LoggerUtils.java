package com.edaochina.shopping.common.utils;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :         Jason
 * @createDate :     2018/10/15 9:37
 * @description :
 */
@UtilityClass
public class LoggerUtils {

    /**
     * logger 类
     */
    private static Logger logger = null;

    /**
     * 记录日志debug信息
     *
     * @param recordClass 记录的类
     * @param info        重要信息
     */
    public void debug(Class recordClass, String info) {
        logger = LoggerFactory.getLogger(recordClass);
        logger.debug(info);
    }

    /**
     * 记录日志重要信息
     *
     * @param recordClass 记录的类
     * @param info        重要信息
     */
    public void info(Class recordClass, String info) {
        logger = LoggerFactory.getLogger(recordClass);
        logger.info(info);
    }

    /**
     * 记录日志重要信息与异常
     *
     * @param recordClass 记录的类
     * @param info        重要信息
     * @param e           异常
     */
    public void info(Class recordClass, String info, Exception e) {
        logger = LoggerFactory.getLogger(recordClass);
        logger.info(info, e);
    }

    /**
     * 记录警告级别日志
     *
     * @param recordClass 记录类
     * @param warnInfo    警告信息
     */
    public void warn(Class recordClass, String warnInfo) {
        logger = LoggerFactory.getLogger(recordClass);
        logger.warn(warnInfo);
    }

    /**
     * 记录警告级别日志与异常
     *
     * @param recordClass 记录类
     * @param warnInfo    警告信息
     * @param e           异常
     */
    public void warn(Class recordClass, String warnInfo, Exception e) {
        logger = LoggerFactory.getLogger(recordClass);
        logger.warn(warnInfo, e);
    }

    /**
     * 记录错误级别日志
     *
     * @param recordClass 记录类
     * @param errorInfo   错误信息
     */
    public void error(Class recordClass, String errorInfo) {
        logger = LoggerFactory.getLogger(recordClass);
        logger.error(errorInfo);
    }

    /**
     * 记录错误级别日志及异常
     *
     * @param recordClass 记录类
     * @param errorInfo   错误信息
     * @param e           异常
     */
    public void error(Class recordClass, String errorInfo, Exception e) {
        logger = LoggerFactory.getLogger(recordClass);
        logger.error(errorInfo, e);
    }
}
