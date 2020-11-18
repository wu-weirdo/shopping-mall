package com.edaochina.shopping.web.sys;


import com.edaochina.shopping.api.service.sys.SysPermissionService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.SysPermissionDTO;
import com.edaochina.shopping.domain.vo.sys.SysPermissionVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author User
 * @since 2019-06-20
 */
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

    private final SysPermissionService permissionService;

    public SysPermissionController(SysPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping("/menuIds")
    public AjaxResult menuIds(Integer roleId) {
        return BaseResult.successResult(permissionService.menuIdsByRoleId(roleId));
    }

    @GetMapping("user/menuIds")
    public AjaxResultGeneric<List<String>> userMenuIds(String userId, String memberType) {
        return BaseResult.successGenericResult(permissionService.menuIdsByAccountId(userId, memberType));
    }

    @RequestMapping("/add")
    public AjaxResult addPermission(@RequestBody SysPermissionDTO dto) {
        return BaseResult.successResult(permissionService.insertPermission(dto));
    }

    @GetMapping("/refresh")
    public AjaxResultGeneric<SysPermissionVO> refresh(@RequestHeader("token") String token) {
        return BaseResult.successGenericResult(permissionService.refresh(token));
    }

}

