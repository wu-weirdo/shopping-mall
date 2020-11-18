package com.edaochina.shopping.web.message;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.service.message.MessageMetaService;
import com.edaochina.shopping.api.service.message.MessageUserService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.entity.message.MessageMeta;
import com.edaochina.shopping.domain.entity.message.MessageUser;
import com.edaochina.shopping.domain.vo.message.MessageMetaVo;
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
@RequestMapping("message/meta")
public class MessageMetaController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private MessageMetaService messageMetaService;

    private final MessageUserService messageUserService;

    public MessageMetaController(MessageUserService messageUserService) {
        this.messageUserService = messageUserService;
    }

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param messageMeta 查询实体
     * @return 所有数据
     */
    @GetMapping
    public AjaxResultGeneric<? extends IPage<MessageMeta>> selectAll(Page<MessageMeta> page, MessageMeta messageMeta) {
        JWTBean bean = JWTThreadLocalHelper.get();
        messageMeta.setRole(null);
        QueryWrapper<MessageMeta> queryWrapper = new QueryWrapper<>(messageMeta);
        queryWrapper.lambda().in(MessageMeta::getRole, 0, bean.getRole());
        return BaseResult.successGenericResult(this.messageMetaService.page(page, queryWrapper));
    }

    /**
     * 未读消息数量查询
     *
     * @param stage 平台
     * @return 未读消息数量
     */
    @GetMapping("unreadCount")
    public AjaxResultGeneric<Integer> unreadCount(int stage) {
        JWTBean bean = JWTThreadLocalHelper.get();
        return BaseResult.successGenericResult(this.messageMetaService.findNewMessageCount(Integer.parseInt(bean.getRole()), bean.getId(), stage));
    }

    /**
     * 未读消息数量查询
     *
     * @param stage 平台
     * @return 未读消息数量
     */
    @GetMapping("unread")
    public AjaxResultGeneric<List<MessageMetaVo>> unread(int stage) {
        JWTBean bean = JWTThreadLocalHelper.get();
        return BaseResult.successGenericResult(this.messageMetaService.findNewMessage(Integer.parseInt(bean.getRole()), bean.getId(), stage));
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

    @PutMapping("complete/{id}")
    public AjaxResultGeneric<? extends Boolean> complete(@PathVariable int id) {
        JWTBean bean = JWTThreadLocalHelper.get();
        MessageUser messageUser = new MessageUser();
        messageUser.setRole(Integer.valueOf(bean.getRole()));
        messageUser.setUid(bean.getId());
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