package com.edaochina.shopping.api.service.pay;

import com.edaochina.shopping.api.service.goods.AppGoodsService;
import com.edaochina.shopping.api.service.order.AppOrderService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.date.DateUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.constants.ApprovalFlagConstants;
import com.edaochina.shopping.domain.constants.OrderStatusConstants;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jintian
 * @date 2019/4/17 11:44
 */
@Service
public class OrderPayService {

    private final AppOrderService appOrderService;
    private final AppGoodsService appGoodsService;
    private final SysUserMerchantService sysUserMerchantService;
    private Logger logger = LoggerFactory.getLogger(OrderPayService.class);

    public OrderPayService(AppOrderService appOrderService, AppGoodsService appGoodsService, SysUserMerchantService sysUserMerchantService) {
        this.appOrderService = appOrderService;
        this.appGoodsService = appGoodsService;
        this.sysUserMerchantService = sysUserMerchantService;
    }

    public OrderMain orderHandler(SysUser sysUser, String goodsId, int num) {
        // 获取商品信息
        Goods goods = orderHandlerCheckGoods(goodsId);
        // 校验订单
        checkOrder(goods, sysUser, num);
        // 获取商家信息
        SysUserMerchant sysUserMerchant = sysUserMerchantService.getById(goods.getShopId());
        // 生成订单
        OrderMain orderMain = createOrderByUserPay(goods, num, sysUserMerchant, sysUser);
        // 更新商品数量
        if (appGoodsService.updateGoodsSurplusNumAndOrderNum(goods.getId(), num, 1) <= 0) {
            throw new YidaoException(ReturnData.OVER_GOODS_NUM.getCode(), ReturnData.OVER_GOODS_NUM.getMsg());
        }
        return orderMain;
    }

    /**
     * 更加用户支付信息生成订单
     *
     * @param goods
     * @param num
     * @param sysUserMerchant
     * @return
     */
    private OrderMain createOrderByUserPay(Goods goods, int num, SysUserMerchant sysUserMerchant, SysUser sysUser) {
        BigDecimal actualPrice = BigDecimal.valueOf(goods.getGoodsPrice().doubleValue() * num)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        return createOrderByUserPay(goods, num, sysUserMerchant, sysUser, actualPrice);
    }

