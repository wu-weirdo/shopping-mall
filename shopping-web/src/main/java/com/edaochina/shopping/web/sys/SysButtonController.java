package com.edaochina.shopping.web.sys;


import com.edaochina.shopping.api.service.sys.SysButtonService;
import com.edaochina.shopping.api.service.user.SysMenuService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.entity.sys.SysButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 按钮表 前端控制器
 * </p>
 *
 * @since 2019-06-20
 */
@RestController
@RequestMapping("/sys/button")
public class SysButtonController {

    @Autowired
    private SysButtonService buttonService;

    @Autowired
    private SysMenuService menuService;

    @RequestMapping("/list/menu")
    public AjaxResult menuList() {
        return BaseResult.successResult(menuService.menuList());
    }

    @RequestMapping("/list/button")
    public AjaxResult buttonList(Integer roleId) {
        return BaseResult.successResult(buttonService.buttonList(roleId));
    }

    @GetMapping("/user")
    public AjaxResultGeneric userButtons(String userId, String memberType) {
        return BaseResult.successGenericResult(buttonService.selectByAccountId(userId, memberType));
    }

    @RequestMapping("/add")
    public AjaxResult addButton(@RequestBody SysButton sysButton) {
        return BaseResult.successResult(buttonService.insertButton(sysButton));
    }
}

