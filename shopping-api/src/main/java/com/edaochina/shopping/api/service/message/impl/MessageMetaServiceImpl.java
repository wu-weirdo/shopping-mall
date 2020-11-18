package com.edaochina.shopping.api.service.message.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.message.MessageMetaDao;
import com.edaochina.shopping.api.service.message.MessageMetaService;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.entity.message.MessageMeta;
import com.edaochina.shopping.domain.vo.message.MessageMetaVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (MessageMeta)表服务实现类
 *
 * @author wangpenglei
 * @since 2019-09-17 15:58:45
 */
@Service("messageMetaService")
public class MessageMetaServiceImpl extends ServiceImpl<MessageMetaDao, MessageMeta> implements MessageMetaService {

    @Override
    public PageResult findNewMessage(int role, String userId, int stage, Pages pages) {
        PageHelperUtils.setPageHelper(pages);
        return PageHelperUtils.parseToPageResult(this.baseMapper.findNewMessage(role, userId, stage));
    }

    @Override
    public List<MessageMetaVo> findNewMessage(int role, String userId, int stage) {
        return this.baseMapper.findNewMessage(role, userId, stage);
    }

    @Override
    public PageResult findOldMessage(int role, String userId, int stage, Pages page) {
        PageHelperUtils.setPageHelper(page);
        return PageHelperUtils.parseToPageResult(this.baseMapper.findOldMessage(role, userId, stage));
    }

    @Override
    public PageResult findAll(int role, String userId, int stage, Pages page) {
        PageHelperUtils.setPageHelper(page);
        return PageHelperUtils.parseToPageResult(this.baseMapper.finAll(role, userId, stage));
    }

    @Override
    public int findNewMessageCount(int role, String userId, int stage) {
        return this.baseMapper.findNewMessageCount(role, userId, stage);
    }
}