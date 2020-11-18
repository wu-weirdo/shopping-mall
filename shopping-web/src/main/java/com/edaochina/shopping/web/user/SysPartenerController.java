package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.service.user.SysCommunityPartnerService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.user.CheckInvitatCodeDTO;
import com.edaochina.shopping.domain.entity.user.SysCommunityPartner;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统群社合伙人处理
 *
 * @author jintian
 * @date 2019/7/26 13:55
 */
@RestController
@RequestMapping("sys/partener")
@Api(tags = "群社合伙人")
public class SysPartenerController {
    @Resource
    SysCommunityPartnerService sysCommunityPartnerService;

    @RequestMapping(value = "/checkInvitatCode", method = RequestMethod.POST)
    @OperationLogMark("小程序群社合伙人登陆")
    public AjaxResult checkInvitatCode(@RequestBody CheckInvitatCodeDTO dto) {
        SysCommunityPartner communityPartner = sysCommunityPartnerService.checkInvitatCode(dto);
        return BaseResult.successResult(communityPartner.getId());
    }

}
