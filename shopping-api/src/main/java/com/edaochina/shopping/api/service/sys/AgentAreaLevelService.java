package com.edaochina.shopping.api.service.sys;


import java.util.List;

/**
 * <p>
 * 代理商区县等级表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-21
 */
public interface AgentAreaLevelService {


    List<String> hasAgentArea(String search);

    List<String> protectedAgentArea(String search);

    List<String> unAgentArea(String search);

    List<String> queryRecommend(String search);
}
