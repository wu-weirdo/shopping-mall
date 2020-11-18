package com.edaochina.shopping.api.facade.order;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.order.OrderPayDTO;
import com.edaochina.shopping.domain.dto.order.QueryAppOrderDTO;
import com.edaochina.shopping.domain.dto.order.QueryMerchantOrderDTO;
import com.edaochina.shopping.domain.dto.order.WriteOffDTO;
import com.edaochina.shopping.domain.vo.order.AppOrderDetailVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppOrderFacade {

    boolean cancelPay(OrderPayDTO orderPayDTO);

    AppOrderDetailVO getOrderDetail(QueryAppOrderDTO queryAppOrderDTO);

    boolean writeOff(WriteOffDTO dto);

    PageResult getMerchantOrders(QueryMerchantOrderDTO dto);

    PageResult v2GetOrderList(QueryAppOrderDTO queryAppOrderDTO);

    @Transactional(rollbackFor = Exception.class)
    boolean writeOff(List<WriteOffDTO> dto);
}
