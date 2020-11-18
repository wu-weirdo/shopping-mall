package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.AreaAgentProfitMapper;
import com.edaochina.shopping.api.service.sys.AreaAgentProfitService;
import com.edaochina.shopping.domain.entity.sys.AreaAgentProfit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AreaAgentProfitServiceImpl implements AreaAgentProfitService {

    @Resource
    AreaAgentProfitMapper areaAgentProfitMapper;

    @Override
    public int insertAreaAgentProfit(AreaAgentProfit areaAgentProfit) {
        return areaAgentProfitMapper.insertAreaAgentProfit(areaAgentProfit);
    }

    @Override
    public AreaAgentProfit selectAreaAgentProfitByCountyId(String countyId, Integer profitType) {
        AreaAgentProfit areaAgentProfits = areaAgentProfitMapper.selectAreaAgentProfitByCountyId(countyId, profitType);
        return areaAgentProfits;
    }

    @Override
    public int updateProfitRatioById(AreaAgentProfit areaAgentProfit) {
        return areaAgentProfitMapper.updateProfitRatioById(areaAgentProfit);
    }

    @Override
    public int updateProfitRatioByCountId(AreaAgentProfit areaAgentProfit) {
        return areaAgentProfitMapper.updateProfitRatioByCountId(areaAgentProfit);
    }
}
