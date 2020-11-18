package com.edaochina.shopping.api.service.message.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.message.MessageUserDao;
import com.edaochina.shopping.api.service.message.MessageUserService;
import com.edaochina.shopping.domain.entity.message.MessageUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (MessageUser)表服务实现类
 *
 * @author wangpenglei
 * @since 2019-09-17 15:59:52
 */
@Service("messageUserService")
@Transactional(rollbackFor = Exception.class)
public class MessageUserServiceImpl extends ServiceImpl<MessageUserDao, MessageUser> implements MessageUserService {

}