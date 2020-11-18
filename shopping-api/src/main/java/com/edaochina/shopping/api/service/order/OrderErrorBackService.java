package com.edaochina.shopping.api.service.order;


import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.order.OrderErrorBackDTO;
import com.edaochina.shopping.domain.dto.order.QueryOrderErrorBackDTO;
import com.edaochina.shopping.domain.dto.order.UpdOrderErrorDTO;
import com.edaochina.shopping.domain.entity.order.OrderErrorBack;
import com.edaochina.shopping.domain.vo.order.OrderErrorBackVO;


/**
 * <p>
 * 异常订单反馈 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
public interface OrderErrorBackService {

    int insertOrderError(OrderErrorBackDTO dto);

    PageResult<OrderErrorBackVO> orderErrorBackList(QueryOrderErrorBackDTO dto);

    int updateOrderErrorBack(UpdOrderErrorDTO dto);

    OrderErrorBackVO selectOrderErrorById(Integer id);

    OrderErrorBack queryByOrderIdAndOrigin(String orderId, String origin);
}
