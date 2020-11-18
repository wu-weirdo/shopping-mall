package com.edaochina.shopping.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.sys.SysUserPermissionDTO;
import com.edaochina.shopping.domain.entity.sys.SysUserPermission;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账户权限服务
 * @since : 2019/7/26
 */
public interface SysUserPermissionService extends IService<SysUserPermission> {

    /**
     * 批量新增数据
     *
     * @param dto 参数
     * @return 新增结果
     */
    int batchInsert(SysUserPermissionDTO dto);
}
