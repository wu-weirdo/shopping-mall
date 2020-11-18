package com.edaochina.shopping.api.dao.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.message.MessageMeta;
import com.edaochina.shopping.domain.vo.message.MessageMetaVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (MessageMeta)表数据库访问层
 *
 * @author wangpenglei
 * @since 2019-09-17 15:58:45
 */
@Mapper
@Repository
public interface MessageMetaDao extends BaseMapper<MessageMeta> {

    /**
     * 查询新消息
     *
     * @param role   角色
     * @param userId 用户id
     * @param stage  平台
     * @return 新消息
     */
    @Select("SELECT *, 0 as complete FROM message_meta WHERE " +
            " push_time <= now( ) AND end_time >= now( ) AND role in (0, #{role}) AND state = 0 AND stage = #{stage} " +
            " AND id NOT IN (SELECT mid FROM message_user WHERE role in (0, #{role}) AND uid = #{userId})")
    List<MessageMetaVo> findNewMessage(@Param("role") int role, @Param("userId") String userId, @Param("stage") int stage);

    /**
     * 查询已读消息
     *
     * @param role   角色
     * @param userId 用户id
     * @param stage  平台
     * @return 已读消息
     */
    @Select("SELECT *, 1 as complete FROM message_meta WHERE " +
            " push_time <= now( ) AND role in (0, #{role}) AND state = 0 AND stage = #{stage} " +
            " AND id IN (SELECT mid FROM message_user WHERE role in (0, #{role}) AND uid = #{userId})")
    List<MessageMetaVo> findOldMessage(@Param("role") int role, @Param("userId") String userId, @Param("stage") int stage);

    /**
     * 查询所有消息
     *
     * @param role   角色
     * @param userId 用户id
     * @param stage  平台
     * @return 消息
     */
    @Select("SELECT m.*, CASE WHEN u.id IS NULL THEN 0 ELSE 1 END AS complete " +
            "FROM message_meta m LEFT JOIN message_user u ON u.mid = m.id AND u.uid = #{userId} " +
            "WHERE " +
            "m.push_time <= now( ) AND m.role IN ( 0, #{role} ) " +
            "AND m.state = 0 " +
            "AND m.stage = #{stage}")
    List<MessageMetaVo> finAll(@Param("role") int role, @Param("userId") String userId, @Param("stage") int stage);

    /**
     * 查询新消息数量
     *
     * @param role   角色
     * @param userId 用户id
     * @param stage  平台
     * @return 新消息
     */
    @Select("SELECT count(1) FROM message_meta WHERE " +
            " push_time <= now( ) AND end_time >= now( ) AND role in (0, #{role}) AND state = 0 AND stage = #{stage} " +
            " AND id NOT IN (SELECT mid FROM message_user WHERE role in (0, #{role}) AND uid = #{userId})")
    int findNewMessageCount(@Param("role") int role, @Param("userId") String userId, @Param("stage") int stage);

}