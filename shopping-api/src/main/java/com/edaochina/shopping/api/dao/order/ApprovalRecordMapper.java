package com.edaochina.shopping.api.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.order.ApprovalRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface ApprovalRecordMapper extends BaseMapper<ApprovalRecord> {

    ApprovalRecord getLastAcceptApprovalRecord(@Param("goodsId") String goodsId);
}
