package com.edaochina.shopping.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.sys.SysUserButtonPermissionDTO;
import com.edaochina.shopping.domain.entity.sys.SysUserButtonPermission;


/**
 * 按钮权限表(SysUserButtonPermission)表服务接口
 *
 * @author wangpenglei
 * @since 2019-07-26 16:05:36
 */
public interface SysUserButtonPermissionService extends IService<SysUserButtonPermission> {

    int batchInsert(SysUserButtonPermissionDTO dto);
}