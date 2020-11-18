package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.SysCountyAgencyMapper;
import com.edaochina.shopping.api.service.sys.AgentAreaLevelService;
import com.edaochina.shopping.api.service.sys.SysCountyAgencyService;
import com.edaochina.shopping.domain.dto.sys.SysCountyAgencyDto;
import com.edaochina.shopping.domain.entity.sys.SysCountyAgency;
import com.edaochina.shopping.domain.vo.sys.SysAgentAreaVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 区县代理查询表 服务实现类
 * </p>
 *
 * @since 2019-05-20
 */
@Service
public class SysCountyAgencyServiceImpl implements SysCountyAgencyService {

    @Autowired
    private AgentAreaLevelService agentAreaLevelService;

    @Autowired
    private SysCountyAgencyMapper sysCountyAgencyMapper;

    @Override
    public SysAgentAreaVo query(SysCountyAgencyDto search) {
        SysAgentAreaVo sysAgentAreaVo = new SysAgentAreaVo();
        if (StringUtils.isNotBlank(search.getSearch())) {
            List<String> hasAgentArea = agentAreaLevelService.hasAgentArea(search.getSearch());
            List<String> protectedAgentArea = agentAreaLevelService.protectedAgentArea(search.getSearch());
            List<String> unAgentArea = agentAreaLevelService.unAgentArea(search.getSearch());
            if (unAgentArea.size() == 0) {
                List<String> recommend = agentAreaLevelService.queryRecommend(search.getSearch());
                sysAgentAreaVo.setRecommend(recommend);
            }

            sysAgentAreaVo.setAgentedName(hasAgentArea);
            sysAgentAreaVo.setProtectedName(protectedAgentArea);
            sysAgentAreaVo.setUnAgentAreaName(unAgentArea);
            if (hasAgentArea.size() > 0 || unAgentArea.size() > 0 || protectedAgentArea.size() > 0) {
                sysAgentAreaVo.setSearchStatus(true);
                sysCountyAgencyMapper.save(search);
            }
        }
        List<String> searchHistory = new ArrayList<>();
        List<SysCountyAgency> sysCountyAgencies = sysCountyAgencyMapper.queryHistory();
        sysCountyAgencies.forEach(sysCountyAgency -> {
            searchHistory.add(sysCountyAgency.getSearch());
        });
        Collections.reverse(searchHistory);
        sysAgentAreaVo.setSearchHistory(searchHistory);
        return sysAgentAreaVo;
    }
}
