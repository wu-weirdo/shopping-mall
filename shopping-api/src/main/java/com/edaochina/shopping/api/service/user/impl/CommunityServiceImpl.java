package com.edaochina.shopping.api.service.user.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.CommunityMapper;
import com.edaochina.shopping.api.dao.user.SysAddressMapper;
import com.edaochina.shopping.api.service.user.CommunityService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.user.CommunityAppListDTO;
import com.edaochina.shopping.domain.dto.user.CommunityListDTO;
import com.edaochina.shopping.domain.dto.user.CommunityUpdDTO;
import com.edaochina.shopping.domain.entity.sys.*;
import com.edaochina.shopping.domain.entity.user.Community;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.sys.SysBackCommunityAreaInfo;
import com.edaochina.shopping.domain.vo.sys.SysHasAgenAreaVo;
import com.edaochina.shopping.domain.vo.user.CommunityAppListVO;
import com.edaochina.shopping.domain.vo.user.CommunityListVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 小区表 by zzk
 * 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2019-01-07
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {
    private static Logger logger = LoggerFactory.getLogger(CommunityServiceImpl.class);

    @Resource
    private CommunityMapper communityMapper;

    @Resource
    private SysAddressMapper sysAddressMapper;

    @Autowired
    private SysUserMerchantService sysUserMerchantService;

    @Override
    public AjaxResult addCommunity(CommunityDTO dto) {
        //检查所选市是否存在代理商,取消檢查代理商
        Community community = new Community();
        BeanUtils.copyProperties(dto, community);
        community.setId(IdUtils.nextId());
        community.setCreateTime(LocalDateTime.now());
        community.setUpdateTime(LocalDateTime.now());
        SysAddress sysProvince = sysAddressMapper.selectById(dto.getProvinceId());
        SysAddress sysCity = sysAddressMapper.selectById(dto.getCityId());
        SysAddress sysCounty = sysAddressMapper.selectById(dto.getCountyId());
        community.setProvinceName(sysProvince.getName());
        community.setCityName(sysCity.getName());
        community.setCountyName(sysCounty.getName());
        if (communityMapper.insert(community) == 1) {
            return BaseResult.successResult();
        }
        return BaseResult.failedResult(ReturnData.ADD_COMM_ERROR);
    }

    @Override
    public PageResult<CommunityListVO> getSysList(CommunityListDTO dto) {
        logger.info("getSysList req dto:" + JSON.toJSONString(dto));
        if (StringUtils.isBlank(dto.getRoleId())) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER);
        }
        PageHelperUtils.setPageHelper(dto.getPages());
        List<CommunityListVO> communityListVOS = communityMapper.querySysCommunitys(dto);
        return PageHelperUtils.parseToPageResult(communityListVOS);
