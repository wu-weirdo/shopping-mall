package com.edaochina.shopping.api.facade.order;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.goods.DeleteOrdersDTO;
import com.edaochina.shopping.domain.dto.order.QuerySysOrderDTO;
import com.edaochina.shopping.domain.dto.order.QuerySysWriteOffOrderDTO;
import com.edaochina.shopping.domain.dto.order.SysOrderDto;
import com.edaochina.shopping.domain.dto.order.WriteOffDTO;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.vo.order.ShareOrderDetail;
import com.edaochina.shopping.domain.vo.order.SysOrderDetailVO;
import com.edaochina.shopping.domain.vo.trade.SysShareProfitVo;

public interface SysOrderFacade extends QueryShopIdsByCommunity {

    boolean deleteOrders(DeleteOrdersDTO deleteOrdersDTO);

    /**
     * 退款
     *
     * @param payRefundApply 退款信息
     * @return 新增退款记录数
     */
    int refund(PayRefundApply payRefundApply);

    boolean writeOff(WriteOffDTO writeOffDTO);

    PageResult getOrderList(SysOrderDto querySysOrderDTO);

    PageResult getWriteOffList(QuerySysWriteOffOrderDTO querySysOrderDTO);

    SysOrderDetailVO getOrderDetail(QuerySysOrderDTO querySysOrderDTO);

    boolean updateCollectOrderStatusForTask();

    String exportOrder(SysOrderDto exportSysOrderDTO);

    PageResult<SysShareProfitVo> getShareOrder(SysShareGoodsProfitDTO dto);

    ShareOrderDetail getShareOrderDetail(String id);
}
