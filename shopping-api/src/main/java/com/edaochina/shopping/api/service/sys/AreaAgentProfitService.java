package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.sys.AreaAgentProfit;

public interface AreaAgentProfitService {
    /**
     * 添加
     *
     * @param areaAgentProfit
     * @return
     */
    int insertAreaAgentProfit(AreaAgentProfit areaAgentProfit);

    /**
     * 根据county_id查询
     *
     * @param countyId
     * @return
     */
    AreaAgentProfit selectAreaAgentProfitByCountyId(String countyId, Integer profitType);

    /**
     * 通过id修改profit_ratio
     *
     * @param areaAgentProfit
     * @return
     */
    int updateProfitRatioById(AreaAgentProfit areaAgentProfit);

    /**
     * 通过county_id修改profit_ratio
     *
     * @param areaAgentProfit
     * @return
     */
    int updateProfitRatioByCountId(AreaAgentProfit areaAgentProfit);
}
