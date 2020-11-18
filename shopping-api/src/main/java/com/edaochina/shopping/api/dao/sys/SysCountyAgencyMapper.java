package com.edaochina.shopping.api.dao.sys;


import com.edaochina.shopping.domain.dto.sys.SysCountyAgencyDto;
import com.edaochina.shopping.domain.entity.sys.SysCountyAgency;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 区县代理查询表 Mapper 接口
 * </p>
 *
 * @since 2019-05-20
 */
@Repository
public interface SysCountyAgencyMapper {

    List<SysCountyAgency> queryHistory();

    int save(SysCountyAgencyDto search);
}
