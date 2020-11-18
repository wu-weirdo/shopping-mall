package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.service.sys.SysDictionaryParamService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys/sysDictionaryParam")
public class SysDictionaryParamController {

    @Resource
    SysDictionaryParamService sysDictionaryParamService;

    @RequestMapping("querySysValueByTypeAndKey")
    public AjaxResult querySysValueByTypeAndKey(@RequestBody SysDictionaryParam sysDictionaryParam) {
        return BaseResult.successResult(sysDictionaryParamService.querySysValueByTypeAndKey(sysDictionaryParam.getSysType(), sysDictionaryParam.getSysKey()));
    }

    @RequestMapping("updateSysValueByTypeAndKey")
    @OperationLogMark("修改字典")
    public AjaxResult updateSysValueByTypeAndKey(@RequestBody SysDictionaryParam sysDictionaryParam) {
        return BaseResult.successResult(sysDictionaryParamService.updateSysValueByTypeAndKey(sysDictionaryParam.getSysType(), sysDictionaryParam.getSysKey(), sysDictionaryParam.getSysValue()));
    }

    @PostMapping("checkValue")
    public AjaxResult checkValue(@RequestBody SysDictionaryParam sysDictionaryParam) {
        return BaseResult.successResult(sysDictionaryParamService.checkValue(sysDictionaryParam.getSysType(), sysDictionaryParam.getSysKey(), sysDictionaryParam.getSysValue()));
    }
}
