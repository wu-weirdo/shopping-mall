package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import com.edaochina.shopping.domain.dto.user.MemberDTO;
import com.edaochina.shopping.domain.dto.user.MerchantDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/merchant")
@Api(tags = "商家")
public class AppMerchantController {

    @Resource
    SysUserFacade sysUserFacade;

    /**
     * 小程序商家登陆
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @OperationLogMark("小程序商家登陆")
    public AjaxResult login(@RequestBody LoginDTO dto) {
        dto.setAccount(dto.getAccount().trim());
        dto.setPassword(dto.getPassword().trim());
        return sysUserFacade.merchantLogin(dto);
    }

    /**
     * 小程序获取商家详情
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public AjaxResult info(@RequestBody MerchantDTO dto) {
        return sysUserFacade.getMerchantInfo(dto);
    }


    @RequestMapping(value = "/addressInfo")
    public AjaxResult addressInfo(String merchantId) {
        return null;
    }

    /**
     * 小程序获取商家详情
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public AjaxResult detail(@RequestBody MemberDTO dto) {
        return sysUserFacade.getMerchantDetail(dto);
    }
}
