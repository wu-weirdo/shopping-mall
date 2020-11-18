package com.edaochina.shopping.api.dao.sys;

import com.edaochina.shopping.domain.entity.sys.SysRole;
import com.edaochina.shopping.domain.vo.sys.SysRoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @since 2019-06-20
 */
public interface SysRoleMapper {

    List<SysRoleVO> roleList();

    SysRole selectById(@Param("id") Integer id);

    int insertRole(SysRole sysRole);

    int updateRole(SysRole sysRole);

    int updateUseFlag(@Param("id") Integer id, @Param("useFlag") Integer useFlag);

    int deleteRole(@Param("id") Integer id);

}
