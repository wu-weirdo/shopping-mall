package com.edaochina.shopping.api.dao.sys;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 按钮权限表 Mapper 接口
 * </p>
 *
 * @since 2019-06-20
 */
public interface SysButtonPermissionMapper {

    List<Integer> buttonPermissionList(@Param("roleId") Integer roleId);

    int insertButtonPermission(@Param("roleId") Integer roleId, @Param("buttonId") Integer buttonId);

    int deleteButtonPermission(@Param("roleId") Integer roleId);
}
