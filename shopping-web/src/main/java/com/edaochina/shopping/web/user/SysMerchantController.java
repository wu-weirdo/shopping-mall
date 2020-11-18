package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.user.SysUserFacade;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.annotation.PermissionsMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.ValidUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.user.MerchantDTO;
import com.edaochina.shopping.domain.dto.user.MerchantRegDTO;
import com.edaochina.shopping.domain.dto.user.UpdMerchantDTO;
import com.edaochina.shopping.domain.vo.user.PromotionInfoVO;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * SysMerchantController
 *
 * @author wangpenglei
 * @since 2019/11/5 11:46
 */
@RestController
@RequestMapping("/sys/merchant")
public class SysMerchantController {

    private final SysUserFacade userFacade;

    public SysMerchantController(SysUserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("promotion/info/{id}")
    public AjaxResultGeneric<PromotionInfoVO> promotionInfo(@PathVariable String id) {
        return userFacade.promotionInfo(id);
    }

    @PostMapping("league/{id}")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE})
    public AjaxResultGeneric<Boolean> league(@PathVariable String id) {
        return userFacade.league(id);
    }

    @PutMapping("league/quit/{id}")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE})
    public AjaxResultGeneric<Boolean> quitLeague(@PathVariable String id) {
        return userFacade.quitLeague(id);
    }


    /**
     * 商家注册
     *
     * @param dto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/register/merchant")
    @OperationLogMark("商家注册")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE, RoleConstants.COMMUNITY_PARTENER})
    public AjaxResult merchantRegister(@Valid @RequestBody MerchantRegDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        return userFacade.merchantRegister(dto);
    }


    /**
     * 商户列表
     *
     * @param merchantDTO
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/list/merchant")
    public AjaxResult merchantList(@Valid @RequestBody MerchantDTO merchantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        PageResult result = userFacade.merchantList(merchantDTO);
        return BaseResult.successResult(result);
    }


    /**
     * 商家编辑
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/update/merchant")
    @OperationLogMark("商家编辑")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE})
    public AjaxResult merchantUpdate(@RequestBody UpdMerchantDTO dto) {
        return userFacade.merchantUpdate(dto);
    }

    /**
     * 删除商家
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/delete/merchant")
    @OperationLogMark("删除商家")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE})
    public AjaxResult deleteMerchant(@RequestBody UpdMerchantDTO dto) {
        return userFacade.deleteMerchant(dto);
    }

}
