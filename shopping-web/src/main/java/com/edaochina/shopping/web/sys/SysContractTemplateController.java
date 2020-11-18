package com.edaochina.shopping.web.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.sys.SysContractTemplateMapper;
import com.edaochina.shopping.api.service.sys.SysContractTemplateService;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.entity.sys.SysContractTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 合同模板表 前端控制器
 * </p>
 *
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/sys/contractTemplate")
public class SysContractTemplateController {

    private final SysContractTemplateMapper sysContractTemplateMapper;

    private final SysContractTemplateService sysContractTemplateService;

    public SysContractTemplateController(SysContractTemplateMapper sysContractTemplateMapper, SysContractTemplateService sysContractTemplateService) {
        this.sysContractTemplateMapper = sysContractTemplateMapper;
        this.sysContractTemplateService = sysContractTemplateService;
    }

    @PutMapping
    public AjaxResult update(@RequestBody SysContractTemplate sysContractTemplate) {
        JWTBean jwtBean = JWTThreadLocalHelper.get();
        if (StringUtils.isEmpty(sysContractTemplate.getCreateUserId())) {
            sysContractTemplate.setCreateUserId(jwtBean.getId());
        }
        sysContractTemplate.setUpdateUserId(jwtBean.getId());
        int count;
        if (sysContractTemplate.getId() == null) {
            count = sysContractTemplateMapper.insert(sysContractTemplate);
        } else {
            count = sysContractTemplateMapper.updateById(sysContractTemplate);
            if (sysContractTemplate.getId() == 2) {
                // TODO 异步生成合同
                sysContractTemplateService.createMerchantContract();
            }
        }
        return BaseResult.successResult(count);
    }

    @GetMapping
    public AjaxResult list() {
        return BaseResult.successResult(sysContractTemplateMapper.selectList(new QueryWrapper<>()));
    }

    @GetMapping("shop")
    public AjaxResult shop() {
        return BaseResult.successResult(sysContractTemplateService.queryByUserType("3"));
    }

    @GetMapping("user")
    public AjaxResult user() {
        return BaseResult.successResult(sysContractTemplateService.queryByUserType("5"));
    }

}

