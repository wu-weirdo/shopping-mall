package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.law.FadadaLawFacade;
import com.edaochina.shopping.domain.dto.law.FadadaReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jintian
 * @date 2019/7/12 14:08
 */
@Controller
@RequestMapping("app/law")
public class AppLawReqController {

    @Autowired
    FadadaLawFacade fadadaLawFacade;

    /*@RequestMapping("registerMerchant")
    public String regin(String userId) {
        return fadadaLawFacade.registerAccount(userId, RoleConstants.MERCHANT_ROLE_STRING);
    }*/

    @ResponseBody
    @RequestMapping("merchantSignContrat")
    public String merchantSignContrat(String merchantId, String returnUrl) {
        return fadadaLawFacade.merchantSignContrat(merchantId, returnUrl);
    }

    @ResponseBody
    @RequestMapping("acceptReturn")
    public String handlerReturn(FadadaReturnInfo returnInfo) {
        fadadaLawFacade.acceptSignReturn(returnInfo);
        return "SUCCESS";
    }
}
