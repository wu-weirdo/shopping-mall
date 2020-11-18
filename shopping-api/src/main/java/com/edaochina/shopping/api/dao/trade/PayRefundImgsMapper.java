package com.edaochina.shopping.api.dao.trade;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.trade.PayRefundImgs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 退款申请图片凭证表 Mapper 接口
 * </p>
 *
 * @since 2019-05-22
 */
@Repository
public interface PayRefundImgsMapper extends BaseMapper<PayRefundImgs> {

    int insertImg(PayRefundImgs payRefundImgs);

    List<String> selectByRefundApplyId(@Param("refundApplyId") Integer refundApplyId);

}
