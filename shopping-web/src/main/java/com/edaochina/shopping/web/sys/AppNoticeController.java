package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.service.sys.SysNoticeService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jintian
 * @date 2019/4/16 15:09
 */
@RestController
@RequestMapping("/app/sysNotice")
public class AppNoticeController {

    @Resource
    SysNoticeService sysNoticeService;

    @RequestMapping("noticeList")
    public AjaxResult appNoticeList() {
        return BaseResult.successResult(sysNoticeService.appNoticeList());
    }
}
