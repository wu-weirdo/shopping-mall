package com.edaochina.shopping.api.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.order.*;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.vo.order.*;
import com.edaochina.shopping.domain.vo.trade.SysShareProfitVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Repository
public interface OrderMainMapper extends BaseMapper<OrderMain> {

    List<ExportSysOrderVO> getExportOrderList(SysOrderDto exportSysOrderDTO);

    Integer getExportOrderCount(SysOrderDto exportSysOrderDTO);

    Integer queryHasBuyNum(@Param("userId") String userId, @Param("goodsId") String goodsId, @Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);

    AppUserOrderCountVO queryUserOrderCount(AppTakeGoodsDTO dto);

    List<AppTakeGoodsVO> queryUserOrderList(AppTakeGoodsDTO dto);

    List<AppMerchantOrderVo> getMerchantOrders(QueryMerchantOrderDTO dto);

    List<AppTakeGoodsVO> queryUserOrders(QueryAppOrderDTO queryAppOrderDTO);

    int getTodayWriteOffCount(@Param("merchantId") String merchantId);

    int updateOrderStatusByOrderNo(@Param("orderNo") String orderNo, @Param("status") Integer status);

    List<SysOrderVO> sysQueryOrders(SysOrderDto querySysOrderDTO);

    int waitTakeGoodsCount(@Param("userId") String userId);

    List<OrderMain> queryHasNotCalcOrder();

    List<OrderMain> queryUnUsedOrder();

    List<String> getUserCollectUserImg(@Param("goodsId") String goodsId);

    List<AppCollectOrderSimpleInfo> queryCollectOrderList(String goodsId);

    Integer getCollectOrderSumByGoodsId(String goodsId);

    Integer getCollectOrderNumByGoodsId(String goodsId);

    int updateOrderRefundStatus(@Param("orderId") String orderId, @Param("refundStatus") Integer refundStatus);

    int refundOrderCount(@Param("userId") String userId);

    List<AppCollectOrderVO> queryUserCollectOrder(AppCollectOrderDTO dto);

    AppCollectOrderDetailVO queryCollectOrderDetail(String orderId);

    int updateOrderToCollectSuccess(Integer id);

    List<OrderMain> queryByCollectInfo(Integer collectId);

    Integer queryBeforeThisCollectOrderNum(String orderId);

    List<OrderMain> getWriteOffList(QuerySysWriteOffOrderDTO querySysOrderDTO);

    List<SysShareProfitVo> getShareOrder(SysShareGoodsProfitDTO dto);
}
