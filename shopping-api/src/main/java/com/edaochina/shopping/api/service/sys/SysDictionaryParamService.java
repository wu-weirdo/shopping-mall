package com.edaochina.shopping.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;

import java.util.List;

public interface SysDictionaryParamService extends IService<SysDictionaryParam> {

    List<SysDictionaryParam> querySysValueByTypeAndKey(String sysType, String sysKey);

    int updateSysValueByTypeAndKey(String sysType, String sysKey, String sysValue);

    boolean checkValue(String sysType, String sysKey, String sysValue);
}
