package com.edaochina.shopping.api.dao.sys;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @since 2019-06-20
 */
@Repository
public interface SysPermissionMapper {

    List<String> selectMenuIdsByRoleId(@Param("roleId") Integer roleId);

    List<String> selectMenuIdsByAccountId(@Param("accountId") Integer accountId);

    int insertPermission(@Param("roleId") Integer roleId, @Param("menuId") String menuId);

    int deletePermission(@Param("roleId") Integer roleId);
}
