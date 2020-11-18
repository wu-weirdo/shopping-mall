package com.edaochina.shopping.api.service.user.impl;

import com.edaochina.shopping.api.dao.user.CommunityPartenerCountyInfoMapper;
import com.edaochina.shopping.api.dao.user.SysCommunityPartnerMapper;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.api.service.user.SysCommunityPartnerService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.CodeUtil;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.SignUtils;
import com.edaochina.shopping.common.utils.date.DateUtil;
import com.edaochina.shopping.common.utils.date.LocalDateTimeUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.user.CheckInvitatCodeDTO;
import com.edaochina.shopping.domain.dto.user.CommuntyPartnerDTO;
import com.edaochina.shopping.domain.dto.user.CommuntyPartnerRegisterDTO;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import com.edaochina.shopping.domain.entity.user.Account;
import com.edaochina.shopping.domain.entity.user.CommunityPartenerCountyInfo;
import com.edaochina.shopping.domain.entity.user.SysCommunityPartner;
import com.edaochina.shopping.domain.vo.user.SysCommunityPartnerDetailVO;
import com.edaochina.shopping.domain.vo.user.SysCommunityPartnerVo;
import com.edaochina.shopping.domain.vo.user.SysUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * @author jintian
 * @date 2019/7/24 14:27
 */
@Service
public class SysCommunityPartnerServiceImpl implements SysCommunityPartnerService {
    private final AccountService accountService;
    @Resource
    private SysCommunityPartnerMapper sysCommunityPartnerMapper;
    @Resource
    private CommunityPartenerCountyInfoMapper communityPartenerCountyInfoMapper;

