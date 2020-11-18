package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.user.AgentCountyInfo;
import com.edaochina.shopping.domain.entity.user.CountyInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentCountyInfoMapper extends BaseMapper<AgentCountyInfo> {

    List<CountyInfo> agentCountyList(String agentId);

    int deleteAgentCounty(String agentId);

    int insertAgentCounty(AgentCountyInfo agentCountyInfo);

    int checkAgentByCountyId(@Param("agentAreaInfos") List<AgentCountyInfo> agentAreaInfos, @Param("agentId") String agentId);

    List<CountyInfo> queryProvinceAndCity();

    List<AgentCountyInfo> queryCountyByCity(String cityCode);

    AgentCountyInfo queryByCountyId(@Param("countyCode") String countyCode);

    int removeErrorCountyInfo(@Param("agentAreaInfos") List<AgentCountyInfo> agentAreaInfos, @Param("agentId") String agentId);
}
