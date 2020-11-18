package com.edaochina.shopping.api.dao.sys;


import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代理商区县等级表 Mapper 接口
 * </p>
 *
 * @since 2019-05-21
 */
public interface AgentAreaLevelMapper {

    List<String> hasAgentArea(@Param("search") String search);

    List<String> protectedAgentArea(@Param("search") String search);

    List<String> unAgentArea(@Param("search") String search);

    List<String> queryRecommend(@Param("search") String search);
}
