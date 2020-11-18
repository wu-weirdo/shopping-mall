package com.edaochina.shopping.api.service.user;

import com.edaochina.shopping.domain.entity.user.AgentCountyInfo;
import com.edaochina.shopping.domain.entity.user.CountyInfo;
import com.edaochina.shopping.domain.vo.sys.SysBackCommunityAreaInfo;

import java.util.Collection;
import java.util.List;

public interface AgentCountyInfoService {

    List<CountyInfo> agentCountyList(String agentId);

    int insertAgentCounty(AgentCountyInfo agentCountyInfo);

    List<String> agentCountyStrList(String agentId);

    List<String> agentProvinceStrList(String agentId);

    List<String> agentCityStrList(String agentId);

    boolean checkAgentByCountyId(List<AgentCountyInfo> agentAreaInfos, String s);

    int removeByAgentId(String id);

    Collection<SysBackCommunityAreaInfo> provinceCityList();

    List<AgentCountyInfo> queryCountyByCity(String cityCode);

    Collection<SysBackCommunityAreaInfo> provinceCityListByAgentId(String agentId);

    AgentCountyInfo queryByCountyId(String countyCode);

    int removeErrorCountyInfo(List<AgentCountyInfo> agentAreaInfos, String agentId);
}
