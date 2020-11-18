package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.service.sys.GoodRuleService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.entity.sys.GoodRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jintian
 * @date 2019/9/17 17:42
 */
@RestController
@RequestMapping("sys/goodRule")
public class SysGoodRuleController {

    @Autowired
    private GoodRuleService goodRuleService;

    @RequestMapping("updateGoodRule")
    public AjaxResult updateGoodRule(@RequestBody GoodRule goodRule) {
        return new AjaxResult(goodRuleService.updateGoodRule(goodRule));
    }

    @RequestMapping("getGoodRuleDetail")
    public AjaxResult getGoodRuleDetail(Integer type) {
        return new AjaxResult(goodRuleService.getGoodRuleDetail(type));
    }
}
