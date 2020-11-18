package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.SysButtonPermissionMapper;
import com.edaochina.shopping.api.service.sys.SysButtonPermissionService;
import com.edaochina.shopping.domain.dto.sys.SysButtonPermissionDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 按钮权限表 服务实现类
 * </p>
 *
 * @since 2019-06-20
 */
@Service
public class SysButtonPermissionServiceImpl implements SysButtonPermissionService {

    @Resource
    private SysButtonPermissionMapper buttonPermissionMapper;

    @Override
    public List<Integer> buttonPermissionList(Integer roleId) {
        return buttonPermissionMapper.buttonPermissionList(roleId);
    }

    @Override
    public int insertButtonPermission(SysButtonPermissionDTO dto) {
        buttonPermissionMapper.deleteButtonPermission(dto.getRoleId());
        dto.getButtonIds().forEach(buttonId -> {
            buttonPermissionMapper.insertButtonPermission(dto.getRoleId(), buttonId);
        });
        return 1;
    }
}
