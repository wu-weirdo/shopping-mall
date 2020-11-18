package com.edaochina.shopping.web.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.service.sys.SysUserButtonPermissionService;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.SysUserButtonPermissionDTO;
import com.edaochina.shopping.domain.entity.sys.SysUserButtonPermission;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 按钮权限表(SysUserButtonPermission)表控制层
 *
 * @author wangpenglei
 * @since 2019-07-26 16:07:37
 */
@RestController
@RequestMapping("sys/user/button/permission")
public class SysUserButtonPermissionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserButtonPermissionService sysUserButtonPermissionService;

    /**
     * 分页查询所有数据
     *
     * @param page                    分页对象
     * @param sysUserButtonPermission 查询实体
     * @return 所有数据
     */
    @GetMapping
    public AjaxResultGeneric selectAll(Page<SysUserButtonPermission> page, SysUserButtonPermission sysUserButtonPermission) {
        return BaseResult.successGenericResult(this.sysUserButtonPermissionService.page(page, new QueryWrapper<>(sysUserButtonPermission)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResultGeneric selectOne(@PathVariable Serializable id) {
        return BaseResult.successGenericResult(this.sysUserButtonPermissionService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysUserButtonPermission 实体对象
     * @return 新增结果
     */
    @PostMapping
    public AjaxResultGeneric insert(@RequestBody SysUserButtonPermission sysUserButtonPermission) {
        return BaseResult.successGenericResult(this.sysUserButtonPermissionService.save(sysUserButtonPermission));
    }

    /**
     * 批量新增数据
     *
     * @param dto 参数
     * @return 新增结果
     */
    @PostMapping("batch")
    public AjaxResultGeneric<Integer> batchInsert(@RequestBody SysUserButtonPermissionDTO dto) {
        return BaseResult.successGenericResult(this.sysUserButtonPermissionService.batchInsert(dto));
    }

    /**
     * 修改数据
     *
     * @param sysUserButtonPermission 实体对象
     * @return 修改结果
     */
    @PutMapping
    public AjaxResultGeneric update(@RequestBody SysUserButtonPermission sysUserButtonPermission) {
        return BaseResult.successGenericResult(this.sysUserButtonPermissionService.updateById(sysUserButtonPermission));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public AjaxResultGeneric delete(@RequestParam("idList") List<Long> idList) {
        return BaseResult.successGenericResult(this.sysUserButtonPermissionService.removeByIds(idList));
    }
}