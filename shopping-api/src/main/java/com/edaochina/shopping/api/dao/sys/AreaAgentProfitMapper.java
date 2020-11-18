package com.edaochina.shopping.api.dao.sys;

import com.edaochina.shopping.domain.entity.sys.AreaAgentProfit;
import org.apache.ibatis.annotations.Param;

/**
 * @author jintian
 * @date 2019/4/2 14:15
 */
public interface AreaAgentProfitMapper {

    int insertAreaAgentProfit(AreaAgentProfit areaAgentProfit);

    AreaAgentProfit selectAreaAgentProfitByCountyId(@Param("countyId") String countyId, @Param("profitType") Integer profitType);

    int updateProfitRatioById(AreaAgentProfit areaAgentProfit);

    int updateProfitRatioByCountId(AreaAgentProfit areaAgentProfit);
}
