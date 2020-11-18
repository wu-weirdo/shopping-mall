package com.edaochina.shopping.api.dao.trade;

import com.edaochina.shopping.domain.entity.trade.PayRefundInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 退款记录表 Mapper 接口
 * </p>
 *
 * @since 2019-06-11
 */
public interface PayRefundInfoMapper {

    int insertRefundInfo(PayRefundInfo payRefundInfo);

    PayRefundInfo queryByOutTradeNo(String outRefundNo);

    /**
     * @param id
     * @param refundStatus
     * @return
     */
    int updateRefundStatus(@Param("id") Integer id, @Param("refundStatus") int refundStatus, @Param("callBackResult") String callBackResult);
}
