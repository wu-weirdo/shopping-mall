package com.edaochina.shopping.web.sys;


import com.edaochina.shopping.api.service.sys.SysRoleService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.entity.sys.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2019-06-20
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @RequestMapping("/list")
    public AjaxResult roleList() {
        return BaseResult.successResult(roleService.roleList());
    }

    @RequestMapping("/add")
    @OperationLogMark("添加部门")
    public AjaxResult addRole(@RequestBody SysRole sysRole) {
        return BaseResult.successResult(roleService.insertRole(sysRole));
    }

    @RequestMapping("/update")
    @OperationLogMark("修改部门")
    public AjaxResult updRole(@RequestBody SysRole sysRole) {
        return BaseResult.successResult(roleService.updateRole(sysRole));
    }

    @RequestMapping("/use")
    @OperationLogMark("修改部门启用情况")
    public AjaxResult useRole(Integer id, Integer useFlag) {
        return BaseResult.successResult(roleService.updateUseFlag(id, useFlag));
    }

    @RequestMapping("/delete")
    @OperationLogMark("删除部门")
    public AjaxResult delRole(Integer id) {
        return BaseResult.successResult(roleService.deleteRole(id));
    }
}

