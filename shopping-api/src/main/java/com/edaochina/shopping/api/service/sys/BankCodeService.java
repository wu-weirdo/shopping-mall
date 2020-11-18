package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.sys.BankCode;

import java.util.List;

/**
 * @author jintian
 * @date 2019/7/29 16:32
 */
public interface BankCodeService {
    List<BankCode> queryBankCodes();
}
