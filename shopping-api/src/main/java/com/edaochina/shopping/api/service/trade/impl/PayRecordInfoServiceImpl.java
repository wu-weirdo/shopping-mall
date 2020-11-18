package com.edaochina.shopping.api.service.trade.impl;

import com.edaochina.shopping.api.dao.trade.PayRecordInfoMapper;
import com.edaochina.shopping.api.service.trade.PayRecordInfoService;
import com.edaochina.shopping.domain.entity.trade.PayRecordInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayRecordInfoServiceImpl implements PayRecordInfoService {

    @Resource
    PayRecordInfoMapper payRecordInfoMapper;

    @Override
    public int insertPayRecord(PayRecordInfo payRecordInfo) {
        return payRecordInfoMapper.insertPayRecord(payRecordInfo);
    }

    @Override
    public int updatePayRecord(PayRecordInfo payRecordInfo) {
        return payRecordInfoMapper.updatePayRecord(payRecordInfo);
    }

    @Override
    public PayRecordInfo queryById(String orderId) {
        return payRecordInfoMapper.queryPayRecordById(orderId);
    }
}
