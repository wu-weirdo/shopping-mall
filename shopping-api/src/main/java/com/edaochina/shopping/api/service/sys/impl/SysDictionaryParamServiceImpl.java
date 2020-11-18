package com.edaochina.shopping.api.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.sys.SysDictionaryParamMapper;
import com.edaochina.shopping.api.service.sys.SysDictionaryParamService;
import com.edaochina.shopping.common.utils.Md5Util;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysDictionaryParamServiceImpl extends ServiceImpl<SysDictionaryParamMapper, SysDictionaryParam> implements SysDictionaryParamService {

    @Resource
    SysDictionaryParamMapper sysDictionaryParamMapper;

    @Override
    public List<SysDictionaryParam> querySysValueByTypeAndKey(String sysType, String sysKey) {
        return sysDictionaryParamMapper.querySysValueByTypeAndKey(sysType, sysKey);
    }

    @Override
    public int updateSysValueByTypeAndKey(String sysType, String sysKey, String sysValue) {
        if (sysKey.contains("password")) {
            sysValue = Md5Util.MD5(sysValue);
        }
        return sysDictionaryParamMapper.updateSysValueByTypeAndKey(sysType, sysKey, sysValue);
    }

    @Override
    public boolean checkValue(String sysType, String sysKey, String sysValue) {
        return sysDictionaryParamMapper.querySysValue(sysType, sysKey).getSysValue().equals(Md5Util.MD5(sysValue));
    }
}
