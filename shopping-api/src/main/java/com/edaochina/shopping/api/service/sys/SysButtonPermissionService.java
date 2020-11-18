package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.dto.sys.SysButtonPermissionDTO;

import java.util.List;

/**
 * <p>
 * 按钮权限表 服务类
 * </p>
 *
 * @since 2019-06-20
 */
public interface SysButtonPermissionService {

    List<Integer> buttonPermissionList(Integer roleId);

    int insertButtonPermission(SysButtonPermissionDTO dto);
}
