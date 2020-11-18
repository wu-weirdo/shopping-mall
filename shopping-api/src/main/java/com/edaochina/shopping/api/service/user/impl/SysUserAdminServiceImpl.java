package com.edaochina.shopping.api.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.SysUserAdminMapper;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.api.service.user.SysUserAdminService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.CodeUtil;
import com.edaochina.shopping.common.utils.SignUtils;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.user.AdminDTO;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import com.edaochina.shopping.domain.entity.user.Account;
import com.edaochina.shopping.domain.entity.user.SysUserAdmin;
import com.edaochina.shopping.domain.vo.user.AdminVO;
import com.edaochina.shopping.domain.vo.user.SysUserVO;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-10
 */
@Service
public class SysUserAdminServiceImpl extends ServiceImpl<SysUserAdminMapper, SysUserAdmin> implements SysUserAdminService {

    @Resource
    SysUserAdminMapper sysUserAdminMapper;

    private final AccountService accountService;

    public SysUserAdminServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AjaxResult adminRegister(AdminDTO dto) {
        if (accountService.exist(dto.getAccount())) {
            return BaseResult.failedResult(ReturnData.USERNAME_EXIST);
        }
        SysUserAdmin admin = new SysUserAdmin();
        BeanUtils.copyProperties(dto, admin);
        admin.setId(IdUtils.nextId());
        admin.setStatus(CommonConstants.ZERO_INT);
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        String salt = CodeUtil.makeAuthCodeSix();
        String pwd = "";
        try {
            pwd = SignUtils.generateSign(dto.getPassword() + salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        admin.setSalt(salt);
        admin.setPassword(pwd);
        if (sysUserAdminMapper.insert(admin) == 1) {
            accountService.saveWithType(Account.copy2Account(admin), RoleConstants.ADMIN_ROLE, admin.getId());
            return BaseResult.successResult(ReturnData.REGISTER_SUCCESS.getCode(), ReturnData.REGISTER_SUCCESS.getMsg());
        }
        return BaseResult.failedResult();
    }

    @Override
    public PageResult adminList(AdminDTO adminDTO) {
        QueryWrapper<SysUserAdmin> wrapper = new QueryWrapper<>();
        //状态 0正常 2已删除
        wrapper.ne("status", CommonConstants.TWO_INT);
        wrapper.ne("account", "admin");
        wrapper.like(StringUtils.isNotBlank(adminDTO.getAccount()), "account", adminDTO.getAccount());
        wrapper.like(StringUtils.isNotBlank(adminDTO.getName()), "name", adminDTO.getName());
        wrapper.eq(adminDTO.getRoleId() != null, "role_id", adminDTO.getRoleId());
        wrapper.ge(adminDTO.getBeginTime() != null, "create_time", adminDTO.getBeginTime());
        wrapper.le(adminDTO.getEndTime() != null, "create_time", adminDTO.getEndTime());
        wrapper.orderByDesc("create_time");
        Pages pages = adminDTO.getPages();
        IPage<SysUserAdmin> page = baseMapper.selectPage(new Page<>(pages.getPageIndex(), pages.getPageSize()), wrapper);
        List<AdminVO> adminVOS = new ArrayList<>();
        try {
            adminVOS = BeanUtil.listBeanToList(page.getRecords(), AdminVO.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        pages.setTotal((int) page.getTotal());
        return PageUtil.getPageResult(adminVOS, pages);
    }

    @Override
    public AjaxResult adminDetail(String id) {
        SysUserAdmin admin = sysUserAdminMapper.selectById(id);
        if (admin != null) {
            AdminVO vo = new AdminVO();
            BeanUtils.copyProperties(admin, vo);
            return BaseResult.successResult(vo);
        }
        return BaseResult.failedResult(ReturnData.USER_NOT_EXIST);
    }

    @Override
    public AjaxResult adminUpdate(AdminDTO dto) {
        SysUserAdmin admin = new SysUserAdmin();
        BeanUtils.copyProperties(dto, admin);
        String salt = CodeUtil.makeAuthCodeSix();
        String pwd = "";
        try {
            pwd = SignUtils.generateSign(dto.getPassword() + salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        admin.setSalt(salt);
        admin.setPassword(pwd);
        admin.setUpdateTime(LocalDateTime.now());
        if (sysUserAdminMapper.updateById(admin) == 1) {
            if (admin.getStatus() != 0) {
                accountService.selectOne(admin.getId(), RoleConstants.ADMIN_ROLE_STRING).ifPresent(accountVO -> {
                    accountVO.setStatus(admin.getStatus());
                    accountService.updateById(accountVO);
                });
            }
            return BaseResult.successResult();
        }
        return BaseResult.failedResult(ReturnData.UPD_USER_ERROR);
    }

    @Override
    public Integer getRoleId(String account) {
        return sysUserAdminMapper.selectRoleId(account);
    }


    @Override
    public SysUserVO adminCheck(LoginDTO dto) {

        QueryWrapper<SysUserAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("account", dto.getAccount());
        wrapper.ne("status", "2");
        SysUserAdmin admin = this.getOne(wrapper);
        if (admin == null || StringUtils.isBlank(admin.getId())) {
            throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }
        if (admin.getStatus() == 1) {
            throw new YidaoException(ReturnData.LOGIN_DENY);
        }
        if (admin.getStatus() == 2) {
            throw new YidaoException(ReturnData.NOT_USER);
        }
        /* 密码加密 */
        String mdPwd;
        /*try {
            mdPwd = SignUtils.generateSign(dto.getPassword() + admin.getSalt());
        } catch (NoSuchAlgorithmException e) {
            LoggerUtils.error(SysUserAdminServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.MD5_ERROR);
        }
        if (!admin.getPassword().equals(mdPwd)) {
            throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }*/
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(admin, sysUserVO);
        return sysUserVO;
    }
}
