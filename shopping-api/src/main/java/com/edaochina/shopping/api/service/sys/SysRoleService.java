package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.sys.SysRole;
import com.edaochina.shopping.domain.vo.sys.SysRoleVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @since 2019-06-20
 */
public interface SysRoleService {

    List<SysRoleVO> roleList();

    SysRole selectById(Integer id);

    int insertRole(SysRole sysRole);

    int updateRole(SysRole sysRole);

    int updateUseFlag(Integer id, Integer useFlag);

    int deleteRole(Integer id);
}
