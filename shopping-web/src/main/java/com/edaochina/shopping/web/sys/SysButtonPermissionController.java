package com.edaochina.shopping.web.sys;


import com.edaochina.shopping.api.service.sys.SysButtonPermissionService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.SysButtonPermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 按钮权限表 前端控制器
 * </p>
 *
 * @since 2019-06-20
 */
@RestController
@RequestMapping("/sys/buttonPermission")
public class SysButtonPermissionController {

    @Autowired
    private SysButtonPermissionService buttonPermissionService;

    @RequestMapping("/list")
    public AjaxResult buttonPermissionList(Integer roleId) {
        return BaseResult.successResult(buttonPermissionService.buttonPermissionList(roleId));
    }

    @RequestMapping("/add")
    public AjaxResult addButtonPermission(@RequestBody SysButtonPermissionDTO dto) {
        return BaseResult.successResult(buttonPermissionService.insertButtonPermission(dto));
    }
}

