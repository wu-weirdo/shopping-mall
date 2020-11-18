package com.edaochina.shopping.api.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysDictionaryParamMapper extends BaseMapper<SysDictionaryParam> {

    SysDictionaryParam querySysValue(@Param("sysType") String sysType, @Param("sysKey") String sysKey);

    List<SysDictionaryParam> querySysValueByTypeAndKey(@Param("sysType") String sysType, @Param("sysKey") String sysKey);

    int updateSysValueByTypeAndKey(@Param("sysType") String sysType, @Param("sysKey") String sysKey, @Param("sysValue") String sysValue);
}
