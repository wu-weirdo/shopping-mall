package com.edaochina.shopping.api.service.sys.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.SysAddressMapper;
import com.edaochina.shopping.api.dao.user.SysUserAgentMapper;
import com.edaochina.shopping.api.service.sys.SysAddressService;
import com.edaochina.shopping.api.service.user.AgentCountyInfoService;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.JWTUtil;
import com.edaochina.shopping.common.utils.http.HttpClientUtilsTool;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.dto.sys.SysAddressDTO;
import com.edaochina.shopping.domain.entity.sys.SysAddress;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 地址数据表 by张志侃 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-11
 */
@Service
public class SysAddressServiceImpl extends ServiceImpl<SysAddressMapper, SysAddress> implements SysAddressService {

    @Resource
    SysAddressMapper mapper;
    @Resource
    SysUserAgentMapper agentMapper;

    @Autowired
    private AgentCountyInfoService agentCountyInfoService;

    @Override
    public List<SysAddress> selectProvinceList() {
        QueryWrapper<SysAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("level", "1");
        return mapper.selectList(wrapper);
    }

    @Override
    public List<SysAddress> selectProvinceList(SysAddressDTO dto) {
        QueryWrapper<SysAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("level", "1");
        //如果角色id为4(代理商),只返回对应省份
        if (StringUtils.isNotBlank(dto.getRoleId()) && CommonConstants.FOUR_STR.equals(dto.getRoleId().trim()) && StringUtils.isNotBlank(dto.getUserId())) {
            // 代理商区县信息
            wrapper.in("code", agentCountyInfoService.agentProvinceStrList(dto.getUserId().trim()));
        }
        return mapper.selectList(wrapper);
    }

    @Override
    public List<SysAddress> selectProvinceListV2() {
        JWTBean jwtBean = JWTThreadLocalHelper.get();
        QueryWrapper<SysAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("level", "1");
        //如果角色id为4(代理商),只返回对应省份
        if (StringUtils.isNotBlank(jwtBean.getRole()) && CommonConstants.FOUR_STR.equals(jwtBean.getRole().trim()) && StringUtils.isNotBlank(jwtBean.getId())) {
            // 代理商区县信息
            wrapper.in("code", agentCountyInfoService.agentProvinceStrList(jwtBean.getId().trim()));
        }
        return mapper.selectList(wrapper);
    }

    @Override
    public List<SysAddress> selectCityList(SysAddressDTO dto) {
        QueryWrapper<SysAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_code", dto.getParentCode());
        wrapper.eq("level", "2");
        if (StringUtils.isNotBlank(dto.getRoleId()) && CommonConstants.FOUR_STR.equals(dto.getRoleId().trim()) && StringUtils.isNotBlank(dto.getUserId().trim())) {
            //  代理商区县信息
            wrapper.in("code", agentCountyInfoService.agentCityStrList(dto.getUserId()));
        }
        return mapper.selectList(wrapper);
    }

    @Override
    public List<SysAddress> selectCityList(SysAddressDTO dto, String token) {
        JWTBean jwtBean = JWTUtil.verifyToken(token);
        QueryWrapper<SysAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_code", dto.getParentCode());
        wrapper.eq("level", "2");
        if (StringUtils.isNotBlank(jwtBean.getRole()) && CommonConstants.FOUR_STR.equals(jwtBean.getRole().trim()) && StringUtils.isNotBlank(jwtBean.getId().trim())) {
            //  代理商区县信息
            wrapper.in("code", agentCountyInfoService.agentCityStrList(jwtBean.getId()));
        }
        return mapper.selectList(wrapper);
    }

    @Override
    public List<SysAddress> selectAreaList(SysAddressDTO dto) {
        QueryWrapper<SysAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_code", dto.getParentCode());
        wrapper.eq("level", "3");
        return mapper.selectList(wrapper);
    }

    @Override
    public List<SysAddress> selectAreaList(SysAddressDTO dto, String token) {
        JWTBean jwtBean = JWTUtil.verifyToken(token);
        QueryWrapper<SysAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_code", dto.getParentCode());
        wrapper.eq("level", "3");
        if (StringUtils.isNotBlank(jwtBean.getRole()) && CommonConstants.FOUR_STR.equals(jwtBean.getRole().trim()) && StringUtils.isNotBlank(jwtBean.getId().trim())) {
            //  代理商区县信息
            wrapper.in("code", agentCountyInfoService.agentCountyStrList(jwtBean.getId()));
        }
        return mapper.selectList(wrapper);
    }

    @Override
    public List<String> selectByAdcode(String adcode, String token) {
        JWTBean jwtBean = JWTUtil.verifyToken(token);
        SysAddress sysAddress = this.selectOneByAdcode(adcode);
        if (sysAddress == null) {
            return new ArrayList<>();
        }
        if (jwtBean.isAgent()) {
            //  代理商区县信息
            if (!agentCountyInfoService.agentCountyStrList(jwtBean.getId()).contains(sysAddress.getCode())) {
                return new ArrayList<>();
            }
        }
        List<SysAddress> sysAddresses = mapper.selectTreeByMinCode(sysAddress.getCode());
        return sysAddresses.stream().sorted(Comparator.comparingInt(value -> Integer.parseInt(value.getLevel()))).map(SysAddress::getCode).collect(Collectors.toList());
    }

    @Override
    public SysAddress selectOneByAdcode(String adcode) {
        QueryWrapper<SysAddress> sysAddressQueryWrapper = new QueryWrapper<>();
        sysAddressQueryWrapper.eq("adcode", adcode);
        List<SysAddress> addresses = mapper.selectList(sysAddressQueryWrapper);
        return addresses.stream().findFirst().orElse(null);
    }

    @Override
    public void initAdcode() {
        Api api = new Api();
        QueryWrapper<SysAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", "1");
        List<SysAddress> sysAddresses = mapper.selectList(queryWrapper);
        sysAddresses.forEach(api::get);
    }

    class Api {

        void get(SysAddress sysAddress) {
            JSONObject jsonObject = HttpClientUtilsTool.httpGet(buildUrl(sysAddress.getName()));
            if ("1".equals(jsonObject.getString("status"))) {
                jsonObject.getJSONArray("districts").forEach(address -> mapping((JSONObject) address));
            }
        }

        private void mapping(JSONObject jsonObject) {
            QueryWrapper<SysAddress> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", jsonObject.getString("name"));
            if (mapper.selectCount(queryWrapper) == 1) {
                SysAddress sysAddress = mapper.selectOne(queryWrapper);
                if (sysAddress != null) {
                    sysAddress.setAdcode(jsonObject.getString("adcode"));
                    mapper.updateById(sysAddress);
                }
            }
            jsonObject.getJSONArray("districts").forEach(address -> mapping((JSONObject) address));
        }

        private String buildUrl(String q) {
            String url = "https://restapi.amap.com/v3/config/district?";
            url += "key=0f4a8c29fa41877717f64c9d8948ba51";
            url += "&keywords=" + q;
            url += "&subdistrict=3";
            return url;
        }


    }
}
