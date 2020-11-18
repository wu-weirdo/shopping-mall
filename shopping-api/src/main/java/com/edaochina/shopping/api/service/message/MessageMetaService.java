package com.edaochina.shopping.api.service.message;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.entity.message.MessageMeta;
import com.edaochina.shopping.domain.vo.message.MessageMetaVo;

import java.util.List;

/**
 * (MessageMeta)表服务接口
 *
 * @author wangpenglei
 * @since 2019-09-17 15:58:45
 */
public interface MessageMetaService extends IService<MessageMeta> {

    /**
     * 查询新消息
     *
     * @param role   角色
     * @param userId 用户id
     * @param stage  平台
     * @return 新消息
     */
    PageResult findNewMessage(int role, String userId, int stage, Pages pages);

    List<MessageMetaVo> findNewMessage(int role, String userId, int stage);

    PageResult findOldMessage(int role, String userId, int stage, Pages page);

    PageResult findAll(int role, String userId, int stage, Pages page);

    int findNewMessageCount(int role, String userId, int stage);
}