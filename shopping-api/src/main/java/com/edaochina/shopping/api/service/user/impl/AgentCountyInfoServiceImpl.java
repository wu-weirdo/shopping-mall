package com.edaochina.shopping.api.service.user.impl;

import com.edaochina.shopping.api.dao.user.AgentCountyInfoMapper;
import com.edaochina.shopping.api.dao.user.CommunityPartenerCountyInfoMapper;
import com.edaochina.shopping.api.service.user.AgentCountyInfoService;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.entity.user.AgentCountyInfo;
import com.edaochina.shopping.domain.entity.user.CountyInfo;
import com.edaochina.shopping.domain.vo.sys.SysBackCommunityAreaInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AgentCountyInfoServiceImpl implements AgentCountyInfoService {

    @Resource
    AgentCountyInfoMapper agentCountyInfoMapper;

    @Resource
    CommunityPartenerCountyInfoMapper communityPartenerCountyInfoMapper;

    @Override
    public List<CountyInfo> agentCountyList(String agentId) {
        return agentCountyInfoMapper.agentCountyList(agentId);
    }

    @Override
    public int insertAgentCounty(AgentCountyInfo agentCountyInfo) {
        return agentCountyInfoMapper.insertAgentCounty(agentCountyInfo);
    }

    @Override
    public List<String> agentCountyStrList(String agentId) {
        List<CountyInfo> agentCountyInfos = agentCountyInfoMapper.agentCountyList(agentId);
        List<String> countyStrs = new ArrayList<>();
        agentCountyInfos.forEach(agentCountyInfo -> {
            countyStrs.add(agentCountyInfo.getCountyCode());
        });
        return countyStrs;
    }

    @Override
    public List<String> agentProvinceStrList(String agentId) {
        List<CountyInfo> agentCountyInfos = agentCountyInfoMapper.agentCountyList(agentId);
        List<String> countyStrs = new ArrayList<>();
        agentCountyInfos.forEach(agentCountyInfo -> {
            countyStrs.add(agentCountyInfo.getProvinceCode());
        });
        return countyStrs;
    }

    @Override
    public List<String> agentCityStrList(String agentId) {
        List<CountyInfo> agentCountyInfos = agentCountyInfoMapper.agentCountyList(agentId);
        List<String> countyStrs = new ArrayList<>();
        agentCountyInfos.forEach(agentCountyInfo -> {
            countyStrs.add(agentCountyInfo.getCityCode());
        });
        return countyStrs;
    }

    @Override
    public boolean checkAgentByCountyId(List<AgentCountyInfo> agentAreaInfos, String agentId) {
        return agentCountyInfoMapper.checkAgentByCountyId(agentAreaInfos, agentId) > 0;
    }

    @Override
    public int removeByAgentId(String agentId) {
        return agentCountyInfoMapper.deleteAgentCounty(agentId);
    }

    @Override
    public Collection<SysBackCommunityAreaInfo> provinceCityList() {
        List<CountyInfo> agentCountyInfos = agentCountyInfoMapper.queryProvinceAndCity();
        return getAreaInfoByAgentCountyInfos(agentCountyInfos, 2);
    }

    private Collection<SysBackCommunityAreaInfo> getAreaInfoByAgentCountyInfos(List<CountyInfo> agentCountyInfos, int level) {
        Map<String, SysBackCommunityAreaInfo> sysBackCommunityAreaInfoMap = new HashMap<>();
        Map<String, SysBackCommunityAreaInfo> provinceMap = new HashMap<>();
        agentCountyInfos.forEach(agentCountyInfo -> {
            SysBackCommunityAreaInfo province = null;
            SysBackCommunityAreaInfo city = null;
            SysBackCommunityAreaInfo county = null;
            if (!sysBackCommunityAreaInfoMap.containsKey(agentCountyInfo.getProvinceCode())) {
                province = new SysBackCommunityAreaInfo(agentCountyInfo.getProvinceCode()
                        , agentCountyInfo.getProvinceName()
                        , new ArrayList<>());
            } else {
                province = sysBackCommunityAreaInfoMap.get(agentCountyInfo.getProvinceCode());
            }
            if (!sysBackCommunityAreaInfoMap.containsKey(agentCountyInfo.getCityCode())) {
                if (level > 2) {
                    city = new SysBackCommunityAreaInfo(agentCountyInfo.getCityCode()
                            , agentCountyInfo.getCityName()
                            , new ArrayList<>());
                } else {
                    city = new SysBackCommunityAreaInfo(agentCountyInfo.getCityCode()
                            , agentCountyInfo.getCityName()
                            , null);
                }
                List<SysBackCommunityAreaInfo> cityChildren = province.getChildren();
                cityChildren.add(city);
                province.setChildren(cityChildren);
            } else {
                city = sysBackCommunityAreaInfoMap.get(agentCountyInfo.getCityCode());
            }
            if (level > 2) {
                if (!sysBackCommunityAreaInfoMap.containsKey(agentCountyInfo.getCountyCode())) {
                    county = new SysBackCommunityAreaInfo(agentCountyInfo.getCountyCode()
                            , agentCountyInfo.getCountyName()
                            , null);
                    List<SysBackCommunityAreaInfo> countyChildren = city.getChildren();
                    countyChildren.add(county);
                    city.setChildren(countyChildren);
                }
                sysBackCommunityAreaInfoMap.put(agentCountyInfo.getCountyCode(), county);
            }
            sysBackCommunityAreaInfoMap.put(agentCountyInfo.getProvinceCode(), province);
            sysBackCommunityAreaInfoMap.put(agentCountyInfo.getCityCode(), city);
            provinceMap.put(agentCountyInfo.getProvinceCode(), province);
        });
        return provinceMap.values();
    }

    @Override
    public List<AgentCountyInfo> queryCountyByCity(String cityCode) {
        return agentCountyInfoMapper.queryCountyByCity(cityCode);
    }

    @Override
    public Collection<SysBackCommunityAreaInfo> provinceCityListByAgentId(String agentId) {
        JWTBean jwtBean = JWTThreadLocalHelper.get();
        List<CountyInfo> agentCountyInfos;
        if (RoleConstants.AGENT_ROLE_STRING.equals(jwtBean.getRole())) {
            agentCountyInfos = agentCountyInfoMapper.agentCountyList(agentId);
        } else {
            agentCountyInfos = communityPartenerCountyInfoMapper.partenerCountyList(agentId);
        }
        return getAreaInfoByAgentCountyInfos(agentCountyInfos, 3);
    }

    @Override
    public AgentCountyInfo queryByCountyId(String countyCode) {
        return agentCountyInfoMapper.queryByCountyId(countyCode);
    }

    @Override
    public int removeErrorCountyInfo(List<AgentCountyInfo> agentAreaInfos, String agentId) {
        return agentCountyInfoMapper.removeErrorCountyInfo(agentAreaInfos, agentId);
    }
}
