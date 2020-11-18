package com.edaochina.shopping.api.dao.trade;

import com.edaochina.shopping.domain.entity.trade.PayRecordInfo;

public interface PayRecordInfoMapper {

    int insertPayRecord(PayRecordInfo payRecordInfo);

    int updatePayRecord(PayRecordInfo payRecordInfo);

    PayRecordInfo queryPayRecordById(String id);
}
