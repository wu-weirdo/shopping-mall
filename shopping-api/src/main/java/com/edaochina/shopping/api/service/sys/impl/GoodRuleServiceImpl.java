package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.GoodRuleMapper;
import com.edaochina.shopping.api.service.sys.GoodRuleService;
import com.edaochina.shopping.domain.entity.sys.GoodRule;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品规则服务类
 *
 * @author jintian
 * @date 2019/9/17 17:44
 */
@Service
public class GoodRuleServiceImpl implements GoodRuleService {

    @Resource
    private GoodRuleMapper goodRuleMapper;


    @Override
    public int updateGoodRule(GoodRule goodRule) {
        return goodRuleMapper.updateByType(goodRule);
    }

    @Override
    public GoodRule getGoodRuleDetail(Integer type) {
        return goodRuleMapper.queryByType(type);
    }
}
