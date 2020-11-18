package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.SysPermissionMapper;
import com.edaochina.shopping.api.dao.user.SysUserAdminMapper;
import com.edaochina.shopping.api.service.sys.SysButtonService;
import com.edaochina.shopping.api.service.sys.SysPermissionService;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.api.service.user.SysMenuConfService;
import com.edaochina.shopping.api.service.user.SysMenuService;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.sys.SysPermissionDTO;
import com.edaochina.shopping.domain.entity.user.SysMenu;
import com.edaochina.shopping.domain.entity.user.SysUserAdmin;
import com.edaochina.shopping.domain.vo.sys.SysPermissionVO;
import com.edaochina.shopping.domain.vo.user.AccountVO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author User
 * @since 2019-06-20
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    private final SysPermissionMapper permissionMapper;
    private final SysButtonService sysButtonService;
    private final SysUserAdminMapper sysUserAdminMapper;
    private final SysMenuService sysMenuService;
    private final SysMenuConfService sysMenuConfService;
    private final AccountService accountService;

    public SysPermissionServiceImpl(SysPermissionMapper permissionMapper, SysButtonService sysButtonService, SysUserAdminMapper sysUserAdminMapper, SysMenuService sysMenuService, SysMenuConfService sysMenuConfService, AccountService accountService) {
        this.permissionMapper = permissionMapper;
        this.sysButtonService = sysButtonService;
        this.sysUserAdminMapper = sysUserAdminMapper;
        this.sysMenuService = sysMenuService;
        this.sysMenuConfService = sysMenuConfService;
        this.accountService = accountService;
    }

    @Override
    public List<String> menuIdsByRoleId(Integer roleId) {
        return permissionMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public SysPermissionVO refresh(String token) {
        JWTBean jwtBean = JWTUtil.verifyToken(token);
        SysPermissionVO permissionVO = SysPermissionVO.build();
        SysUserAdmin sysUserAdmin = new SysUserAdmin();
        boolean isAdmin = isAdmin(jwtBean, sysUserAdmin);
        permissionVO.setButtonAliasList(buildButtonAliasList(isAdmin, sysUserAdmin));
        permissionVO.setMenuList(buildMenuList(jwtBean, isAdmin, sysUserAdmin));
        return permissionVO;
    }

    @Override
    public List<String> menuIdsByAccountId(String userId, String memberType) {
        Optional<AccountVO> accountVO = accountService.selectOne(userId, memberType);
        if (!accountVO.isPresent()) {
            return new ArrayList<>();
        }
        return permissionMapper.selectMenuIdsByAccountId(accountVO.get().getId());
    }


    @Override
    public int insertPermission(SysPermissionDTO dto) {
        permissionMapper.deletePermission(dto.getRoleId());
        List<String> menuIds = dto.getMenuIds();
        menuIds.forEach(menuId -> permissionMapper.insertPermission(dto.getRoleId(), menuId));
        return 1;
    }

    private List<String> buildButtonAliasList(boolean isAdmin, SysUserAdmin sysUserAdmin) {
        List<String> buttonAliasList = sysButtonService.allButtonAlias();
        if (isAdmin) {
            buttonAliasList = sysButtonService.buttonAliasList(sysUserAdmin.getRoleId());
            Optional<AccountVO> accountVO = accountService.selectOne(sysUserAdmin.getId(), RoleConstants.ADMIN_ROLE_STRING);
            List<String> finalButtonAliasList = buttonAliasList;
            accountVO.ifPresent(account -> finalButtonAliasList.addAll(sysButtonService.buttonUserAliasList(account.getId())));
        }
        return buttonAliasList.stream().distinct().collect(Collectors.toList());
    }

    private List<SysMenu> buildMenuList(JWTBean jwtBean, boolean isAdmin, SysUserAdmin sysUserAdmin) {
        List<SysMenu> menuList;
        if (isAdmin) {
            menuList = sysMenuService.selectByRoleId(sysUserAdmin.getRoleId());
            Optional<AccountVO> accountVO = accountService.selectOne(sysUserAdmin.getId(), RoleConstants.ADMIN_ROLE_STRING);
            accountVO.ifPresent(account -> menuList.addAll(sysMenuService.selectByAccountId(account.getId())));
            Set<String> parentIds = menuList.parallelStream().distinct().map(SysMenu::getParentId).collect(Collectors.toSet());
            if (!parentIds.isEmpty()) {
                Collection<SysMenu> parentList = sysMenuService.listByIds(parentIds);
                menuList.addAll(parentList);
            }
        } else {
            menuList = sysMenuService.selectByRole(jwtBean.getRole());
        }
        Map<String, SysMenu> idMap = menuList.parallelStream().distinct().collect(Collectors.toMap(SysMenu::getId, menu -> menu));
        return menuList.stream()
                .peek(sysMenu -> {
                    if (Integer.parseInt(sysMenu.getParentId()) > 0) {
                        Optional<SysMenu> sysMenuOptional = Optional.ofNullable(idMap.get(sysMenu.getParentId()));
                        sysMenuOptional.ifPresent(parent -> {
                            if (parent.getChildren() == null) {
                                parent.setChildren(new ArrayList<>());
                            }
                            parent.getChildren().add(sysMenu);
                        });
                    }
                })
                .filter(sysMenu -> Integer.parseInt(sysMenu.getParentId()) < 0)
                .peek(sysMenu -> Optional.ofNullable(sysMenu.getChildren()).ifPresent(sysMenus ->
                        sysMenu.setChildren(sysMenus.parallelStream().distinct().collect(Collectors.toList()))))
                .sorted(Comparator.comparing(SysMenu::getSort))
                .collect(Collectors.toList());
    }

    private boolean isAdmin(JWTBean jwtBean, SysUserAdmin sysUserAdmin) {
        SysUserAdmin admin = null;
        boolean is = RoleConstants.ADMIN_ROLE_STRING.equals(jwtBean.getRole())
                && (admin = sysUserAdminMapper.selectById(jwtBean.getId())) != null
                && admin.getRoleId() != 1;
        if (is) {
            sysUserAdmin.setRoleId(admin.getRoleId());
            sysUserAdmin.setId(admin.getId());
        }
        return is;
    }
}
