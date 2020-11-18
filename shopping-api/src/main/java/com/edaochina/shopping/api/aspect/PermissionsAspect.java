package com.edaochina.shopping.api.aspect;

import com.edaochina.shopping.common.annotation.PermissionsMark;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 检查权限
 *
 * @author wangpenglei
 * @since 2019/10/11 15:21
 */
@Component
@Aspect
public class PermissionsAspect {

    @Pointcut("@annotation(com.edaochina.shopping.common.annotation.PermissionsMark)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void check(JoinPoint joinPoint) {
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        PermissionsMark permissionsMark = method.getAnnotation(PermissionsMark.class);
        int[] roles = permissionsMark.value();

        JWTBean bean = JWTThreadLocalHelper.get();
        if (bean == null) {
            throw new YidaoException("用户信息缺失!");
        }
        if (Arrays.stream(roles).mapToObj(String::valueOf).noneMatch(role -> role.equals(bean.getRole()))) {
            throw new YidaoException("用户无权限!");
        }
    }

}
