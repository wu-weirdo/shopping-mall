package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.AgentAreaLevelMapper;
import com.edaochina.shopping.api.service.sys.AgentAreaLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 代理商区县等级表 服务实现类
 * </p>
 *
 * @since 2019-05-21
 */
@Service
public class AgentAreaLevelServiceImpl implements AgentAreaLevelService {

    @Autowired
    private AgentAreaLevelMapper agentAreaLevelMapper;

    @Override
    public List<String> hasAgentArea(String search) {
        return agentAreaLevelMapper.hasAgentArea(search);
    }

    @Override
    public List<String> protectedAgentArea(String search) {
        return agentAreaLevelMapper.protectedAgentArea(search);
    }

    @Override
    public List<String> unAgentArea(String search) {
        return agentAreaLevelMapper.unAgentArea(search);
    }

    @Override
    public List<String> queryRecommend(String search) {
        return agentAreaLevelMapper.queryRecommend(search);
    }
}
