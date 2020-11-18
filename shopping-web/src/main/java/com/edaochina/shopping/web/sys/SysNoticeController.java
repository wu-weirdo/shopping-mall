package com.edaochina.shopping.web.sys;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.api.service.sys.SysNoticeService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.entity.sys.SysNotice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys/sysNotice")
public class SysNoticeController {

    @Resource
    SysNoticeService sysNoticeService;

    /**
     * 通知列表
     *
     * @return
     */
    @RequestMapping("noticeList")
    public AjaxResult noticeList() {
        return BaseResult.successResult(sysNoticeService.selectNoticeList());
    }

    /**
     * 添加通知
     *
     * @return
     */
    @RequestMapping("addNotice")
    public AjaxResult addNotice(@RequestBody SysNotice sysNotice) {
        return BaseResult.successResult(sysNoticeService.addNotice(sysNotice));
    }

    /**
     * 编辑通知
     *
     * @param sysNotice
     * @return
     */
    @RequestMapping("updateNotice")
    public AjaxResult updateNotice(@RequestBody SysNotice sysNotice) {
        return BaseResult.successResult(sysNoticeService.updateNotice(sysNotice));
    }

    /**
     * 删除通知
     *
     * @param reqJson
     * @return
     */
    @RequestMapping("deleteNotice")
    public AjaxResult deleteNotice(@RequestBody JSONObject reqJson) {
        return BaseResult.successResult(sysNoticeService.deleteNotice(reqJson.getIntValue("id")));
    }

    /**
     * 显示和隐藏通知
     *
     * @param reqJson
     * @return
     */
    @RequestMapping("updateStatus")
    public AjaxResult updateStatus(@RequestBody JSONObject reqJson) {
        return BaseResult.successResult(sysNoticeService.updateStatus(reqJson.getIntValue("id"), reqJson.getIntValue("noticeStatus")));
    }

    @RequestMapping("getNoticeShowNum")
    public AjaxResult getNoticeShowNum() {
        return BaseResult.successResult(sysNoticeService.getNoticeShowNum());
    }
}
