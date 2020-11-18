package com.edaochina.shopping.api.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.service.goods.AppGoodsService;
import com.edaochina.shopping.api.service.order.AppOrderService;
import com.edaochina.shopping.api.service.order.OrderErrorBackService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.AssertUtils;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.OrderStatusConstants;
import com.edaochina.shopping.domain.dto.order.AppCollectOrderDTO;
import com.edaochina.shopping.domain.dto.order.AppTakeGoodsDTO;
import com.edaochina.shopping.domain.dto.order.QueryAppOrderDTO;
import com.edaochina.shopping.domain.dto.order.QueryMerchantOrderDTO;
import com.edaochina.shopping.domain.entity.order.OrderErrorBack;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Service
public class AppOrderServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements AppOrderService {
    @Resource
    OrderMainMapper orderMainMapper;

    @Resource
    SysUserMerchantService sysUserMerchantService;

    @Autowired
    private AppGoodsService appGoodsService;

    @Autowired
    private OrderErrorBackService orderErrorBackService;


    @Override
    public List<OrderMain> queryByOrderId(String orderId) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        return orderMainMapper.selectList(wrapper);
    }

    @Override
    public OrderMain selectOneById(String orderId) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId);
        return orderMainMapper.selectOne(wrapper);
    }

    @Override
    public int queryHasBuyNum(String userId, String goodsId, Date startOfDay, Date endOfDay) {
        Integer num = orderMainMapper.queryHasBuyNum(userId, goodsId, startOfDay, endOfDay);
        return num == null ? 0 : num;
    }

    @Override
    public AppUserOrderCountVO queryUserOrderCount(AppTakeGoodsDTO dto) {
        if (dto.getUserId() == null || dto.getUserId() == "") {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        if (dto.getShoperId() == null || dto.getShoperId() == "") {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        AppUserOrderCountVO appUserOrderCountVO = orderMainMapper.queryUserOrderCount(dto);
        SysUserMerchant userMerchant = sysUserMerchantService.getById(dto.getShoperId());
        AssertUtils.checkNotNull(userMerchant, "商家不存在");
        appUserOrderCountVO.setShopName(userMerchant.getTitle());
        return appUserOrderCountVO;
    }

    @Override
    public PageResult<AppTakeGoodsVO> queryUserOrderList(AppTakeGoodsDTO dto) {
        if (dto.getUserId() == null || dto.getUserId() == "") {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        if (dto.getShoperId() == null || dto.getShoperId() == "") {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        PageHelperUtils.setPageHelper(dto.getPages());
        List<AppTakeGoodsVO> takeGoodsVOS = orderMainMapper.queryUserOrderList(dto);
        return PageHelperUtils.parseToPageResult(takeGoodsVOS);
    }

    @Override
    public List<AppMerchantOrderVo> getMerchantOrders(QueryMerchantOrderDTO dto) {
        return orderMainMapper.getMerchantOrders(dto);
    }

    /**
     * 通过支付单号将订单更新为失败
     *
     * @param id
     * @return
     */
    @Override
    public boolean updateOrderToDelByOrderNo(String id) {
        return orderMainMapper.updateOrderStatusByOrderNo(id, OrderStatusConstants.PAY_FAIL) > 0;
    }

    @Override
    public PageResult v2GetOrderList(QueryAppOrderDTO queryAppOrderDTO) {
        PageHelperUtils.setPageHelper(queryAppOrderDTO.getPages());
        List<AppTakeGoodsVO> appTakeGoods = orderMainMapper.queryUserOrders(queryAppOrderDTO);
        return PageHelperUtils.parseToPageResult(appTakeGoods);
    }

    @Override
    public int getTodayWriteOffCount(String merchantId) {
        return orderMainMapper.getTodayWriteOffCount(merchantId);
    }

    /**
     * 将订单处理为失败
     *
     * @param dbOrderMain   订单信息
     * @param payReturnInfo 回调信息
     */
    @Override
    public void handleOrderToPayFail(OrderMain dbOrderMain, String payReturnInfo) {
        OrderMain orderMain = new OrderMain();
        // orderMain.setReturnPayInfo(payReturnInfo);
        orderMain.setId(dbOrderMain.getId());
        orderMain.setStatus(OrderStatusConstants.PAY_FAIL);
        this.updateById(orderMain);
        // 将商品数量加上去
        appGoodsService.updateGoodsSurplusNumAndOrderNum(dbOrderMain.getGoodsId(), -dbOrderMain.getGoodsNum(), -1);
    }

    @Override
    public int waitTakeGoodsCount(String userId) {
        return orderMainMapper.waitTakeGoodsCount(userId);
    }

    @Override
    public List<String> getUserCollectUserImg(String goodsId) {
        return orderMainMapper.getUserCollectUserImg(goodsId);
    }

    @Override
    public Map<String, Object> getCollectOrderList(String goodsId) {
        Map<String, Object> result = new HashMap<>(3);
        List<AppCollectOrderSimpleInfo> orderSimpleInfos = orderMainMapper.queryCollectOrderList(goodsId);
        Integer sum = orderMainMapper.getCollectOrderSumByGoodsId(goodsId);
        Integer orderNum = orderMainMapper.getCollectOrderNumByGoodsId(goodsId);
        result.put("sum", sum == null ? 0 : sum);
        result.put("orderNum", orderNum == null ? 0 : orderNum);
        result.put("list", orderSimpleInfos);
        return result;
    }

    @Override
    public int refundOrderCount(String userId) {
        return orderMainMapper.refundOrderCount(userId);
    }

    @Override
    public PageResult queryUserCollectOrder(AppCollectOrderDTO dto) {
        PageHelperUtils.setPageHelper(dto.getPages());
        List<AppCollectOrderVO> collectOrderVOS = orderMainMapper.queryUserCollectOrder(dto);
        return PageHelperUtils.parseToPageResult(collectOrderVOS);
    }

    @Override
    public AppCollectOrderDetailVO queryCollectOrderDetail(String orderId) {
        if (orderId == null) {
            throw new YidaoException("订单id不能为空!");
        }
        AppCollectOrderDetailVO appCollectOrderDetailVO = orderMainMapper.queryCollectOrderDetail(orderId);
        Integer sortNum = orderMainMapper.queryBeforeThisCollectOrderNum(orderId);

        OrderErrorBack orderErrorBack = orderErrorBackService.queryByOrderIdAndOrigin(orderId, "1");
        appCollectOrderDetailVO.setHasErrorFlag(orderErrorBack != null);
        appCollectOrderDetailVO.setSortNum(sortNum + 1);
        return appCollectOrderDetailVO;
    }
}