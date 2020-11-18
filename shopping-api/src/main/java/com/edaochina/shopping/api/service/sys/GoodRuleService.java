package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.sys.GoodRule;

/**
 * @author jintian
 * @date 2019/9/17 17:43
 */
public interface GoodRuleService {
    int updateGoodRule(GoodRule goodRule);

    GoodRule getGoodRuleDetail(Integer type);
}
