package com.edaochina.shopping.api.aspect;

import com.edaochina.shopping.api.service.sys.OperationLogService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.common.utils.JsonUtils;
import com.edaochina.shopping.common.utils.StringUtil;
import com.edaochina.shopping.common.utils.ip.IPUtils;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import com.edaochina.shopping.domain.entity.sys.OperationLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 操作日志切面处理类
 * @since : 2019/7/2 10:18
 */
@Component
@Aspect
public class OperationLogAspect {

    private final HttpServletRequest request;
    private final OperationLogService operationLogService;

    public OperationLogAspect(HttpServletRequest request, OperationLogService operationLogService) {
        this.request = request;
        this.operationLogService = operationLogService;
    }

    @Pointcut("@annotation(com.edaochina.shopping.common.annotation.OperationLogMark)")
    public void logPointCut() {
    }

    @AfterReturning("logPointCut()")
    @Async
    public void saveLog(JoinPoint joinPoint) {
        //保存日志
        OperationLog log = new OperationLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        OperationLogMark operationLogMark = method.getAnnotation(OperationLogMark.class);
        log.setOperation(operationLogMark.value());
        log.setRemark(operationLogMark.remark());

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        log.setMethod(className + "." + methodName);

        //请求的参数
        List<Object> args = Arrays.asList(joinPoint.getArgs())
                .parallelStream()
                .peek(o -> {
                    if (o instanceof LoginDTO) {
                        ((LoginDTO) o).setPassword("");
                    }
                })
                .filter(o -> !(o instanceof BindingResult))
                .collect(Collectors.toList());
        //将参数所在的数组转换成json
        String params = JsonUtils.toJson(args);
        log.setParams(StringUtil.filterEmoji(params));

        String token = request.getHeader("token");
        try {
            JWTBean jwtBean = JWTUtil.verifyToken(token);
            log.setUserId(jwtBean.getId());
            log.setUserType(jwtBean.getRole());
            log.setUserName(jwtBean.getName());
        } catch (Exception e) {
            log.setRemark(e.getMessage());
        }
        log.setCreateTime(LocalDateTime.now());
        log.setIp(IPUtils.getIpAddr(request));
        log.setUrl(request.getRequestURI());
        operationLogService.save(log);
    }

}
