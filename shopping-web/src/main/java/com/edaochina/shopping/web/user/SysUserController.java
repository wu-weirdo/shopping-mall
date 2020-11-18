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
import com.edaochina.shopping.domain.dto.user.*;
import com.edaochina.shopping.domain.vo.user.AgentVO;
import com.edaochina.shopping.domain.vo.user.LoginVO;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 商户表 by 张志侃 前端控制器
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    SysUserFacade sysUserFacade;

    /**
     * 系统用户登陆
     *
     * @param dto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/login")
    @OperationLogMark("系统用户登陆")
    public AjaxResult agentLogin(@Valid @RequestBody LoginDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        dto.setAccount(dto.getAccount().trim());
        dto.setPassword(dto.getPassword().trim());
        LoginVO loginVO = sysUserFacade.sysLogin(dto);
        return BaseResult.successResult(loginVO);
    }

    /**
     * 代理商注册
     *
     * @param dto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/register/agent")
    @OperationLogMark("代理商注册")
    public AjaxResult agentRegister(@Valid @RequestBody AgentRegDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        dto.setAccount(dto.getAccount().trim());
        dto.setPassword(dto.getPassword().trim());
        return sysUserFacade.agentRegister(dto);
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
        return sysUserFacade.merchantRegister(dto);
    }

    /**
     * 管理员注册
     *
     * @param dto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/register/admin")
    @OperationLogMark("管理员注册")
    public AjaxResult adminRegister(@Valid @RequestBody AdminDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        dto.setAccount(dto.getAccount().trim());
        dto.setPassword(dto.getPassword().trim());
        return sysUserFacade.adminRegister(dto);
    }


    @PostMapping(value = "/list")
    public AjaxResult userList(@RequestBody UserDTO dto) {
        return sysUserFacade.userList(dto);
    }

    /**
     * 代理商列表
     *
     * @param agentDTO
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/list/agent")
    public AjaxResult agentList(@Valid @RequestBody AgentDTO agentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        PageResult<AgentVO> result = sysUserFacade.agentList(agentDTO);
        return BaseResult.successResult(result);
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
        PageResult result = sysUserFacade.merchantList(merchantDTO);
        return BaseResult.successResult(result);
    }

    /**
     * 管理员列表
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/list/admin")
    public AjaxResult adminList(@RequestBody AdminDTO dto) {
        PageResult pageResult = sysUserFacade.adminList(dto);
        return BaseResult.successResult(pageResult);
    }

    /**
     * 代理商编辑
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/update/agent")
    @OperationLogMark("代理商编辑")
    public AjaxResult agentUpdate(@RequestBody UpdAgentDTO dto) {
        return sysUserFacade.agentUpdate(dto);
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
        return sysUserFacade.merchantUpdate(dto);
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
        return sysUserFacade.deleteMerchant(dto);
    }

    /**
     * 管理员编辑
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/update/admin")
    @OperationLogMark("管理员编辑")
    public AjaxResult adminUpdate(@RequestBody AdminDTO dto) {
        return sysUserFacade.adminUpdate(dto);
    }

    /**
     * 代理商详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/detail/agent")
    public AjaxResult agentDetail(String id) {
        return sysUserFacade.agentDetail(id);
    }

    /**
     * 商户详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/detail/merchant")
    public AjaxResult merchantDetail(String id) {
        return sysUserFacade.merchantDetail(id);
    }


    /**
     * 管理员详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/detail/admin")
    public AjaxResult adminDetail(String id) {
        return sysUserFacade.adminDetail(id);
    }


    /**
     * 用户详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/user")
    public AjaxResult userDetail(String id) {
        return sysUserFacade.userDetail(id);
    }


    /**
     * 禁用账号
     *
     * @param dto
     * @return2
     */
    @PostMapping(value = "/account/disable")
    public AjaxResult disableAccount(@RequestBody UserDTO dto) {
        return sysUserFacade.disableAccount(dto);
    }

    /**
     * 启用账号
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/account/enable")
    public AjaxResult enableAccount(@RequestBody UserDTO dto) {
        return sysUserFacade.enableAccount(dto);
    }

    /**
     * 删除账号
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/account/delete")
    @OperationLogMark("删除账号")
    public AjaxResult deleteAccount(@RequestBody UserDTO dto) {
        return sysUserFacade.deleteAccount(dto);
    }

    /**
     * 编辑账号
     *
     * @param sysUserUpdateDTO
     * @return
     */
    @PostMapping(value = "/account/edit")
    @OperationLogMark("编辑账号")
    public AjaxResult editAccount(@RequestBody SysUserUpdateDTO sysUserUpdateDTO) {
        return sysUserFacade.editAccount(sysUserUpdateDTO);
    }

    /**
     * 用户导出excel
     *
     * @param dto 查询参数
     * @return excel下载链接
     */
    @PostMapping("/export")
    public AjaxResult export(@RequestBody UserDTO dto) {
        return BaseResult.successResult(sysUserFacade.export(dto));
    }

    /**
     * 商户导出excel
     *
     * @param merchantDTO   查询参数
     * @param bindingResult 参数绑定结果
     * @return excel下载链接
     */
    @PostMapping(value = "/merchant/export")
    public AjaxResult merchantExport(@Valid @RequestBody MerchantDTO merchantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        return BaseResult.successResult(sysUserFacade.merchantExport(merchantDTO));
    }

    @RequestMapping("register/communtyPartner")
    @OperationLogMark("注册社群合伙人")
    public AjaxResult communtyPartnerRegister(@RequestBody CommuntyPartnerRegisterDTO dto) {
        return BaseResult.successResult(sysUserFacade.communtyPartnerRegister(dto));
    }

    @RequestMapping("list/communtyPartner")
    public AjaxResult communtyPartnerList(@RequestBody CommuntyPartnerDTO dto) {
        return BaseResult.successResult(sysUserFacade.communtyPartnerList(dto));
    }

    @RequestMapping("communtyPartner/detail")
    public AjaxResult communtyPartnerDetail(String id) {
        return BaseResult.successResult(sysUserFacade.communtyPartnerDetail(id));
    }

    @RequestMapping("communtyPartner/update")
    public AjaxResult communtyPartnerUpdate(@RequestBody CommuntyPartnerRegisterDTO dto) {
        return BaseResult.successResult(sysUserFacade.communtyPartnerUpdate(dto));
    }

    @GetMapping("merchant/county/count")
    public AjaxResultGeneric<Integer> merchantCountByCounty(String countyId) {
        return sysUserFacade.merchantCountByCounty(countyId);
    }

    /**
     * 查询商家的顾客
     *
     * @return 顾客列表
     */
    @PostMapping("merchant/customer")
    public AjaxResult merchantCustomer(@RequestBody SysMerchantCustomerDTO dto) {
        return BaseResult.successResult(sysUserFacade.merchantCustomer(dto));
    }
}