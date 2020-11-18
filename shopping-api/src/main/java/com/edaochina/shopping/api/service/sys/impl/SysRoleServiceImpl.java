package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.SysRoleMapper;
import com.edaochina.shopping.api.dao.user.SysUserAdminMapper;
import com.edaochina.shopping.api.service.sys.SysRoleService;
import com.edaochina.shopping.domain.entity.sys.SysRole;
import com.edaochina.shopping.domain.vo.sys.SysRoleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @since 2019-06-20
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserAdminMapper adminMapper;

    @Override
    public List<SysRoleVO> roleList() {
        return sysRoleMapper.roleList();
    }

    @Override
    public SysRole selectById(Integer id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public int insertRole(SysRole sysRole) {
        return sysRoleMapper.insertRole(sysRole);
    }

    @Override
    public int updateRole(SysRole sysRole) {
        return sysRoleMapper.updateRole(sysRole);
    }

    @Override
    public int updateUseFlag(Integer id, Integer useFlag) {
        return sysRoleMapper.updateUseFlag(id, useFlag);
    }

    @Override
    public int deleteRole(Integer id) {
        List<String> adminIds = adminMapper.selectIdByRole(id);
        adminIds.forEach(adminId -> {
            adminMapper.updateRoleId(adminId, 0);
        });
        return sysRoleMapper.deleteRole(id);
    }
}
