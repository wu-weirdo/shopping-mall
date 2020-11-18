package com.edaochina.shopping.api.facade.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.sys.SysDictionaryParamMapper;
import com.edaochina.shopping.api.dao.user.CommunityMapper;
import com.edaochina.shopping.api.dao.user.SysUserHistoryCommunityMapper;
import com.edaochina.shopping.api.dao.user.UserMapper;
import com.edaochina.shopping.api.facade.user.CommunityFacade;
import com.edaochina.shopping.api.service.sys.AMapService;
import com.edaochina.shopping.api.service.user.CommunityService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.user.CommunityAppListDTO;
import com.edaochina.shopping.domain.dto.user.CommunityListDTO;
import com.edaochina.shopping.domain.dto.user.CommunitySelectDTO;
import com.edaochina.shopping.domain.dto.user.CommunityUpdDTO;
import com.edaochina.shopping.domain.entity.sys.CommunityDTO;
import com.edaochina.shopping.domain.entity.sys.SysAddress;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import com.edaochina.shopping.domain.entity.sys.SysUserHistoryCommunity;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.vo.user.CommunityAppListVO;
import com.edaochina.shopping.domain.vo.user.CommunitySelectVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzk
 * @since 2019/1/11
 */
@Service
public class CommunityFacadeImpl implements CommunityFacade {
    @Resource
    CommunityService communityService;

    @Resource
    SysDictionaryParamMapper sysDictionaryParamMapper;

    @Resource
    AMapService aMapService;

    private final UserMapper userMapper;
    private final SysUserHistoryCommunityMapper sysUserHistoryCommunityMapper;
    private final CommunityMapper communityMapper;

    public CommunityFacadeImpl(UserMapper userMapper, SysUserHistoryCommunityMapper sysUserHistoryCommunityMapper, CommunityMapper communityMapper) {
        this.userMapper = userMapper;
        this.sysUserHistoryCommunityMapper = sysUserHistoryCommunityMapper;
        this.communityMapper = communityMapper;
    }

    @Override
    public AjaxResult addCommunity(CommunityDTO dto) {
        return communityService.addCommunity(dto);
    }

    @Override
    public AjaxResult getSysList(CommunityListDTO dto) {
        return BaseResult.successResult(communityService.getSysList(dto));
    }

    @Override
    public AjaxResult getAppList(CommunityAppListDTO dto) {
        if (dto.getMaxDistinct() != null) {
            return communityService.getAppList(dto);
        }
        SysDictionaryParam distinctDictionary = sysDictionaryParamMapper.querySysValue("community_range", "community_range");
        Long distinctValue = distinctDictionary == null ? -1 : Long.valueOf(distinctDictionary.getSysValue());
        if (distinctValue > 0) {
            dto.setMaxDistinct(distinctValue);
            return communityService.getAppList(dto);
        } else {
            return getSysAll();
        }
    }

    @Override
    public AjaxResult getSysAll() {
        return BaseResult.successResult(communityService.getSysAll());
    }

    @Override
    public AjaxResult getAgentList() {
        return BaseResult.successResult(communityService.getAgentList());
    }

    @Override
    public AjaxResult getDetail(String id) {
        return BaseResult.successResult(communityService.getDetail(id));
    }

    @Override
    public AjaxResult delete(String id) {
        return BaseResult.successResult(communityService.deleteComm(id));
    }

    @Override
    public AjaxResult updateComm(CommunityUpdDTO dto) {
        return BaseResult.successResult(communityService.updateComm(dto));
    }

    @Override
    public AjaxResult getSysListByCountyId(String countyId) {
        return BaseResult.successResult(communityService.getSysListByCountyId(countyId));
    }

    /**
     * 添加历史小区，如果没有绑定小区就绑定小区
     *
     * @param sysUserHistoryCommunity 用户选择小区信息
     * @return 是否成功
     */
    @Override
    public AjaxResult insertHistory(SysUserHistoryCommunity sysUserHistoryCommunity) {
        SysUser sysUser = userMapper.selectById(sysUserHistoryCommunity.getUserId());
        if (sysUser == null) {
            throw new YidaoException(ReturnData.USER_NOT_EXIST);
        }
        if (StringUtils.isEmpty(sysUser.getBindCommunity())) {
            sysUser.bindCommunity(communityService.getById(sysUserHistoryCommunity.getCommunityId()));
            userMapper.updateById(sysUser);
        }
        return BaseResult.successResult(sysUserHistoryCommunityMapper.insert(sysUserHistoryCommunity));
    }

