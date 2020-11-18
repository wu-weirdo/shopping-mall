package com.edaochina.shopping.api.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.SysUserAgentMapper;
import com.edaochina.shopping.api.dao.user.SysUserMerchantMapper;
import com.edaochina.shopping.api.dao.user.UserMapper;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.api.service.user.AgentCountyInfoService;
import com.edaochina.shopping.api.service.user.SysUserAgentService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.CharUtils;
import com.edaochina.shopping.common.utils.CodeUtil;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.SignUtils;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.user.AgentDTO;
import com.edaochina.shopping.domain.dto.user.AgentRegDTO;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import com.edaochina.shopping.domain.dto.user.UpdAgentDTO;
import com.edaochina.shopping.domain.entity.user.*;
import com.edaochina.shopping.domain.vo.user.AgentVO;
import com.edaochina.shopping.domain.vo.user.SysUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 代理商表 by 张志侃 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Service
public class SysUserAgentServiceImpl extends ServiceImpl<SysUserAgentMapper, SysUserAgent> implements SysUserAgentService {

    @Resource
    private SysUserAgentMapper agentMapper;

    private final AccountService accountService;
    @Resource
    private AgentCountyInfoService agentCountyInfoService;
    @Resource
    private SysUserMerchantMapper merchantMapper;
    @Resource
    private UserMapper userMapper;

