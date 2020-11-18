package com.edaochina.shopping.common.filter;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.common.utils.IOUtils;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.filter.help.EbHttpServletWrapper;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.common.utils.StringUtil;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.ip.IPUtils;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author jintian
 * @date 2019/3/20 11:40
 */
@Component
@WebFilter
public class LoggerFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.clear();
        // 日志中放一个 key 便于日志查看
        MDC.put("key", StringUtil.UUIDStr());
        JWTThreadLocalHelper.clear();
        // 请求前判断是否是app过来的请求，是的话打印请求参数
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestUri = request.getRequestURI();
        logger.info(requestUri);
        // 检查页面是否过期
        if (!checkIsAppReq(requestUri) && checkPageVersion(request, requestUri)) {
            OutputStream out = servletResponse.getOutputStream();
            AjaxResult ajaxResult = BaseResult.failedResult(
                    ReturnData.PAGE_OVER_TIME.getCode(), ReturnData.PAGE_OVER_TIME.getMsg());
            org.apache.commons.io.IOUtils.write(ajaxResult.toJson(), out);
            return;
        }
        if (!checkIsAppReq(requestUri) && checkLogin(request, requestUri)) {
            OutputStream out = servletResponse.getOutputStream();
            AjaxResult ajaxResult = BaseResult.failedResult(
                    ReturnData.CODE_WORING.getCode(), ReturnData.CODE_WORING.getMsg());
            org.apache.commons.io.IOUtils.write(ajaxResult.toJson(), out);
            return;
        }
        if (checkIsNeedLog(requestUri)) {
            writeReqInfoToLog(request, requestUri, servletResponse, filterChain);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        MDC.clear();
        JWTThreadLocalHelper.clear();
    }

    // 检查页面版本号是否过期
    private boolean checkPageVersion(HttpServletRequest request, String requestUri) {
        return false;
        //return !requestUri.startsWith("/sys/user/login") && !isApiDocs(requestUri);
        /*String version = request.getHeader("version");
        if (StringUtils.isBlank(version)) {
            return true;
        }
        */
        // TODO 页面版本号
    }


    /**
     * 判断用户是否登录
     *
     * @param request    请求
     * @param requestUri 请求地址
     * @return 是否登录
     */
    private boolean checkLogin(HttpServletRequest request, String requestUri) {
        if (requestUri.contains("/sys/user/login") || isApiDocs(requestUri)) {
            return false;
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return true;
        }
        JWTBean jwtBean = JWTUtil.verifyToken(token);
        if (jwtBean == null) {
            return true;
        } else {
            String latestToken = RedisTool.get(jwtBean.getTokenKey());
            if (!token.equals(latestToken)) {
                // 旧token,需要重新登录
                return true;
            }
            logger.info("当前用户: " + jwtBean);
            JWTThreadLocalHelper.put(jwtBean);
            return false;
        }
    }

    private boolean isApiDocs(String uri) {
        return uri.contains("swagger") || uri.contains("v2/api-docs") || uri.contains("csrf");
    }

    private boolean checkIsNeedLog(String requestUri) {
        return !requestUri.contains("/app/wxpay") &&
                !requestUri.contains("/app/tencent")
                && !requestUri.contains("/common");
    }

    /**
     * 将请求内容写入日志
     *
     * @param request
     */
    private void writeReqInfoToLog(HttpServletRequest request, String uri, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String ip = IPUtils.getIpAddr(request);
        // 判断是否是post请求，是的话读取流内容，复制流
        if ("GET".equals(request.getMethod())) {
            logger.info(uri + " ,req ip : " + ip + JWTThreadLocalHelper.logRoleAndUser() + " ,req:" + JSON.toJSONString(request.getParameterMap()));
            filterChain.doFilter(request, servletResponse);
        } else {
            // post请求获取servletRequest 的requestParament内容
            try {
                EbHttpServletWrapper ebHttpServletWrapper = new EbHttpServletWrapper(request);
                ServletInputStream inputStream = ebHttpServletWrapper.getInputStream();
                String reqString = IOUtils.readStreamAsString(inputStream, "UTF-8");
                logger.info(uri + ",req ip : " + ip + JWTThreadLocalHelper.logRoleAndUser() + " ,req:" + reqString + ", params : " + JSON.toJSONString(request.getParameterMap()));
                filterChain.doFilter(ebHttpServletWrapper, servletResponse);
            } catch (Exception e) {

            }
        }
    }

    /**
     * 判断是否是app发过来的请求
     *
     * @param requestUri
     * @return
     */
    private boolean checkIsAppReq(String requestUri) {
        return requestUri.contains("/app/")
                || requestUri.contains("/common")
                || requestUri.contains("/address/area/hasAgentAreaList")
                || requestUri.contains("/community/listByCountyId")
                || requestUri.contains("/detail/merchant")
                // 排除掉直播相关的，前台也要用这个
                || requestUri.contains("live");
    }

    @Override
    public void destroy() {

    }
}