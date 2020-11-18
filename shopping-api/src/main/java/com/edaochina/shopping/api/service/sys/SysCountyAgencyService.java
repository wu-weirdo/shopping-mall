package com.edaochina.shopping.api.service.sys;


import com.edaochina.shopping.domain.dto.sys.SysCountyAgencyDto;
import com.edaochina.shopping.domain.vo.sys.SysAgentAreaVo;

/**
 * <p>
 * 区县代理查询表 服务类
 * </p>
 *
 * @since 2019-05-20
 */
public interface SysCountyAgencyService {

    SysAgentAreaVo query(SysCountyAgencyDto search);
}
