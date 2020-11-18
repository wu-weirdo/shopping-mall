package com.edaochina.shopping.common.handler;

import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.filter.help.EbHttpServletWrapper;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.mail.MailService;
import org.slf4j.MDC;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

/**
 * @author :         Jason
 * @createDate :     2018/10/11 14:41
 * @description :    全局异常处理
 */
@RestControllerAdvice
@EnableWebMvc
public class RestExceptionHandler {

    private final MailService mailService;
    private final HttpServletRequest request;
    @Value("${spring.profiles.active}")
    private String active;
    private final static String MAIL_ACTIVE = "config";
    @Value("${developer.mail}")
    private List<String> mails;

    public RestExceptionHandler(MailService mailService, HttpServletRequest request) {
        this.mailService = mailService;
        this.request = request;
    }


    /**
     * 拦截类型不匹配异常
     *
     * @param ex 异常实体
     * @return 指定格式的 result bean
     */
    @ExceptionHandler(TypeMismatchException.class)
    public AjaxResult typeMismatchException(TypeMismatchException ex) {
        LoggerUtils.error(this.getClass(), ex.getMessage(), ex);
        return BaseResult.failedResult(ReturnData.TYPE_MISMATCH.getCode(),
                ReturnData.TYPE_MISMATCH.getMsg());
    }

    /**
     * 拦截消息不可读异常
     *
     * @param ex 异常实体
     * @return 指定格式的 result bean
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AjaxResult httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        report(ex);
        LoggerUtils.error(this.getClass(), ex.getMessage(), ex);
        return BaseResult.failedResult(ReturnData.TYPE_MISMATCH.getCode(),
                ReturnData.TYPE_MISMATCH.getMsg());
    }

    /**
     * 拦截参数缺失异常
     *
     * @param ex 异常实体
     * @return 指定格式的 result bean
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public AjaxResult missingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        report(ex);
        LoggerUtils.error(this.getClass(), ex.getMessage(), ex);
        return BaseResult.failedResult(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(),
                ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
    }

    /**
     * 拦截自定义异常
     *
     * @param ex 自定义异常
     * @return 指定格式的 result bean
     */
    @ExceptionHandler(YidaoException.class)
    public AjaxResult yidaoException(YidaoException ex) {
        LoggerUtils.error(this.getClass(), ex.getMessage(), ex);
        return BaseResult.failedResult(ex.getErrorCode(), ex.getErrorMsg());
    }

    /**
     * 拦截运行时异常
     *
     * @param ex 运行时异常
     * @return 指定格式的 result bean
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult runtimeException(RuntimeException ex) {
        report(ex);
        LoggerUtils.error(ex.getClass(), ex.getMessage(), ex);
        return BaseResult.failedResult(ReturnData.SYSTEM_ERROR.getCode(),
                ReturnData.SYSTEM_ERROR.getMsg());
    }

    /**
     * 拦截运行时异常
     *
     * @param ex 运行时异常
     * @return 指定格式的 result bean
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult exception(Exception ex) {
        report(ex);
        LoggerUtils.error(this.getClass(), ex.getMessage(), ex);
        return BaseResult.failedResult(ReturnData.SYSTEM_ERROR.getCode(),
                ReturnData.SYSTEM_ERROR.getMsg());
    }

    /**
     * 发生异常时邮件通知开发者，一小时内不重复通知
     *
     * @param e 异常
     */
    private void report(Exception e) {
        if (!Objects.equals(active, MAIL_ACTIVE)) {
            return;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        StringBuilder contentBuilder = new StringBuilder("RequestBody: \n");
        try {
            EbHttpServletWrapper wrapper = new EbHttpServletWrapper(request);
            contentBuilder.append(wrapper.getBody()).append("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        contentBuilder.append("Url: \n").append(request.getRequestURI()).append("\n");
        contentBuilder.append("ThreadKey: \n").append(MDC.get("key")).append("\n");
        contentBuilder.append("QueryString: \n").append(request.getQueryString()).append("\n");
        String content = contentBuilder.append("Exception: \n").append(sw.toString()).toString();
        mails.forEach(mail -> mailService.sendExceptionMail(mail, "24H发生异常", content));
    }

}