    public SysCommunityPartnerServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean register(CommuntyPartnerRegisterDTO dto) {
        SysCommunityPartner sysCommunityPartner = new SysCommunityPartner();
        String id = IdUtils.nextId();
        BeanUtils.copyProperties(dto, sysCommunityPartner);
        //  合作区县添加
        sysCommunityPartner.setId(id);
        // 设置合作最后期限
        sysCommunityPartner.setMemberTime(LocalDateTimeUtil.dateToLocalDateTime(DateUtil.addYears(new Date(), 1)));
        String salt = CodeUtil.makeAuthCodeSix();
        // 唯一性检查
        if (accountService.exist(dto.getAccount())) {
            throw new YidaoException(ReturnData.USERNAME_EXIST);
        }
        // 设置邀请码
        sysCommunityPartner.setInvitatCode(salt);
        // 密码处理
        String pwd = "";
        try {
            pwd = SignUtils.generateSign(dto.getPassword() + salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        sysCommunityPartner.setPassword(pwd);
        sysCommunityPartner.setSalt(salt);
        for (CommunityPartenerCountyInfo communityPartenerCountyInfo : dto.getAreaInfos()) {
            communityPartenerCountyInfo.setCommunityPartenerId(id);
            communityPartenerCountyInfoMapper.insertPartenerCounty(communityPartenerCountyInfo);
        }
        accountService.saveWithType(Account.copy2Account(sysCommunityPartner), RoleConstants.COMMUNITY_PARTENER, id);
        buildCode(sysCommunityPartner);
        return sysCommunityPartnerMapper.insertSelective(sysCommunityPartner) > 0;
    }

    private void buildCode(SysCommunityPartner communityPartner) {
        String code = CodeUtil.makeAuthCode(6);
        while (sysCommunityPartnerMapper.queryByLiveCode(code) != null) {
            code = CodeUtil.makeAuthCode(6);
        }
        communityPartner.setLiveCode(code);
    }

    @Override
    public PageResult<SysCommunityPartnerVo> querySysCommunityPartners(CommuntyPartnerDTO dto) {
        PageHelperUtils.setPageHelper(dto.getPages());
        //  查询内容
        List<SysCommunityPartnerVo> sysCommunityPartnerVos = sysCommunityPartnerMapper.querySysCommunityPartners(dto);
        return PageHelperUtils.parseToPageResult(sysCommunityPartnerVos);
    }

    @Override
    public SysCommunityPartnerDetailVO queryDetal(String id) {
        SysCommunityPartnerDetailVO vo = new SysCommunityPartnerDetailVO();
        SysCommunityPartner sysCommunityPartner = sysCommunityPartnerMapper.selectByPrimaryKey(id);
        if (sysCommunityPartner == null) {
            throw new YidaoException("社群合伙人不存在");
        }
        BeanUtils.copyProperties(sysCommunityPartner, vo);
        //  代理区县
        List<CommunityPartenerCountyInfo> communityPartenerCountyInfos = communityPartenerCountyInfoMapper.communityPartenerCountyInfoList(id);
        vo.setAreaInfos(communityPartenerCountyInfos);
        return vo;
    }

    @Override
    public boolean update(CommuntyPartnerRegisterDTO dto) {
        SysCommunityPartner sysCommunityPartner = new SysCommunityPartner();

        BeanUtils.copyProperties(dto, sysCommunityPartner);
        if (dto.getStatus() == 2) {
            if (sysCommunityPartner.getStatus() != 0) {
                accountService.selectOne(sysCommunityPartner.getId(), RoleConstants.COMMUNITY_PARTENER_STRING).ifPresent(accountVO -> {
                    accountVO.setStatus(sysCommunityPartner.getStatus());
                    accountService.updateById(accountVO);
                });
            }
            return sysCommunityPartnerMapper.updateByPrimaryKeySelective(sysCommunityPartner) > 0;
        }
        communityPartenerCountyInfoMapper.removeByPartenerId(dto.getId());

        if (StringUtils.isNotBlank(dto.getPassword())) {
            String salt = CodeUtil.makeAuthCodeSix();
            String pwd = "";
            try {
                pwd = SignUtils.generateSign(dto.getPassword() + salt);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            sysCommunityPartner.setPassword(pwd);
            sysCommunityPartner.setSalt(salt);
        }
        //  合作区县添加
        for (CommunityPartenerCountyInfo communityPartenerCountyInfo : dto.getAreaInfos()) {
            communityPartenerCountyInfo.setCommunityPartenerId(dto.getId());
            communityPartenerCountyInfoMapper.insertPartenerCounty(communityPartenerCountyInfo);
        }
        buildCode(sysCommunityPartner);
        return sysCommunityPartnerMapper.updateByPrimaryKeySelective(sysCommunityPartner) > 0;
    }

    @Override
    public SysUserVO partenerCheck(LoginDTO dto) {
        SysCommunityPartner communityPartner = sysCommunityPartnerMapper.queryByAccount(dto.getAccount());
        if (communityPartner == null || StringUtils.isBlank(communityPartner.getId())) {
            throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }
        if (communityPartner.getStatus() == 1) {
            throw new YidaoException(ReturnData.LOGIN_DENY);
        }
        if (communityPartner.getStatus() == 2) {
            throw new YidaoException(ReturnData.NOT_USER);
        }
        /* 密码加密 */
        String mdPwd;
        try {
            mdPwd = SignUtils.generateSign(dto.getPassword() + communityPartner.getSalt());
        } catch (NoSuchAlgorithmException e) {
            LoggerUtils.error(SysUserAdminServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.MD5_ERROR);
        }
        if (!communityPartner.getPassword().equals(mdPwd)) {
            throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(communityPartner, sysUserVO);
        return sysUserVO;
    }

    @Override
    public int addBalance(String partenerId, BigDecimal agentPrice) {
        // 余额添加
        return sysCommunityPartnerMapper.addBalance(partenerId, agentPrice);
    }

    @Override
    public SysCommunityPartner checkInvitatCode(CheckInvitatCodeDTO dto) {
        SysCommunityPartner sysCommunityPartner = sysCommunityPartnerMapper.checkInvitatCode(dto);
        if (sysCommunityPartner == null) {
            throw new YidaoException(ReturnData.IDENTITY_CODE_ERROR);
        }
        return sysCommunityPartner;
    }
}
