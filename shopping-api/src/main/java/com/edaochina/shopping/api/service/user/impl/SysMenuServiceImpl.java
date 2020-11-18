package com.edaochina.shopping.api.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.SysMenuMapper;
import com.edaochina.shopping.api.service.user.SysMenuService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.domain.entity.user.SysMenu;
import com.edaochina.shopping.domain.vo.sys.SysMenuInfo;
import com.edaochina.shopping.domain.vo.sys.SysMenuVO;
import com.edaochina.shopping.domain.vo.user.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 菜单表 by 张志侃 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public MenuVO query(String id) {
        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(id)) {
            sysMenuQueryWrapper.eq("id", id);
        }
        SysMenu sysMenu = this.getOne(sysMenuQueryWrapper);
        if (sysMenu == null || sysMenu.getId() == null) {
            throw new YidaoException(ReturnData.NOT_MENU);
        }
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(sysMenu, menuVO);
        return menuVO;
    }

    @Override
    public Collection<SysMenuInfo> menuList() {
        List<SysMenuVO> sysMenuVOList = sysMenuMapper.sysMenuList();
        Map<String, SysMenuInfo> parentMenuMap = new LinkedHashMap<>();
        sysMenuVOList.forEach(sysMenuVO -> {
            SysMenuInfo parentMenu;
            if ("-1".equals(sysMenuVO.getParentId()) && !parentMenuMap.containsKey(sysMenuVO.getId())) {
                parentMenu = new SysMenuInfo(
                        sysMenuVO.getId(),
                        sysMenuVO.getTitle(),
                        sysMenuVO.getParentId(),
                        new ArrayList<>());
                parentMenuMap.put(sysMenuVO.getId(), parentMenu);
            } else if (!"-1".equals(sysMenuVO.getParentId())) {
                if (parentMenuMap.containsKey(sysMenuVO.getParentId())) {
                    parentMenu = parentMenuMap.get(sysMenuVO.getParentId());
                } else {
                    parentMenu = new SysMenuInfo(sysMenuVO.getParentId(), new ArrayList<>());
                }
                parentMenu.getChildren().add(new SysMenuInfo(sysMenuVO.getId(), sysMenuVO.getTitle(), sysMenuVO.getParentId(), null));
                parentMenuMap.put(parentMenu.getId(), parentMenu);
            } else {
                parentMenu = parentMenuMap.get(sysMenuVO.getId());
                parentMenu.setTitle(sysMenuVO.getTitle());
            }

        });
        return parentMenuMap.values();
    }

    @Override
    public List<String> sysMenuIds() {
        return sysMenuMapper.sysMenuIds();
    }

    @Override
    public List<SysMenu> selectByRoleId(Integer roleId) {
        return sysMenuMapper.selectByRoleId(roleId);
    }

    @Override
    public List<SysMenu> selectByAccountId(Integer accountId) {
        return sysMenuMapper.selectByAccountId(accountId);
    }

    @Override
    public List<SysMenu> selectByRole(String role) {
        return sysMenuMapper.selectByRole(role);
    }
}
