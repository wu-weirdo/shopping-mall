package com.edaochina.shopping.web.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.service.user.SysMenuService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.annotation.PermissionsMark;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.AssertUtils;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.entity.user.SysMenu;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SysMerchantController
 *
 * @author wangpenglei
 * @since 2019/11/5 11:46
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    private final SysMenuService menuService;

    public MenuController(SysMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public AjaxResultGeneric<List<SysMenu>> list() {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        return BaseResult.successGenericResult(menuService.list(queryWrapper));
    }

    @PutMapping("{id}")
    @OperationLogMark("菜单排序编辑")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE})
    public AjaxResultGeneric<Boolean> quitLeague(@PathVariable String id, @RequestBody SysMenu order) {
        SysMenu menu = menuService.getById(id);
        AssertUtils.checkNotNull(menu, "菜单不存在!");
        menu.setSort(order.getSort());
        return BaseResult.successGenericResult(menuService.updateById(menu));
    }
}
