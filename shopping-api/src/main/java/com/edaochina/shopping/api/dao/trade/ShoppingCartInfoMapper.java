package com.edaochina.shopping.api.dao.trade;


import com.edaochina.shopping.domain.dto.order.OrderPayDTO;
import com.edaochina.shopping.domain.dto.trade.DelCartListDTO;
import com.edaochina.shopping.domain.dto.trade.ShoppingCartDTO;
import com.edaochina.shopping.domain.entity.trade.ShoppingCartInfo;
import com.edaochina.shopping.domain.vo.trade.ShoppingCartInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ShoppingCartInfoMapper {

    int addShoppingCart(ShoppingCartInfo shoppingCartInfo);

    int updateShoppingCart(ShoppingCartInfo shoppingCartInfo);

    int updateCartDelStatus(@Param("id") Integer id, @Param("deleteFlag") Integer deleteFlag);

    int updateShoppingCartList(@Param("shoppingCarts") List<ShoppingCartInfo> shoppingCarts);

    int delShoppingCart(Integer id);

    List<ShoppingCartInfoVo> queryUserShoppingCart(@Param("userId") String userId, @Param("longitude") String longitude, @Param("latitude") String latitude);

    List<ShoppingCartInfoVo> v2QueryUserShoppingCart(ShoppingCartDTO dto);

    ShoppingCartInfo queryByUserIdAndGoodId(@Param("userId") String userId, @Param("goodsId") String goodsId);

    ShoppingCartInfo queryById(Integer id);

    int cleanOverTimeShoppingCart();

    int removeAllShoppingCarts(@Param("userId") String userId);

    int restoreShoppingCart(OrderPayDTO orderPayDTO);

    int shoppingCartCount(@Param("userId") String userId);

    Integer validCartCount(@Param("userId") String userId);

    int deleteCartList(DelCartListDTO dto);

    int deleteInvalidCart(@Param("userId") String userId);
}
