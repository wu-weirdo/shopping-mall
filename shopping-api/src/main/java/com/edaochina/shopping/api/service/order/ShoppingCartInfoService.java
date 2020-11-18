package com.edaochina.shopping.api.service.order;

import com.edaochina.shopping.domain.dto.order.OrderPayDTO;
import com.edaochina.shopping.domain.dto.trade.DelCartListDTO;
import com.edaochina.shopping.domain.dto.trade.ShoppingCartDTO;
import com.edaochina.shopping.domain.entity.trade.ShoppingCartInfo;
import com.edaochina.shopping.domain.entity.trade.UpdateShoppingCartInfo;
import com.edaochina.shopping.domain.vo.trade.AppShoppingCartInfo;

import java.util.Collection;
import java.util.List;

public interface ShoppingCartInfoService {
    boolean insertShoppingCart(ShoppingCartInfo shoppingCartInfo);

    int delShoppingCart(Integer id);

    int updateShoppingCartStatus(Integer id, Integer deleteFlag);

    int updateShoppingCart(ShoppingCartInfo shoppingCartInfo);

    Collection<AppShoppingCartInfo> queryUserShoppingCart(String userId, String longitude, String latitude);

    List<AppShoppingCartInfo> v2QueryUserShoppingCart(ShoppingCartDTO dto);

    ShoppingCartInfo queryById(Integer shoppingCartId);

    void cleanOverTimeShoppingCart();

    int updateShoppingCartList(UpdateShoppingCartInfo updateShoppingCartInfo);

    int restoreShoppingCart(OrderPayDTO orderPayDTO);

    int deleteInvalidCart(String userId);

    int validCartCount(String userId);

    int deleteCartList(DelCartListDTO dto);
}