    @Override
    public AjaxResultGeneric<CommunitySelectVO> select(CommunitySelectDTO dto) {
        CommunitySelectVO communitySelectVO = new CommunitySelectVO();
        if (dto.getMaxDistinct() == null) {
            SysDictionaryParam distinctDictionary = sysDictionaryParamMapper.querySysValue("community_range", "community_range");
            dto.setMaxDistinct(Long.valueOf(distinctDictionary.getSysValue()));
        }
        List<CommunityAppListVO> communityAppListVOS = null;
        SysDictionaryParam distinctDictionary = sysDictionaryParamMapper.querySysValue("approvel", "approvel_flag");
        // 判断是否在审核中，审核时去除无商品小区
        if ("是".equals(distinctDictionary.getSysValue())) {
            communityAppListVOS = communityMapper.selectInIdAndHasGoods(dto);
        } else {
            communityAppListVOS = communityMapper.selectInId(dto);
        }

        if (dto.getCountyId() == null) {
            List<String> communityIds = getSysUserHistoryCommunityList(dto.getUserId(), dto.getOpenId(), dto.getLimit(), dto.getCurrentCommunityId()).parallelStream()
                    .map(SysUserHistoryCommunity::getCommunityId)
                    .map(String::trim)
                    .collect(Collectors.toList());
            if (communityIds.size() > 0) {
                dto.setHistoryIds(communityIds);
                communitySelectVO.setHistory(communityMapper.selectByHistory(dto));
                if (StringUtils.isEmpty(dto.getName())) {
                    communityAppListVOS = communityAppListVOS.stream()
                            .filter(communityAppListVO -> !communityIds.contains(communityAppListVO.getId()))
                            .filter(communityAppListVO -> {
                                if (communityAppListVO.getId().equals(dto.getCurrentCommunityId())) {
                                    communitySelectVO.setCurrentCommunity(communityAppListVO);
                                    return false;
                                } else {
                                    return true;
                                }
                            })
                            .collect(Collectors.toList());
                }
            }
        }
        communitySelectVO.setNearby(communityAppListVOS);
        return BaseResult.successGenericResult(communitySelectVO);
    }

    @Override
    public AjaxResult selectHistory(String userId, String openId, int limit, String currentCommunityId) {
        return BaseResult.successResult(getSysUserHistoryCommunityList(userId, openId, limit, currentCommunityId));
    }

    @Override
    public AjaxResultGeneric<List<CommunityAppListVO>> queryCommunity(CommunitySelectDTO dto) {
        SysAddress address = aMapService.geocode(dto.getLongitude(), dto.getLatitude());
        if (StringUtils.isEmpty(address.getCode())) {
            return new AjaxResultGeneric<>(new ArrayList<>());
        }
        List<CommunityAppListVO> communityAppListVOS = communityMapper.queryCommunity(address.getCode(), dto.getLongitude(), dto.getLatitude(), dto.getName());
        // 如果没有的话查范围内的
        if (communityAppListVOS == null || communityAppListVOS.size() == 0) {
            SysDictionaryParam distinctDictionary = sysDictionaryParamMapper.querySysValue("community_range", "community_range");
            dto.setMaxDistinct(Long.valueOf(distinctDictionary.getSysValue()));
            communityAppListVOS = communityMapper.selectInId(dto);
        }
        return new AjaxResultGeneric<>(communityAppListVOS);
    }

    private List<SysUserHistoryCommunity> getSysUserHistoryCommunityList(String userId, String openId, int limit, String currentCommunityId) {
        QueryWrapper<SysUserHistoryCommunity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("open_id", openId);
        queryWrapper.notIn("community_id", currentCommunityId);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit " + limit);
        return sysUserHistoryCommunityMapper.selectList(queryWrapper);
    }
}
