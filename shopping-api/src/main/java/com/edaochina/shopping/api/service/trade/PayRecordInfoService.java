package com.edaochina.shopping.api.service.trade;

import com.edaochina.shopping.domain.entity.trade.PayRecordInfo;

public interface PayRecordInfoService {

    int insertPayRecord(PayRecordInfo payRecordInfo);

    int updatePayRecord(PayRecordInfo payRecordInfo);

    /**
     * @param orderNo 支付订单号
     * @return PayRecordInfo
     */
    PayRecordInfo queryById(String orderNo);
}