    /**
     * 更加用户支付信息生成订单
     *
     * @param goods
     * @param num
     * @param sysUserMerchant
     * @return
     */
    private OrderMain createOrderByUserPay(Goods goods, int num, SysUserMerchant sysUserMerchant, SysUser sysUser, BigDecimal actualPrice) {
        OrderMain orderMain = new OrderMain();
        orderMain.setGoodsId(goods.getId());
        orderMain.setUserId(sysUser.getId());
        // 初始化id值
        String orderId = IdUtils.nextId();
        orderMain.setId(orderId);
        orderMain.setOrderNo(orderId);
        orderMain.setPhone(sysUser.getPhone());
        logger.info("goods id " + goods.getId() + ",goods price:" + goods.getGoodsPrice() + ",goods costPrice:" + goods.getGoodsCostPrice());
        // 获取成本价
        double costPrice = goods.getGoodsCostPrice().doubleValue() * num;
        orderMain.setActualPrice(actualPrice);
        orderMain.setTotalPrice(actualPrice);
        orderMain.setCostPrice(new BigDecimal(costPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
        orderMain.setShoperId(goods.getShopId());
        orderMain.setShoperName(sysUserMerchant.getTitle());
        orderMain.setShoperAddress(sysUserMerchant.getAddress());
        orderMain.setGoodsNum(num);
        // 初始化状态
        orderMain.setStatus(OrderStatusConstants.WAIT_PAY);
        // 额外信息
        orderMain.setGoodsName(goods.getGoodsName());
        orderMain.setCreateTime(LocalDateTime.now());
        orderMain.setUpdateTime(LocalDateTime.now());
        return orderMain;
    }

    public OrderMain orderHandler(SysUser sysUser, String goodsId, int num, BigDecimal actualPrice) {
        // 获取商品信息
        Goods goods = orderHandlerCheckGoods(goodsId);
        // 校验订单
        checkOrder(goods, sysUser, num);
        // 获取商家信息
        SysUserMerchant sysUserMerchant = sysUserMerchantService.getById(goods.getShopId());
        // 生成订单
        OrderMain orderMain = createOrderByUserPay(goods, num, sysUserMerchant, sysUser, actualPrice);
        // 更新商品数量
        if (appGoodsService.updateGoodsSurplusNumAndOrderNum(goods.getId(), num, 1) <= 0) {
            throw new YidaoException(ReturnData.OVER_GOODS_NUM.getCode(), ReturnData.OVER_GOODS_NUM.getMsg());
        }
        return orderMain;
    }

    private Goods orderHandlerCheckGoods(String goodsId) {
        Goods goods = appGoodsService.getById(goodsId);
        logger.info("orderHandler goods:" + (goods == null));
        if (goods == null) {
            throw new YidaoException(ReturnData.PAY_GOODS_IS_NOT_EXIT.getCode(), ReturnData.PAY_GOODS_IS_NOT_EXIT.getMsg());
        }
        return goods;
    }

    /**
     * 检查订单请求
     *
     * @param goods
     * @param sysUser
     * @param num
     */
    private void checkOrder(Goods goods, SysUser sysUser, int num) {
        logger.info("checkOrder goods deleteFlag :" + goods.getDeleteFlag());
        // 检查商品是否下架
        if (goods.getDeleteFlag() == 20 || goods.getPutawayStatus() == 20) {
            throw new YidaoException(ReturnData.GOODS_HAS_DEL.getCode(), ReturnData.GOODS_HAS_DEL.getMsg());
        }
        logger.info("checkOrder goods lastValidTime :" + goods.getLastValidTime());
        // 检查商品上下架时间
        if (goods.getLastValidTime().isBefore(DateUtil.date2LocalDateTime(new Date())) || DateUtil.date2LocalDateTime(new Date()).isBefore(goods.getPutawayTime())) {
            throw new YidaoException(ReturnData.GOODS_HAS_DEL.getCode(), ReturnData.GOODS_HAS_DEL.getMsg());
        }
        logger.info("inventory :" + goods.getGoodsSurplusNum() + ",pay num:" + num);
        // 检查商品库存
        if (num > goods.getGoodsSurplusNum()) {
            throw new YidaoException(ReturnData.OVER_GOODS_NUM.getCode(), goods.getGoodsName() + ReturnData.OVER_GOODS_NUM.getMsg());
        }
        if (!goods.getApprovalFlag().equals(ApprovalFlagConstants.ACCEPT)) {
            throw new YidaoException(ReturnData.GOODS_HAS_DEL.getCode(), ReturnData.GOODS_HAS_DEL.getMsg());
        }
        // 检查商品上下架状态
        if (goods.getPutawayStatus() == 20) {
            throw new YidaoException(ReturnData.GOODS_HAS_DEL);
        }
        // 检查商品限购数量
        // 当天限购
        logger.info("good limit flag:" + goods.getLimitBuyFlag());
        if (goods.getLimitBuyFlag() == 20) {
            int hasByNum = appOrderService.queryHasBuyNum(sysUser.getId(), goods.getId(), DateUtil.getStartOfDay(new Date()), DateUtil.getEndOfDay(new Date()));
            logger.info("hasBuyNum:" + hasByNum + ",buyNum:" + num + ",limitBuyNum:" + goods.getLimitBuyNum());
            if (hasByNum + num > goods.getLimitBuyNum()) {
                throw new YidaoException(ReturnData.LIMIT_BUY_NUM.getCode(), goods.getGoodsName() + ReturnData.LIMIT_BUY_NUM.getMsg());
            }
            // 永久限购
        } else if (goods.getLimitBuyFlag() == 30) {
            int hasByNum = appOrderService.queryHasBuyNum(sysUser.getId(), goods.getId(), null, null);
            logger.info("hasBuyNum:" + hasByNum + ",buyNum:" + num + ",limitBuyNum:" + goods.getLimitBuyNum());
            if (hasByNum + num > goods.getLimitBuyNum()) {
                throw new YidaoException(ReturnData.LIMIT_BUY_NUM.getCode(), goods.getGoodsName() + ReturnData.LIMIT_BUY_NUM.getMsg());
            }
        }

    }
}
