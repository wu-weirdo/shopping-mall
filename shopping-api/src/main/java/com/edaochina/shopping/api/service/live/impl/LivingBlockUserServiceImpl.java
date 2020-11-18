package com.edaochina.shopping.api.service.live.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.live.LivingBlockUserDao;
import com.edaochina.shopping.api.service.live.LivingBlockUserService;
import com.edaochina.shopping.domain.entity.live.LivingBlockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 直播拉黑用户列表(LivingBlockUser)表服务实现类
 *
 * @author wangpenglei
 * @since 2019-08-26 17:48:12
 */
@Service("livingBlockUserService")
@Transactional(rollbackFor = Exception.class)
public class LivingBlockUserServiceImpl extends ServiceImpl<LivingBlockUserDao, LivingBlockUser> implements LivingBlockUserService {

}