package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.sys.SysContractTemplate;

/**
 * <p>
 * 合同模板表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-24
 */
public interface SysContractTemplateService {

    SysContractTemplate queryByUserType(String type);

    String createMerchantContract();
}
