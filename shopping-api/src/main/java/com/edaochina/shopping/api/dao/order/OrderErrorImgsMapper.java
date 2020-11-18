package com.edaochina.shopping.api.dao.order;


import com.edaochina.shopping.domain.entity.order.OrderErrorImgs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 异常订单图片凭证 Mapper 接口
 * </p>
 *
 * @since 2019-05-22
 */
public interface OrderErrorImgsMapper {

    int insertImg(OrderErrorImgs errorImgs);

    List<String> selectByOrderErrorId(@Param("orderErrorId") Integer orderErrorId);
}
