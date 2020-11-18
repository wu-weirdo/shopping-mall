package com.edaochina.shopping.common.handler;

import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.common.utils.JsonUtils;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.constants.RedisConstants;
import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author :         zzk
 * @createDate :     2018/10/16 10:37
 * @description :
 */
public class YidaoHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.reset();
        // 设置编码格式
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 获取请求头的 JWT token
        String token = httpServletRequest.getHeader("token");
        // 用户类型为空
        if (StringUtils.isBlank(token)) {
            // 获取响应画笔
            PrintWriter printWriter = httpServletResponse.getWriter();
            return loginExpired(printWriter);
        }
        // 取得 JWT
        JWTBean jwtBean = JWTUtil.verifyToken(token);
        // 判断 JWT 取得的 jwtBean 参数是否完全
        if (checkIsNull(jwtBean)) {
            // 获取响应画笔
            PrintWriter printWriter = httpServletResponse.getWriter();
            return loginExpired(printWriter);
        }
        // 获取用户ID
        String userId = jwtBean.getId();
        // 根据用户编号获取 redis 上本用户的 token
        //String redisKey = RedisConstants.SYS_TOKEN + jwtBean.getRole() + "-" + userId;
        String redisKey = RedisConstants.SYS_TOKEN + userId;
        String redisToken = RedisTool.get(redisKey);
        // 假如 redis 内不存在 token 阻断程序运行
        if (redisToken == null) {
            // 获取响应画笔
            PrintWriter printWriter = httpServletResponse.getWriter();
            return printFailResult(printWriter);
        }
        // 假如 redis token 不等于前端传入 token 阻断程序运行
        if (!Objects.equal(redisToken, token)) {
            // 获取响应画笔
            PrintWriter printWriter = httpServletResponse.getWriter();
            return printFailResult(printWriter);
        }
        //鉴权通过,刷新token为两小时
        RedisTool.expire(redisKey, 60 * 60 * 2L);
        httpServletResponse.setHeader("token", token);
        return true;
    }

    /**
     * 判断 JWTBean 是否有空
     *
     * @param jwtBean jason web token bean
     * @return 是否为空 true 为空
     */
    private boolean checkIsNull(JWTBean jwtBean) {
        if (jwtBean == null) {
            return true;
        }
        return jwtBean.getId() == null;
    }

    /**
     * 阻断无资格请求
     *
     * @param printWriter 字符输出
     * @return false 阻断程序继续运行
     */
    private boolean printFailResult(PrintWriter printWriter) {
        AjaxResult ajaxResult = BaseResult.failedResult(
                ReturnData.UNAUTHORIZED.getCode(), ReturnData.UNAUTHORIZED.getMsg());
        printWriter.print(JsonUtils.toJson(ajaxResult));
        printWriter.flush();
        return false;
    }

    /**
     * 登录过期
     *
     * @param printWriter 字符输出
     * @return false 阻断程序继续运行
     */
    private boolean loginExpired(PrintWriter printWriter) {
        AjaxResult ajaxResult = BaseResult.failedResult(
                ReturnData.CODE_WORING.getCode(), ReturnData.CODE_WORING.getMsg());
        printWriter.print(JsonUtils.toJson(ajaxResult));
        return false;
    }
}
