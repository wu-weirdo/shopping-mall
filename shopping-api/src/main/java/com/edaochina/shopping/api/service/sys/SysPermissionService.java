package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.dto.sys.SysPermissionDTO;
import com.edaochina.shopping.domain.vo.sys.SysPermissionVO;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @since 2019-06-20
 */
public interface SysPermissionService {

    List<String> menuIdsByRoleId(Integer roleId);

    int insertPermission(SysPermissionDTO dto);

    /**
     * 返回用户权限
     *
     * @param token 用户token
     * @return 权限
     */
    SysPermissionVO refresh(String token);

    List<String> menuIdsByAccountId(String userId, String memberType);
}
