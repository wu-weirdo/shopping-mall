package com.edaochina.shopping.web.message;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.edaochina.shopping.api.service.message.MessageMetaService;
import com.edaochina.shopping.api.service.message.MessageUserService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.entity.message.MessageMeta;
import com.edaochina.shopping.domain.entity.message.MessageUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (MessageMeta)表控制层
 *
 * @author wangpenglei
 * @since 2019-09-17 15:58:46
 */
@RestController
@RequestMapping("app/message/meta")
public class AppMessageMetaController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private MessageMetaService messageMetaService;

    private final MessageUserService messageUserService;

    public AppMessageMetaController(MessageUserService messageUserService) {
        this.messageUserService = messageUserService;
    }

    @GetMapping
    public AjaxResultGeneric<PageResult> findAll(int stage, int role, String userId, Pages page) {
        return BaseResult.successGenericResult(this.messageMetaService.findAll(role, userId, stage, page));
    }

    /**
     * 未读消息数量查询
     *
     * @param stage 平台
     * @return 未读消息数量
     */
    @GetMapping("unreadCount")
    public AjaxResultGeneric<Integer> unreadCount(int stage, int role, String userId) {
        return BaseResult.successGenericResult(this.messageMetaService.findNewMessageCount(role, userId, stage));
    }

    /**
     * 未读消息查询
     *
     * @param stage 平台
     * @return 未读消息数量
     */
    @GetMapping("unread")
    public AjaxResultGeneric<PageResult> unread(int stage, int role, String userId, Pages page) {
        return BaseResult.successGenericResult(this.messageMetaService.findNewMessage(role, userId, stage, page));
    }


    /**
     * @param stage  小程序2
     * @param role
     * @param userId
     * @param page
     * @return
     */
    @GetMapping("read")
    public AjaxResultGeneric<PageResult> read(int stage, int role, String userId, Pages page) {
        return BaseResult.successGenericResult(this.messageMetaService.findOldMessage(role, userId, stage, page));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResultGeneric<? extends MessageMeta> selectOne(@PathVariable Serializable id) {
        MessageMeta messageMeta = this.messageMetaService.getById(id);
        if (messageMeta != null) {
            return BaseResult.successGenericResult(messageMeta);
        }
        return BaseResult.failedGenericResult(404, "没有此消息", null);
    }

    /**
     * 设置某消息为已读
     *
     * @param id
     * @param role
     * @param userId
     * @return
     */
    @GetMapping("complete/{id}")
    public AjaxResultGeneric<? extends Boolean> complete(@PathVariable int id, int role, String userId) {
        MessageUser messageUser = new MessageUser();
        messageUser.setRole(role);
        messageUser.setUid(userId);
        messageUser.setMid(id);
        if (messageUserService.count(new QueryWrapper<>(messageUser)) == 0) {
            messageUserService.save(messageUser);
        }
        return BaseResult.successGenericResult(true);
    }

    /**
     * 新增数据
     *
     * @param messageMeta 实体对象
     * @return 新增结果
     */
    @PostMapping
    @OperationLogMark("创建站内信")
    public AjaxResultGeneric<? extends Boolean> insert(@RequestBody MessageMeta messageMeta) {
        return BaseResult.successGenericResult(this.messageMetaService.save(messageMeta));
    }

    /**
     * 修改数据
     *
     * @param messageMeta 实体对象
     * @return 修改结果
     */
    @PutMapping
    @OperationLogMark("修改站内信")
    public AjaxResultGeneric<? extends Boolean> update(@RequestBody MessageMeta messageMeta) {
        return BaseResult.successGenericResult(this.messageMetaService.updateById(messageMeta));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public AjaxResultGeneric<? extends Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return BaseResult.successGenericResult(this.messageMetaService.removeByIds(idList));
    }
}