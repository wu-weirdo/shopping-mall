package com.edaochina.shopping.api.service.live.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.live.LivingCommentDao;
import com.edaochina.shopping.api.service.live.LivingBlockUserService;
import com.edaochina.shopping.api.service.live.LivingCommentService;
import com.edaochina.shopping.domain.entity.live.LivingBlockUser;
import com.edaochina.shopping.domain.entity.live.LivingComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 直播评论(LivingComment)表服务实现类
 *
 * @author wangpenglei
 * @since 2019-08-26 17:47:57
 */
@Service("livingCommentService")
@Transactional(rollbackFor = Exception.class)
public class LivingCommentServiceImpl extends ServiceImpl<LivingCommentDao, LivingComment> implements LivingCommentService {

    private final LivingBlockUserService livingBlockUserService;

    public LivingCommentServiceImpl(LivingBlockUserService livingBlockUserService) {
        this.livingBlockUserService = livingBlockUserService;
    }

    @Override
    public boolean saveAndCheck(LivingComment livingComment) {
        QueryWrapper<LivingBlockUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(LivingBlockUser::getDelFlag, 0)
                .eq(LivingBlockUser::getBlockUserId, livingComment.getUserId())
                .eq(LivingBlockUser::getUserType, livingComment.getUserType())
                .eq(LivingBlockUser::getLivingId, livingComment.getLivingId());
        if (livingBlockUserService.count(queryWrapper) > 0) {
            livingComment.setDelFlag(1);
        }
        return this.save(livingComment);
    }

}