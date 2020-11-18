package com.edaochina.shopping.api.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.order.AppCollectOrderDTO;
import com.edaochina.shopping.domain.dto.order.AppTakeGoodsDTO;
import com.edaochina.shopping.domain.dto.order.QueryAppOrderDTO;
import com.edaochina.shopping.domain.dto.order.QueryMerchantOrderDTO;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.vo.order.AppCollectOrderDetailVO;
import com.edaochina.shopping.domain.vo.order.AppMerchantOrderVo;
import com.edaochina.shopping.domain.vo.order.AppTakeGoodsVO;
import com.edaochina.shopping.domain.vo.order.AppUserOrderCountVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface AppOrderService extends IService<OrderMain> {

    List<OrderMain> queryByOrderId(String orderId);

    OrderMain selectOneById(String orderId);

    int queryHasBuyNum(String userId, String goodsId, Date startOfDay, Date endOfDay);

    AppUserOrderCountVO queryUserOrderCount(AppTakeGoodsDTO dto);

    PageResult<AppTakeGoodsVO> queryUserOrderList(AppTakeGoodsDTO dto);

    List<AppMerchantOrderVo> getMerchantOrders(QueryMerchantOrderDTO dto);

    boolean updateOrderToDelByOrderNo(String id);

    PageResult v2GetOrderList(QueryAppOrderDTO queryAppOrderDTO);

    int getTodayWriteOffCount(String merchantId);

    void handleOrderToPayFail(OrderMain orderId, String payReturnInfo);

    int waitTakeGoodsCount(String userId);

    List<String> getUserCollectUserImg(String goodsId);

    Map<String, Object> getCollectOrderList(String goodsId);

    int refundOrderCount(String userId);

    PageResult queryUserCollectOrder(AppCollectOrderDTO dto);

    AppCollectOrderDetailVO queryCollectOrderDetail(String orderId);
}
