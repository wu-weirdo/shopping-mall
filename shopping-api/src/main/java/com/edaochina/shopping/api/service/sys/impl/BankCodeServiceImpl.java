package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.BankCodeMapper;
import com.edaochina.shopping.api.service.sys.BankCodeService;
import com.edaochina.shopping.domain.entity.sys.BankCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jintian
 * @date 2019/7/29 16:33
 */
@Service
public class BankCodeServiceImpl implements BankCodeService {

    @Autowired
    private BankCodeMapper mapper;

    @Override
    public List<BankCode> queryBankCodes() {
        return mapper.queryBankCodes();
    }
}
