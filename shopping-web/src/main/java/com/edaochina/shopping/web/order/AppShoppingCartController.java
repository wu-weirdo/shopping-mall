package com.edaochina.shopping.web.order;

import com.edaochina.shopping.api.service.order.ShoppingCartInfoService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.trade.DelCartListDTO;
import com.edaochina.shopping.domain.dto.trade.ShoppingCartDTO;
import com.edaochina.shopping.domain.entity.trade.ShoppingCartInfo;
import com.edaochina.shopping.domain.entity.trade.UpdateShoppingCartInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/cart")
@Api(tags = "购物车")
public class AppShoppingCartController {

    @Resource
    ShoppingCartInfoService shoppingCartInfoService;

    @RequestMapping("/addCart")
    public AjaxResult addCart(@RequestBody ShoppingCartInfo shoppingCartInfo) {
        return BaseResult.successResult(shoppingCartInfoService.insertShoppingCart(shoppingCartInfo));
    }

    @RequestMapping("/delCart")
    public AjaxResult delCart(Integer id) {
        return BaseResult.successResult(shoppingCartInfoService.delShoppingCart(id));
    }

    @RequestMapping("/updateCart")
    public AjaxResult updateCart(@RequestBody ShoppingCartInfo shoppingCartInfo) {
        return BaseResult.successResult(shoppingCartInfoService.updateShoppingCart(shoppingCartInfo));
    }

    @RequestMapping("/cartList")
    public AjaxResult cartList(String userId, String longitude, String latitude) {
        return BaseResult.successResult(shoppingCartInfoService.queryUserShoppingCart(userId, longitude, latitude));
    }

    @RequestMapping("/v2/cartList")
    public AjaxResult v2CartList(ShoppingCartDTO dto) {
        return BaseResult.successResult(shoppingCartInfoService.v2QueryUserShoppingCart(dto));
    }

    @RequestMapping("updateShoppingCarts")
    public AjaxResult updateShoppingCartList(@RequestBody UpdateShoppingCartInfo updateShoppingCartInfo) {
        return BaseResult.successResult(shoppingCartInfoService.updateShoppingCartList(updateShoppingCartInfo));
    }

    /**
     * 清空失效商品
     *
     * @param userId
     * @return
     */
    @RequestMapping("/delInvalidCart")
    public AjaxResult delInvalidCart(String userId) {
        return BaseResult.successResult(shoppingCartInfoService.deleteInvalidCart(userId));
    }

    /**
     * 有效购物车数
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getValidCartCount")
    public AjaxResult getValidCartCount(String userId) {
        return BaseResult.successResult(shoppingCartInfoService.validCartCount(userId));
    }

    /**
     * 批量删除购物车
     *
     * @param dto
     * @return
     */
    @RequestMapping("/delCartList")
    public AjaxResult delCartList(@RequestBody DelCartListDTO dto) {
        return BaseResult.successResult(shoppingCartInfoService.deleteCartList(dto));
    }
}
