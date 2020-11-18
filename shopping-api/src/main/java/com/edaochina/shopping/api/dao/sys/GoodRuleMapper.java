package com.edaochina.shopping.api.dao.sys;

import com.edaochina.shopping.domain.entity.sys.GoodRule;

/**
 * @author jintian
 * @date 2019/9/17 17:34
 */
public interface GoodRuleMapper {

    int updateByType(GoodRule goodRule);

    GoodRule queryByType(Integer type);
}