/*


        //如果角色id是4(代理商),则返回对应市区的数据
        if (CommonConstants.FOUR_STR.equals(dto.getRoleId().trim())) {
            List<String> countyIds = agentCountyInfoService.agentCountyStrList(dto.getUserId().trim());
            if (CollectionUtils.isNotEmpty(countyIds)) {
                // 代理商区县信息
                wrapper.in("county_id", countyIds);
            } else {
                wrapper.eq("county_id", -1);
            }
        }
        if (RoleConstants.COMMUNITY_PARTENER_STRING.equals(dto.getRoleId().trim())) {
            List<String> countyIds = communityPartenerCountyInfoMapper.partenerCountyStrList(dto.getUserId().trim());
            if (CollectionUtils.isNotEmpty(countyIds)) {
                // 代理商区县信息
                wrapper.in("county_id", countyIds);
            } else {
                wrapper.eq("county_id", -1);
            }
        }
        Pages pages = dto.getPages();
        IPage<Community> page = baseMapper.selectPage(new Page<>(pages.getPageIndex(), pages.getPageSize()), wrapper);
        List<CommunityListVO> communityListVOS = new ArrayList<>();
        try {
            communityListVOS = BeanUtil.listBeanToList(page.getRecords(), CommunityListVO.class);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        pages.setTotal((int) page.getTotal());
        return PageUtil.getPageResult(communityListVOS, pages);*/
    }

    @Override
    public AjaxResult getAppList(CommunityAppListDTO dto) {
        List<CommunityAppListVO> communities = communityMapper.selectByCoord(dto);
        return BaseResult.successResult(communities);
    }

    @Override
    public List<Community> getSysAll() {
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        return communityMapper.selectList(queryWrapper);
    }

    @Override
    public Community getDetail(String id) {
        return communityMapper.selectById(id);
    }

    @Override
    public int deleteComm(String id) {
        checkUpdate(id, "");
        return communityMapper.deleteCommunity(id);
    }

    @Override
    public int updateComm(CommunityUpdDTO dto) {
        Community community = new Community();
        checkUpdate(dto.getId(), dto.getCountyId());
        BeanUtils.copyProperties(dto, community);
        SysAddress sysProvince = sysAddressMapper.selectById(dto.getProvinceId());
        SysAddress sysCity = sysAddressMapper.selectById(dto.getCityId());
        SysAddress sysCounty = sysAddressMapper.selectById(dto.getCountyId());
        community.setProvinceName(sysProvince.getName());
        community.setCityName(sysCity.getName());
        community.setCountyName(sysCounty.getName());
        return communityMapper.updateById(community);
    }

    /**
     * 检查小区能否更新删除
     *
     * @param communityId
     */
    private void checkUpdate(String communityId, String countyId) {
        // 判断该小区低下有无商家信息
        Community community = this.getById(communityId);
        List<SysUserMerchant> userMerchants = sysUserMerchantService.querySysUserMerchantByCommunityId(communityId);
        if (userMerchants.size() > 0 && !community.getCountyId().equals(countyId)) {
            throw new YidaoException(ReturnData.UPDATE_COMM_ERROR);
        }
    }

    @Override
    public List<Community> getSysListByCountyId(String countyId) {
        return communityMapper.getSysListByCountyId(countyId);
    }

    @Override
    public SysAreaInfo selectHasAgentAreaList() {
        //List<SysHasAgenAreaVo> sysHasAgenAreaVos = communityMapper.queryDistinctArea();
        SysAreaInfo sysAreaInfo = RedisTool.get("HasAgentAreaList", SysAreaInfo.class);
        if (sysAreaInfo != null) {
            return sysAreaInfo;
        }
        List<SysHasAgenAreaVo> sysHasAgenAreaVos = sysAddressMapper.queryDistinctArea();
        sysAreaInfo = new SysAreaInfo();
        Map<String, List<SysCityInfo>> sysCityInfoMap = new HashMap<>();
        Map<String, List<SysCountyInfo>> sysCountyInfoMap = new HashMap<>();
        Map<String, SysProvinceInfo> sysProvinceInfoMap = new HashMap<>();
        Map<String, String> areas = new HashMap<>();
        sysHasAgenAreaVos.forEach(
                sysHasAgenAreaVo -> {
                    sysProvinceInfoMap.put(sysHasAgenAreaVo.getProvinceCode(), new SysProvinceInfo(sysHasAgenAreaVo.getProvinceName(), sysHasAgenAreaVo.getProvinceCode()));
                    if (!areas.containsKey(sysHasAgenAreaVo.getProvinceCode())) {
                        sysCityInfoMap.put(sysHasAgenAreaVo.getProvinceCode(), new ArrayList<SysCityInfo>());
                    }
                    if (!areas.containsKey(sysHasAgenAreaVo.getCityCode())) {
                        List<SysCityInfo> sysCityInfos = sysCityInfoMap.get(sysHasAgenAreaVo.getProvinceCode());
                        sysCityInfos.add(new SysCityInfo(sysHasAgenAreaVo.getProvinceName(), sysHasAgenAreaVo.getCityName(), sysHasAgenAreaVo.getCityCode()));
                        sysCityInfoMap.put(sysHasAgenAreaVo.getProvinceCode(), sysCityInfos);
                        sysCountyInfoMap.put(sysHasAgenAreaVo.getCityCode(), new ArrayList<SysCountyInfo>());
                    }
                    if (!areas.containsKey(sysHasAgenAreaVo.getCountyCode())) {
                        List<SysCountyInfo> sysCountyInfos = sysCountyInfoMap.get(sysHasAgenAreaVo.getCityCode());
                        sysCountyInfos.add(new SysCountyInfo(sysHasAgenAreaVo.getCityName(), sysHasAgenAreaVo.getCountyName(), sysHasAgenAreaVo.getCountyCode()));
                        sysCountyInfoMap.put(sysHasAgenAreaVo.getCityCode(), sysCountyInfos);
                    }
                    areas.put(sysHasAgenAreaVo.getProvinceCode(), sysHasAgenAreaVo.getProvinceName());
                    areas.put(sysHasAgenAreaVo.getCityCode(), sysHasAgenAreaVo.getCityName());
                    areas.put(sysHasAgenAreaVo.getCountyCode(), sysHasAgenAreaVo.getCountyName());
                }
        );
        sysAreaInfo.setAreas(sysCountyInfoMap);
        sysAreaInfo.setCitys(sysCityInfoMap);
        Collection<SysProvinceInfo> sysProvinceCollection = sysProvinceInfoMap.values();
        SysProvinceInfo[] sysProvinceInfos = new SysProvinceInfo[sysProvinceCollection.size()];
        sysProvinceCollection.toArray(sysProvinceInfos);
        sysAreaInfo.setProvinces(sysProvinceInfos);
        RedisTool.set("HasAgentAreaList", sysAreaInfo);
        return sysAreaInfo;
    }

    @Override
    public List<Community> getAgentList() {
        return communityMapper.getAgentList();
    }

    @Override
    public Collection<SysBackCommunityAreaInfo> queryHasCommunityList() {
        Collection<SysBackCommunityAreaInfo> SysBackCommunityAreaInfos = null;
        List<SysHasAgenAreaVo> sysHasAgenAreaVos = communityMapper.queryDistinctArea();
        Map<String, SysBackCommunityAreaInfo> sysBackCommunityAreaInfoMap = new HashMap<>();
        Map<String, SysBackCommunityAreaInfo> provinceMap = new HashMap<>();
        sysHasAgenAreaVos.forEach(sysHasAgenAreaVo -> {
            SysBackCommunityAreaInfo province = null;
            SysBackCommunityAreaInfo city = null;
            SysBackCommunityAreaInfo county = null;
            if (!sysBackCommunityAreaInfoMap.containsKey(sysHasAgenAreaVo.getProvinceCode())) {
                province = new SysBackCommunityAreaInfo(sysHasAgenAreaVo.getProvinceCode()
                        , sysHasAgenAreaVo.getProvinceName()
                        , new ArrayList<>());
            } else {
                province = sysBackCommunityAreaInfoMap.get(sysHasAgenAreaVo.getProvinceCode());
            }
            if (!sysBackCommunityAreaInfoMap.containsKey(sysHasAgenAreaVo.getCityCode())) {
                city = new SysBackCommunityAreaInfo(sysHasAgenAreaVo.getCityCode()
                        , sysHasAgenAreaVo.getCityName()
                        , new ArrayList<>());
                List<SysBackCommunityAreaInfo> cityChildren = province.getChildren();
                cityChildren.add(city);
                province.setChildren(cityChildren);
            } else {
                city = sysBackCommunityAreaInfoMap.get(sysHasAgenAreaVo.getCityCode());
            }
            if (!sysBackCommunityAreaInfoMap.containsKey(sysHasAgenAreaVo.getCountyCode())) {
                county = new SysBackCommunityAreaInfo(sysHasAgenAreaVo.getCountyCode()
                        , sysHasAgenAreaVo.getCountyName()
                        , null);
                List<SysBackCommunityAreaInfo> countyChildren = city.getChildren();
                countyChildren.add(county);
                city.setChildren(countyChildren);
            }
            sysBackCommunityAreaInfoMap.put(sysHasAgenAreaVo.getProvinceCode(), province);
            sysBackCommunityAreaInfoMap.put(sysHasAgenAreaVo.getCityCode(), city);
            sysBackCommunityAreaInfoMap.put(sysHasAgenAreaVo.getCountyCode(), county);
            provinceMap.put(sysHasAgenAreaVo.getProvinceCode(), province);
        });
        SysBackCommunityAreaInfos = provinceMap.values();
        return SysBackCommunityAreaInfos;
    }

}
