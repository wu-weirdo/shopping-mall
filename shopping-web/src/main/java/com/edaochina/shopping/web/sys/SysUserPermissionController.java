package com.edaochina.shopping.web.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.service.sys.SysUserPermissionService;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.SysUserPermissionDTO;
import com.edaochina.shopping.domain.entity.sys.SysUserPermission;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 权限表(SysUserPermission)表控制层
 *
 * @author wangpenglei
 * @since 2019-07-26 16:15:09
 */
@RestController
@RequestMapping("sys/user/permission")
public class SysUserPermissionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserPermissionService sysUserPermissionService;

    /**
     * 分页查询所有数据
     *
     * @param page              分页对象
     * @param sysUserPermission 查询实体
     * @return 所有数据
     */
    @GetMapping
    public AjaxResultGeneric selectAll(Page<SysUserPermission> page, SysUserPermission sysUserPermission) {
        return BaseResult.successGenericResult(this.sysUserPermissionService.page(page, new QueryWrapper<>(sysUserPermission)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResultGeneric selectOne(@PathVariable Serializable id) {
        return BaseResult.successGenericResult(this.sysUserPermissionService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysUserPermission 实体对象
     * @return 新增结果
     */
    @PostMapping
    public AjaxResultGeneric insert(@RequestBody SysUserPermission sysUserPermission) {
        return BaseResult.successGenericResult(this.sysUserPermissionService.save(sysUserPermission));
    }

    /**
     * 批量新增数据
     *
     * @param dto 参数
     * @return 新增结果
     */
    @PostMapping("batch")
    public AjaxResultGeneric<Integer> batchInsert(@RequestBody SysUserPermissionDTO dto) {
        return BaseResult.successGenericResult(this.sysUserPermissionService.batchInsert(dto));
    }

    /**
     * 修改数据
     *
     * @param sysUserPermission 实体对象
     * @return 修改结果
     */
    @PutMapping
    public AjaxResultGeneric update(@RequestBody SysUserPermission sysUserPermission) {
        return BaseResult.successGenericResult(this.sysUserPermissionService.updateById(sysUserPermission));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public AjaxResultGeneric delete(@RequestParam("idList") List<Long> idList) {
        return BaseResult.successGenericResult(this.sysUserPermissionService.removeByIds(idList));
    }
}