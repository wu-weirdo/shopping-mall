package com.edaochina.shopping.api.dao.order;

import com.edaochina.shopping.domain.dto.order.QueryOrderErrorBackDTO;
import com.edaochina.shopping.domain.dto.order.UpdOrderErrorDTO;
import com.edaochina.shopping.domain.entity.order.OrderErrorBack;
import com.edaochina.shopping.domain.vo.order.OrderErrorBackVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 异常订单反馈表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
public interface OrderErrorBackMapper {

    List<OrderErrorBackVO> orderErrorList(QueryOrderErrorBackDTO dto);

    int updateOrderError(UpdOrderErrorDTO dto);

    int orderErrorCount(QueryOrderErrorBackDTO dto);

    OrderErrorBack selectByOrderId(String orderId);

    int saveToDb(OrderErrorBack orderErrorBack);

    OrderErrorBackVO selectById(Integer id);

    OrderErrorBack queryByOrderIdAndOrigin(@Param("orderId") String orderId, @Param("origin") String origin);
}