    public SysUserAgentServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult agentRegister(AgentRegDTO dto) {
        if (dto.getIdentityNo().length() > 18) {
            throw new YidaoException("身份证号码长度异常!");
        }
        if (dto.getAgentAreaInfos() == null || dto.getAgentAreaInfos().size() == 0) {
            throw new YidaoException(ReturnData.ORDER_PARAM_EMPTY);
        }
        //判断账号是否存在
        if (accountService.exist(dto.getAccount())) {
            return BaseResult.failedResult(ReturnData.USERNAME_EXIST);
        }
        //判断该县是否已存在代理商
        boolean checkResult = agentCountyInfoService.checkAgentByCountyId(dto.getAgentAreaInfos(), "");
        if (checkResult) {
            return BaseResult.failedResult(ReturnData.CITY_AGENT_EXIST);
        }
        SysUserAgent agent = new SysUserAgent();
        buildCode(agent);
        BeanUtils.copyProperties(dto, agent);
        agent.setId(IdUtils.nextId());
        // 设置代理商区县信息
        for (AgentCountyInfo agentAreaInfo : dto.getAgentAreaInfos()) {
            agentAreaInfo.setAgentId(agent.getId());
            if (org.springframework.util.StringUtils.isEmpty(agentAreaInfo.getCityName())) {
                throw new YidaoException("代理城市名称参数缺失!");
            }
            agentCountyInfoService.insertAgentCounty(agentAreaInfo);
        }
        agent.setName(CharUtils.decodeby10(dto.getName()));
        agent.setStatus(CommonConstants.ZERO_INT);
        agent.setCreateTime(LocalDateTime.now());
        agent.setUpdateTime(LocalDateTime.now());
        String salt = CodeUtil.makeAuthCodeSix();
        String pwd = "";
        try {
            pwd = SignUtils.generateSign(dto.getPassword() + salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        agent.setSalt(salt);
        agent.setPassword(pwd);
        if (agentMapper.insert(agent) == 1) {
            accountService.saveWithType(Account.copy2Account(agent), RoleConstants.AGENT_ROLE, agent.getId());
            return BaseResult.successResult(ReturnData.REGISTER_SUCCESS);
        }
        return BaseResult.failedResult();
    }

    private void buildCode(SysUserAgent agent) {
        String code = CodeUtil.makeAuthCode(6);
        QueryWrapper<SysUserAgent> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserAgent::getLiveCode, code);
        while (this.count(wrapper) != 0) {
            wrapper = new QueryWrapper<>();
            code = CodeUtil.makeAuthCode(6);
            wrapper.lambda().eq(SysUserAgent::getLiveCode, code);
        }
        agent.setLiveCode(code);
    }

    @Override
    public SysUserVO agentCheck(LoginDTO dto) {
        QueryWrapper<SysUserAgent> wrapper = new QueryWrapper<>();
        wrapper.eq("account", dto.getAccount());
        wrapper.ne("status", 2);
        SysUserAgent agent = this.getOne(wrapper);
        if (agent == null || StringUtils.isBlank(agent.getId())) {
            throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }
        /* 密码加密 */
        String mdPwd;
        try {
            mdPwd = SignUtils.generateSign(dto.getPassword() + agent.getSalt());
        } catch (NoSuchAlgorithmException e) {
            LoggerUtils.error(SysUserAgentServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.MD5_ERROR);
        }
        if (!agent.getPassword().equals(mdPwd)) {
            throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }
   /*     if (!dto.getOpenid().isEmpty()){
            agentMapper.updateOpenid(dto.getAccount(),dto.getOpenid());
        }*/
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(agent, sysUserVO);
        return sysUserVO;
    }

    @Override
    public PageResult agentList(AgentDTO agentDTO) {
        // 查总数
        int n = agentMapper.queryCountByAgentDTO(agentDTO);
        Pages pages = agentDTO.getPages();
        pages.setTotal(n);
        agentDTO.setPageSize(pages.getPageSize());
        agentDTO.setStartNum(pages.getPageSize() * (pages.getPageIndex() - 1));
        // 查分页结果
        List<SysUserAgent> sysUserAgents = agentMapper.queryByAgentDTO(agentDTO);
        return PageUtil.getPageResult(sysUserAgents, pages);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult agentUpdate(UpdAgentDTO updAgentDTO) {
        SysUserAgent userAgent = new SysUserAgent();
        BeanUtils.copyProperties(updAgentDTO, userAgent);
        // 删除
        if (updAgentDTO.getStatus() != null && updAgentDTO.getStatus() == 2) {
            agentCountyInfoService.removeByAgentId(updAgentDTO.getId());
            agentMapper.updateById(userAgent);
            accountService.selectOne(userAgent.getId(), RoleConstants.AGENT_ROLE_STRING).ifPresent(accountVO -> {
                accountVO.setStatus(userAgent.getStatus());
                accountService.updateById(accountVO);
            });
            return BaseResult.successResult();
        }
        boolean checkResult = agentCountyInfoService.checkAgentByCountyId(updAgentDTO.getAgentAreaInfos(), updAgentDTO.getId());
        if (checkResult) {
            return BaseResult.failedResult(ReturnData.CITY_AGENT_EXIST);
        }
        if (StringUtils.isNotBlank(updAgentDTO.getPassword())) {
            String salt = CodeUtil.makeAuthCodeSix();
            String pwd = "";
            try {
                pwd = SignUtils.generateSign(updAgentDTO.getPassword() + salt);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            userAgent.setPassword(pwd);
            userAgent.setSalt(salt);
        }
        userAgent.setUpdateTime(LocalDateTime.now());
        if (updAgentDTO.getStatus() == null) {
            // 设置代理商区县信息
            agentCountyInfoService.removeByAgentId(updAgentDTO.getId());
            for (AgentCountyInfo agentAreaInfo : updAgentDTO.getAgentAreaInfos()) {
                agentAreaInfo.setAgentId(userAgent.getId());
                agentCountyInfoService.insertAgentCounty(agentAreaInfo);
            }
        }
        // 删除非代理区县的代理信息
        // agentCountyInfoService.removeErrorCountyInfo(updAgentDTO.getAgentAreaInfos(),updAgentDTO.getId());
        buildCode(userAgent);
        if (agentMapper.updateById(userAgent) == 1) {
            return BaseResult.successResult();
        }
        return BaseResult.failedResult(ReturnData.UPD_USER_ERROR);
    }


    @Override
    public AjaxResult agentDetail(String id) {
        SysUserAgent agent = agentMapper.selectById(id);
        if (agent != null) {
            AgentVO agentVO = new AgentVO();
            BeanUtils.copyProperties(agent, agentVO);
            List<CountyInfo> agentCountyInfos = agentCountyInfoService.agentCountyList(id);
            agentVO.setAgentAreaInfos(agentCountyInfos);
            Integer merchantMemberNum = merchantMapper.getMemberNumByAgentId(id);
            agentVO.setMerchantMemberNum(merchantMemberNum == null ? 0 : merchantMemberNum);
            Integer userMemberNum = userMapper.getMemberNumByAgentId(id);
            agentVO.setUserMemberNum(userMemberNum == null ? 0 : userMemberNum);
            return BaseResult.successResult(agentVO);
        }
        return BaseResult.failedResult(ReturnData.USER_NOT_EXIST);
    }

    @Override
    public SysUserAgent queryByCountyId(String countyId) {
        return agentMapper.queryAgentByCounty(countyId);
    }

    @Override
    public int addProfitMoney(String userId, BigDecimal toAccountMoney) {
        return agentMapper.updateBalanceMoney(userId, toAccountMoney.doubleValue());
    }

    @Override
    public SysUserAgent queryMerchantAffiAgent(String shareMerchantId) {
        SysUserMerchant merchant = merchantMapper.selectById(shareMerchantId);
        if (merchant != null) {
            return queryByCountyId(merchant.getCountyId());
        }
        return null;
    }
}
