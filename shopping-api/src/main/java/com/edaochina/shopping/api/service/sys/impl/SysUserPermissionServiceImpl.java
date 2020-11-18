package com.edaochina.shopping.api.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.sys.SysUserPermissionMapper;
import com.edaochina.shopping.api.service.sys.SysUserPermissionService;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.domain.dto.sys.SysUserPermissionDTO;
import com.edaochina.shopping.domain.entity.sys.SysUserPermission;
import com.edaochina.shopping.domain.vo.user.AccountVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账户权限服务实现类
 * @since : 2019/7/26
 */
@Service
public class SysUserPermissionServiceImpl extends ServiceImpl<SysUserPermissionMapper, SysUserPermission> implements SysUserPermissionService {

    private final AccountService accountService;

    public SysUserPermissionServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * 批量新增数据
     *
     * @param dto 参数
     * @return 新增结果
     */
    @Override
    public int batchInsert(SysUserPermissionDTO dto) {
        Optional<AccountVO> accountVO = accountService.selectOne(dto.getUserId(), dto.getMemberType());
        if (!accountVO.isPresent()) {
            return 0;
        }
        List<SysUserPermission> sysUserPermissions = dto.getMenuIds().stream()
                .map(menuId -> {
                    SysUserPermission sysUserPermission = new SysUserPermission();
                    sysUserPermission.setMenuId(menuId);
                    sysUserPermission.setAccountId(accountVO.get().getId());
                    sysUserPermission.setMemberType(Integer.parseInt(dto.getMemberType()));
                    return sysUserPermission;
                })
                .collect(Collectors.toList());
        QueryWrapper<SysUserPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserPermission::getAccountId, accountVO.get().getId());
        this.remove(queryWrapper);
        return this.saveBatch(sysUserPermissions) ? sysUserPermissions.size() : 0;
    }
}
