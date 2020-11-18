package com.edaochina.shopping.api.facade.order;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.order.ApprovalRecordDTO;

public interface ApprovalRecordFacade extends QueryShopIdsByCommunity {

    PageResult getApprovalRecordList(ApprovalRecordDTO approvalRecordDTO);

}
