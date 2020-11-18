package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.service.sys.BankCodeService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jintian
 * @date 2019/7/29 16:30
 */
@RestController
@RequestMapping("app/bank")
public class AppBankController {

    @Autowired
    private BankCodeService bankCodeService;

    @RequestMapping("queryBankCodes")
    public AjaxResult queryBankCodes() {
        return BaseResult.successResult(bankCodeService.queryBankCodes());
    }
}
