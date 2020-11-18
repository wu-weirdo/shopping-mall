package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.service.trade.WithdrawalSpecialUserService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.entity.trade.WithdrawalSpecialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提现特殊用户管理
 *
 * @author jintian
 * @date 2019/9/10 11:07
 */
@RequestMapping("sys/withdrawalUser")
@RestController
public class WithdrawalSpecialUserController {

    @Autowired
    private WithdrawalSpecialUserService service;

    @PostMapping("addWithrawalUser")
    public AjaxResult addWithrawalUser(@RequestBody WithdrawalSpecialUser user) {
        synchronized (user.getUserId().intern()) {
            return new AjaxResult(service.addWithrawalUser(user));
        }
    }

    @PostMapping("editWithrawalUser")
    public AjaxResult editWithrawalUser(@RequestBody WithdrawalSpecialUser user) {
        return new AjaxResult(service.editWithrawalUser(user));
    }

    @RequestMapping("delWithrawalUser")
    public AjaxResult delWithrawalUser(Integer id) {
        return new AjaxResult(service.delWithrawalUser(id));
    }


    @RequestMapping("queryWithrawalUsers")
    public AjaxResult queryWithrawalUsers(@RequestBody Pages pages) {
        PageHelperUtils.setPageHelper(pages);
        return new AjaxResult(PageHelperUtils.parseToPageResult(service.queryWithrawalUsers()));
    }

}
