package com.edaochina.shopping.api.service.live;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.live.LivingComment;

/**
 * 直播评论(LivingComment)表服务接口
 *
 * @author wangpenglei
 * @since 2019-08-26 17:47:56
 */
public interface LivingCommentService extends IService<LivingComment> {

    /**
     * 新建评论并检查黑名单
     *
     * @param livingComment 评论
     * @return 是否成功
     */
    boolean saveAndCheck(LivingComment livingComment);
}