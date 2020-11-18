package com.edaochina.shopping.api.facade.pay;


import com.edaochina.shopping.domain.dto.order.InitiateTeamDTO;
import com.edaochina.shopping.domain.dto.order.OrderPayDTO;
import com.edaochina.shopping.domain.dto.order.ShoppingPayDTO;
import com.edaochina.shopping.domain.dto.order.UserMemberPayDTO;
import com.github.binarywang.wxpay.exception.WxPayException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface PayFacade {

    int appPayCallBackV2(OrderPayDTO orderPayDTO);

    Map<String, String> merchantMemberPay(OrderPayDTO dto);

    String notifyWxPay(Map<String, String> params);

    Map<String, String> seckillOrderPay(InitiateTeamDTO initiateTeamDTO);

    /**
     * 微信秒杀商品支付回调处理类
     *
     * @param params
     * @return
     */
    String notifySeckillWxPay(Map<String, String> params);

    @Transactional(rollbackFor = Exception.class)
    Map<String, String> seckillOrderPayV2(InitiateTeamDTO initiateTeamDTO);

    String notifySeckillWxPayV2(Map<String, String> params);

    Map<String, String> shoppingCartPay(ShoppingPayDTO shoppingPayDTO);

    String notifyShoppingWxPay(Map<String, String> params);

    int appPayCallBack(OrderPayDTO orderPayDTO);

    Map<String, String> userMemberPay(UserMemberPayDTO dto);

    String notifyUserMemberPay(String xmlData) throws WxPayException;

    /**
     * 退款回调处理
     *
     * @param params
     * @return
     */
    String refundCallBack(Map<String, String> params);
}
