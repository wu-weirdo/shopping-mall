package com.edaochina.shopping.api.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.order.ApprovalRecord;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface ApprovalRecordService extends IService<ApprovalRecord> {

    List<ApprovalRecord> getApprovalRecordListForTask();

    ApprovalRecord getLastAcceptApprovalRecord(String goodsId);
}
