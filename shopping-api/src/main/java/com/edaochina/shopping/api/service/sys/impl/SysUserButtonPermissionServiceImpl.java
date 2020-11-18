package com.edaochina.shopping.api.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.sys.SysUserButtonPermissionDao;
import com.edaochina.shopping.api.service.sys.SysUserButtonPermissionService;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.domain.dto.sys.SysUserButtonPermissionDTO;
import com.edaochina.shopping.domain.entity.sys.SysUserButtonPermission;
import com.edaochina.shopping.domain.vo.user.AccountVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 按钮权限表(SysUserButtonPermission)表服务实现类
 *
 * @author wangpenglei
 * @since 2019-07-26 16:06:26
 */
@Service("sysUserButtonPermissionService")
public class SysUserButtonPermissionServiceImpl extends ServiceImpl<SysUserButtonPermissionDao, SysUserButtonPermission> implements SysUserButtonPermissionService {

    private final AccountService accountService;

    public SysUserButtonPermissionServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public int batchInsert(SysUserButtonPermissionDTO dto) {
        Optional<AccountVO> accountVO = accountService.selectOne(dto.getUserId(), dto.getMemberType());
        if (!accountVO.isPresent()) {
            return 0;
        }
        List<SysUserButtonPermission> sysUserPermissions = dto.getButtonIds().stream()
                .map(buttonId -> {
                    SysUserButtonPermission sysUserButtonPermission = new SysUserButtonPermission();
                    sysUserButtonPermission.setButtonId(buttonId);
                    sysUserButtonPermission.setAccountId(accountVO.get().getId());
                    sysUserButtonPermission.setMemberType(Integer.parseInt(dto.getMemberType()));
                    return sysUserButtonPermission;
                })
                .collect(Collectors.toList());
        QueryWrapper<SysUserButtonPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserButtonPermission::getAccountId, accountVO.get().getId());
        this.remove(queryWrapper);
        return this.saveBatch(sysUserPermissions) ? sysUserPermissions.size() : 0;
    }
}