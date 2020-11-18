package com.edaochina.shopping.api.service.trade.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.trade.MemberOrderCalcDetailMapper;
import com.edaochina.shopping.api.dao.user.SysCommunityPartnerMapper;
import com.edaochina.shopping.api.dao.user.SysUserAgentMapper;
import com.edaochina.shopping.api.service.trade.MemberOrderCalcDetailService;
import com.edaochina.shopping.common.utils.excel.ExportExcelUtil;
import com.edaochina.shopping.domain.constants.ProfitConstants;
import com.edaochina.shopping.domain.dto.order.MemberOrderCountDTO;
import com.edaochina.shopping.domain.entity.trade.MemberOrderCalcDetail;
import com.edaochina.shopping.domain.entity.user.SysCommunityPartner;
import com.edaochina.shopping.domain.entity.user.SysUserAgent;
import com.edaochina.shopping.domain.vo.order.SysMemberOrderCountVO;
import com.edaochina.shopping.domain.vo.order.SysMemberOrderMonthVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author User
 */
@Service
public class MemberOrderCalcDetailServiceImpl implements MemberOrderCalcDetailService {

    @Resource
    MemberOrderCalcDetailMapper memberOrderCalcDetailMapper;

    private final SysUserAgentMapper sysUserAgentMapper;
    private final SysCommunityPartnerMapper sysCommunityPartnerMapper;

    public MemberOrderCalcDetailServiceImpl(SysUserAgentMapper sysUserAgentMapper, SysCommunityPartnerMapper sysCommunityPartnerMapper) {
        this.sysUserAgentMapper = sysUserAgentMapper;
        this.sysCommunityPartnerMapper = sysCommunityPartnerMapper;
    }

    @Override
    public List<MemberOrderCalcDetail> selectMemberOrderCalcDetailByProfitType(Integer profitType) {
        return memberOrderCalcDetailMapper.selectMemberOrderCalcDetailByProfitType(profitType);
    }

    @Override
    public int insert(MemberOrderCalcDetail memberOrderCalcDetail) {
        return memberOrderCalcDetailMapper.insert(memberOrderCalcDetail);
    }

    @Override
    public List<SysMemberOrderCountVO> queryUserMemberOrderCount(MemberOrderCountDTO memberOrderCountDto) {
        return memberOrderCalcDetailMapper.queryUserMemberOrderCount(memberOrderCountDto);
    }

    @Override
    public List<MemberOrderCalcDetail> queryUnSupplement(String countyCode, Integer memberType, Integer supplementType) {
        return memberOrderCalcDetailMapper.queryUnSupplement(countyCode, memberType, supplementType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void supplement(MemberOrderCalcDetail userUnSupplement, BigDecimal supplementRatio) {
        userUnSupplement.setProfitResult(userUnSupplement.getMemberPrice().multiply(supplementRatio));
        userUnSupplement.setSupplementType(true);
        memberOrderCalcDetailMapper.insert(userUnSupplement);

        memberOrderCalcDetailMapper.setToSupplemented(userUnSupplement.getId());
    }

    @Override
    public BigDecimal getAgentCalcCountInfo(String agentId, Date startTime, Date endTime) {
        return memberOrderCalcDetailMapper.getAgentCalcCountInfo(agentId, startTime, endTime);
    }

    @Override
    public List<SysMemberOrderMonthVO.MonthDetail> month(MemberOrderCountDTO memberOrderCountDto) {
        if (Objects.equals(memberOrderCountDto.getMemberType(), ProfitConstants.MemberType.AGENT)
                && StringUtils.isEmpty(memberOrderCountDto.getUserId())) {
            QueryWrapper<SysUserAgent> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(memberOrderCountDto.getName())) {
                queryWrapper.eq("name", memberOrderCountDto.getName());
            }
            if (!StringUtils.isEmpty(memberOrderCountDto.getPhone())) {
                queryWrapper.eq("phone", memberOrderCountDto.getPhone());
            }
            SysUserAgent sysUserAgent = sysUserAgentMapper.selectOne(queryWrapper);
            if (sysUserAgent != null) {
                memberOrderCountDto.setUserId(sysUserAgent.getId());
            }
        } else if (Objects.equals(memberOrderCountDto.getMemberType(), ProfitConstants.MemberType.COMMUNITY_PARTNER)
                && StringUtils.isEmpty(memberOrderCountDto.getUserId())) {
            SysCommunityPartner sysCommunityPartner = new SysCommunityPartner();
            sysCommunityPartner.setName(memberOrderCountDto.getName());
            sysCommunityPartner.setPhone(memberOrderCountDto.getPhone());
            sysCommunityPartner = sysCommunityPartnerMapper.selectByNameAndPhone(sysCommunityPartner);
            if (sysCommunityPartner != null) {
                memberOrderCountDto.setUserId(sysCommunityPartner.getId());
            }
        }
        List<SysMemberOrderMonthVO> monthList = memberOrderCalcDetailMapper.selectProfitGroupByMonth(memberOrderCountDto);
        Map<String, SysMemberOrderMonthVO.MonthDetail> detailMap = new HashMap<>(monthList.size() / 2);
        monthList.forEach(sysMemberOrderMonthVO -> detailMap.compute(sysMemberOrderMonthVO.getMonth(), (s, monthDetail) -> {
            if (monthDetail == null) {
                monthDetail = new SysMemberOrderMonthVO.MonthDetail();
            }
            return monthDetail.bind(sysMemberOrderMonthVO);
        }));
        return new ArrayList<>(detailMap.values());
    }

    @Override
    public String export(MemberOrderCountDTO dto) {
        List<SysMemberOrderMonthVO.MonthDetail> detailList = month(dto);
        ExportExcelUtil.Builder builder = ExportExcelUtil.Builder.newInstance()
                .createSheet("月度分润导出")
                .createHeader(Arrays.asList("月份", "会员用户数", "用户分润额", "会员商家数", "商户分润额", "合计收益",
                        "已提现金额"))
                .setFields(Arrays.asList("getMonth", "getMemberNum", "getProfitPrice", "getMerchantMemberNum", "getMerchantProfitPrice",
                        "getWithdrawal", "getProfitPriceSum"));
        builder.put(detailList);
        return builder.export();
    }

    @Override
    public BigDecimal getPartenerCalcCountInfo(String partenerId, Date beginDayOfWeek, Date endDayOfWeek) {
        return memberOrderCalcDetailMapper.getPartenerCalcCountInfo(partenerId, beginDayOfWeek, endDayOfWeek);
    }
}
