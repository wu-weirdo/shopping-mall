package com.edaochina.shopping.common.frame;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

/**
 * 请求内容已及返回内容日志添加
 *
 * @author jintian
 * @date 2019/3/13 13:41
 */
@ControllerAdvice(basePackages = "com.edaochina.shopping.web")
public class RequestLog implements ResponseBodyAdvice {

    private static Logger logger = LoggerFactory.getLogger(RequestLog.class);


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) serverHttpRequest;
        // 获取请求地址
        String requestUri = httpRequest.getURI().getPath();
        String params = "";
        Map map = httpRequest.getServletRequest().getParameterMap();
        params = JSON.toJSONString(map);
        // 非系统日志打印（后端系统很多查询列表打印内容浪费资源）
        if (!requestUri.startsWith("/sys") && !requestUri.contains("list") && !requestUri.contains("List")) {
            logger.info(requestUri + " result:" + JSON.toJSONString(o));
        }
        return o;
    }
}
