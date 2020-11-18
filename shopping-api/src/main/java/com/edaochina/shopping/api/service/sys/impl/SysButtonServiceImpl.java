package com.edaochina.shopping.api.service.sys.impl;


import com.edaochina.shopping.api.dao.sys.SysButtonMapper;
import com.edaochina.shopping.api.dao.user.SysMenuMapper;
import com.edaochina.shopping.api.service.sys.SysButtonService;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.domain.entity.sys.SysButton;
import com.edaochina.shopping.domain.vo.sys.SysButtonVO;
import com.edaochina.shopping.domain.vo.sys.SysMenuVO;
import com.edaochina.shopping.domain.vo.sys.SysPermissionInfo;
import com.edaochina.shopping.domain.vo.user.AccountVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 按钮表 服务实现类
 * </p>
 *
 * @since 2019-06-20
 */
@Service
public class SysButtonServiceImpl implements SysButtonService {

    @Resource
    private SysButtonMapper buttonMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    private final AccountService accountService;

    public SysButtonServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Collection<SysPermissionInfo> buttonList(Integer roleId) {
        List<SysButtonVO> buttonList = buttonMapper.buttonList(roleId);
        return buttonList2PermissionInfoList(buttonList);
    }

    private Collection<SysPermissionInfo> buttonList2PermissionInfoList(List<SysButtonVO> buttonList) {
        Map<String, SysPermissionInfo> permissionInfoMap = new LinkedHashMap<>();
        buttonList.forEach(sysButton -> {
            SysPermissionInfo menu;
            if (!permissionInfoMap.containsKey(sysButton.getMenuId())) {
                SysMenuVO menuVO = sysMenuMapper.queryById(sysButton.getMenuId());
                if (menuVO == null) {
                    return;
                }
                menu = new SysPermissionInfo(sysButton.getMenuId(), menuVO.getTitle(), new ArrayList<>());
            } else {
                menu = permissionInfoMap.get(sysButton.getMenuId());
            }

            List<SysButtonVO> menuChildren = menu.getChildren();
            menuChildren.add(sysButton);
            menu.setChildren(menuChildren);
            permissionInfoMap.put(sysButton.getMenuId(), menu);
        });
        return permissionInfoMap.values();
    }

    @Override
    public int insertButton(SysButton sysButton) {
        return buttonMapper.insertButton(sysButton);
    }

    @Override
    public SysButton selectById(Integer id) {
        return buttonMapper.selectById(id);
    }

    @Override
    public List<String> buttonAliasList(Integer roleId) {
        return buttonMapper.buttonAliasList(roleId);
    }

    @Override
    public List<String> buttonUserAliasList(Integer accountId) {
        return buttonMapper.buttonUserAliasList(accountId);
    }

    @Override
    public List<String> allButtonAlias() {
        return buttonMapper.allButtonAlias();
    }

    @Override
    public Collection<SysPermissionInfo> selectByAccountId(String userId, String memberType) {
        Optional<AccountVO> optional = accountService.selectOne(userId, memberType);
        if (!optional.isPresent()) {
            return Collections.emptyList();
        }
        List<SysButtonVO> buttonList = buttonMapper.selectByAccountId(optional.get().getId());
        return new ArrayList<>(buttonList2PermissionInfoList(buttonList));
    }
}
