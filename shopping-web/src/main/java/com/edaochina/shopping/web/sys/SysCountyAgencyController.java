package com.edaochina.shopping.web.sys;


import com.edaochina.shopping.api.service.sys.SysCountyAgencyService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.SysCountyAgencyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 区县代理查询表 前端控制器
 * </p>
 *
 * @since 2019-05-20
 */
@RestController
@RequestMapping("/sys/countyAgency")
public class SysCountyAgencyController {

    @Autowired
    private SysCountyAgencyService sysCountyAgencyService;

    @RequestMapping("query")
    public AjaxResult query(@RequestBody SysCountyAgencyDto search) {
        return BaseResult.successResult(sysCountyAgencyService.query(search));
    }

}

