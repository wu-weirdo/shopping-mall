/*
package com.edaochina.shopping.common.aspect;

import com.edaochina.shopping.common.annotation.CheckSysUserRole;
import com.edaochina.shopping.domain.constants.RedisConstants;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.common.utils.JsonUtils;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.vo.user.MenuChildrenVO;
import com.edaochina.shopping.domain.vo.user.MenuVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

*/
/**
 * @author :         Jason
 * @createDate :     2018/10/15 18:55
 * @description :
 *//*

@Aspect
@Component
public class RoleCheckAspect {

    @Pointcut("@annotation(com.edaochina.shopping.common.annotation.CheckSysUserRole)")
    private void pointCut() {
    }

    @Around("pointCut()&&@annotation(checkSysUserRole)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, CheckSysUserRole checkSysUserRole)
            throws Throwable {
        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        // 获取 JWT
        String jwt = request.getHeader("token");
        // 通过工具类检验 JWT 获取 JWTBean
        JWTBean jwtBean = JWTUtil.verifyToken(jwt);
        // 获取 JSON 格式菜单列表
        String menu = RedisTool.get(RedisConstants.ROLE + jwtBean.getRole());
        List<MenuVO> menuVOList = JsonUtils.toList(menu, MenuVO.class);
        // 获取注解允许的菜单列表
        String[] menuIdList = checkSysUserRole.menuId();
        Boolean haveAccess = false;
        // 遍历菜单列表和允许的菜单列表
        for (MenuVO menuVO : menuVOList) {
            // 获取子菜单列表
            List<MenuChildrenVO> menuChildVOS = menuVO.getChildren();
            if (menuVO.getChildren() == null) {
                continue;
            }
            // 遍历子菜单和接口指定菜单 判断用户是否有资格
            for (MenuChildrenVO children : menuChildVOS) {
                for (String menuId : menuIdList) {
                    if (menuId.equals("" + children.getId())) {
                        haveAccess = true;
                    }
                }
            }
        }
        // 如果未拥有权限则返回 AjaxResult
        if (!haveAccess) {
            return BaseResult.failedResult(ReturnData.INVALID_PRIVILEGE.getCode(),
                    ReturnData.INVALID_PRIVILEGE.getMsg());
        }
        // 如果拥有权限则继续运行
        return proceedingJoinPoint.proceed();
    }
}
*/
