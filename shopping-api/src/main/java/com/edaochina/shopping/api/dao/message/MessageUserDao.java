package com.edaochina.shopping.api.dao.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.message.MessageUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (MessageUser)表数据库访问层
 *
 * @author wangpenglei
 * @since 2019-09-17 15:59:52
 */
@Mapper
@Repository
public interface MessageUserDao extends BaseMapper<MessageUser> {

}