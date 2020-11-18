package com.edaochina.shopping.api.facade.order.impl;

import com.alibaba.fastjson.JSON;
import com.edaochina.shopping.api.facade.order.AppOrderFacade;
import com.edaochina.shopping.api.service.goods.AppGoodsService;
import com.edaochina.shopping.api.service.goods.GoodsCollectInfoService;
import com.edaochina.shopping.api.service.goods.GoodsImgsService;
import com.edaochina.shopping.api.service.order.AppOrderService;
import com.edaochina.shopping.api.service.order.MemberOrderService;
import com.edaochina.shopping.api.service.order.OrderErrorBackService;
import com.edaochina.shopping.api.service.order.ShoppingCartInfoService;
import com.edaochina.shopping.api.service.sys.SysPayRefundApplyService;
import com.edaochina.shopping.api.service.trade.PayRecordInfoService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.api.service.user.UserService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.date.DateUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.*;
import com.edaochina.shopping.domain.dto.order.OrderPayDTO;
import com.edaochina.shopping.domain.dto.order.QueryAppOrderDTO;
import com.edaochina.shopping.domain.dto.order.QueryMerchantOrderDTO;
import com.edaochina.shopping.domain.dto.order.WriteOffDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.order.MemberOrder;
import com.edaochina.shopping.domain.entity.order.OrderErrorBack;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRecordInfo;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.goods.GoodsImgsVO;
import com.edaochina.shopping.domain.vo.order.AppMerchantOrderVo;
import com.edaochina.shopping.domain.vo.order.AppOrderDetailVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service("appOrderFacadeImpl")
public class AppOrderFacadeImpl implements AppOrderFacade {

    private Logger logger = LoggerFactory.getLogger(AppOrderFacadeImpl.class);

    @Autowired
    private AppOrderService appOrderService;

    @Autowired
    private AppGoodsService appGoodsService;

    @Autowired
    private GoodsImgsService goodsImgsService;

    @Autowired
    private PayRecordInfoService payRecordInfoService;

    @Autowired
    private MemberOrderService memberOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartInfoService shoppingCartInfoService;

    @Autowired
    private OrderErrorBackService orderErrorBackService;

    @Autowired
    private GoodsCollectInfoService goodsCollectInfoService;

    private final SysPayRefundApplyService payRefundApplyService;
    private final SysUserMerchantService sysUserMerchantService;

    public AppOrderFacadeImpl(SysPayRefundApplyService payRefundApplyService, SysUserMerchantService sysUserMerchantService) {
        this.payRefundApplyService = payRefundApplyService;
        this.sysUserMerchantService = sysUserMerchantService;
    }

    /**
     * 取消支付
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelPay(OrderPayDTO orderPayDTO) {
        logger.info("cancelPay req:" + JSON.toJSONString(orderPayDTO));
        PayRecordInfo recordInfo = payRecordInfoService.queryById(orderPayDTO.getOrderId());
        logger.info("recordInfo info :" + JSON.toJSONString(recordInfo));
        // 防止小程序异常调用
        if (0 != recordInfo.getPayStatus()) {
            logger.info("cancelPay recordeInfo has handler");
            return true;
        }
        if (recordInfo.getPayType().equals(OrderTypeConstants.SECKILL_ORDER.getCode())) {
            MemberOrder memberOrder = memberOrderService.selectMemberOrderByOutOrderNo(orderPayDTO.getOrderId());
            if (memberOrder != null) {
                userService.updateUserMemberType(memberOrder.getUserId(), CommonConstants.ZERO_STR, "", "");
            }
        } else if (recordInfo.getPayType().equals(OrderTypeConstants.MERCHANT_MEMBER_ORDER.getCode())) {
            MemberOrder memberOrder = memberOrderService.selectMemberOrderByOutOrderNo(orderPayDTO.getOrderId());
            if (memberOrder != null) {
                //  更新商家会员信息
                SysUserMerchant merchant = new SysUserMerchant();
                merchant.setId(memberOrder.getUserId());
                merchant.setMemberType(0);
                sysUserMerchantService.updateById(merchant);
            }
        } else if (recordInfo.getPayType().equals(OrderTypeConstants.SHOPING_CART_ORDER.getCode())) {
            // 还原购物车信息
            shoppingCartInfoService.restoreShoppingCart(orderPayDTO);
        } else if (recordInfo.getPayType().equals(OrderTypeConstants.USER_MEMBER_ORDER.getCode())) {
            MemberOrder memberOrder = memberOrderService.selectMemberOrderByOutOrderNo(orderPayDTO.getOrderId());
            if (memberOrder != null) {
                //  更新商家会员信息
                SysUser user = new SysUser();
                user.setId(memberOrder.getUserId());
                user.setMemberType(0);
                userService.updateById(user);
            }
        }
        appOrderService.updateOrderToDelByOrderNo(recordInfo.getId());
        List<OrderMain> orderMains = appOrderService.queryByOrderId(recordInfo.getId());
        orderMains.forEach(orderMain -> {
            // 回退剩余商品数量
            appGoodsService.updateGoodsSurplusNumAndOrderNum(orderMain.getGoodsId(), -orderMain.getGoodsNum(), -1);
            // 判断是否是拼团订单，如果是则减少拼团数量
            goodsCollectInfoService.addBuyNum(orderMain.getGoodsId(), -orderMain.getGoodsNum());
        });
        return true;
    }

    /**
     * 获取订单详情
     *
     * @param queryAppOrderDTO
     * @return
     */
    @Override
    public AppOrderDetailVO getOrderDetail(QueryAppOrderDTO queryAppOrderDTO) {
        logger.info("getOrderDetail req:" + queryAppOrderDTO);
        AppOrderDetailVO appOrderDetailVO = new AppOrderDetailVO();
        OrderMain order = appOrderService.getById(queryAppOrderDTO.getId());
        logger.info("order:" + order);
        if (order == null) {
            return appOrderDetailVO;
        }
        BeanUtils.copyProperties(order, appOrderDetailVO);
        appOrderDetailVO.setHasRefundFlag(order.getHasRefund());
        appOrderDetailVO.setShopPhone(sysUserMerchantService.getById(order.getShoperId()).getPhone());
        Goods goods = appGoodsService.getById(order.getGoodsId());
        logger.info("goods:" + goods);
        if (goods != null) {
            appOrderDetailVO.setGoodsUseCountDown(goods.getUseCountDown());
            // 设置最后有效使用时间
            appOrderDetailVO.setUseLastValidTime(getUseLastTime(goods, order.getCreateTime()));
            appOrderDetailVO.setGoodsSpec(goods.getGoodsSpec());
            // 订单中显示商品图片
            List<GoodsImgsVO> goodsImgs = goodsImgsService.getGoodsImgsList(order.getGoodsId());
            logger.info("GoodsImgsVO goodsImgs:" + JSON.toJSONString(goodsImgs));
            if (CollectionUtils.isNotEmpty(goodsImgs)) {
                appOrderDetailVO.setGoodsImg(goodsImgs.get(0).getImgUrl());
            }
            appOrderDetailVO.setCollectStatus(goods.getCollectFlag());
        }

        if (StringUtils.isNotEmpty(queryAppOrderDTO.getOrigin())) {
            OrderErrorBack orderErrorBack = orderErrorBackService.queryByOrderIdAndOrigin(queryAppOrderDTO.getId(), queryAppOrderDTO.getOrigin());
            appOrderDetailVO.setHasErrorFlag(orderErrorBack != null);
        }
        return appOrderDetailVO;
    }

    @Override
    public PageResult getMerchantOrders(QueryMerchantOrderDTO dto) {
        logger.info("getMerchantOrders req:" + JSON.toJSONString(dto));
        PageHelperUtils.setPageHelper(dto.getPages());
        List<AppMerchantOrderVo> merchantOrderVos = appOrderService.getMerchantOrders(dto);
        return PageHelperUtils.parseToPageResult(merchantOrderVos);
    }

    /**
     * 新版本的用户订单列表接口
     *
     * @param queryAppOrderDTO
     * @return
     */
    @Override
    public PageResult v2GetOrderList(QueryAppOrderDTO queryAppOrderDTO) {
        logger.info("v2GetOrderList req:" + JSON.toJSONString(queryAppOrderDTO));
        return appOrderService.v2GetOrderList(queryAppOrderDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean writeOff(WriteOffDTO dto) {
        logger.info("AppOrderFacadeImpl writeOff :" + JSON.toJSONString(dto));
        LocalDateTime now = LocalDateTime.now();
        OrderMain dbOrderMain = appOrderService.getById(dto.getId());
        return writeOff(dbOrderMain);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean writeOff(List<WriteOffDTO> dto) {
        logger.info("AppOrderFacadeImpl writeOff :" + JSON.toJSONString(dto));
        List<OrderMain> orderMains = dto.parallelStream().map(writeOffDTO -> appOrderService.getById(writeOffDTO.getId())).collect(Collectors.toList());
        if (orderMains.parallelStream().map(OrderMain::getShoperId).distinct().count() != 1) {
            throw new YidaoException("只能批量核销同一商家的订单!");
        }
        orderMains.parallelStream().forEach(this::writeOff);
        return true;
    }

    private boolean writeOff(OrderMain dbOrderMain) {
        LocalDateTime now = LocalDateTime.now();
        Goods goods = appGoodsService.getById(dbOrderMain.getGoodsId());
        if (CountDownConstants.DEFIND_TIME.toString().equals(goods.getUseCountDown().toString())) {
            if (goods.getWriteOffEndTime().isBefore(now)
                    || goods.getWriteOffStartTime().isAfter(now)) {
                throw new YidaoException(ReturnData.WRITE_OFF_TIME_NOT_VALID);
            }
        }
        OrderMain orderMain = new OrderMain();
        orderMain.setId(dbOrderMain.getId());
        orderMain.setWriteOffTime(now);
        orderMain.setUpdateTime(now);
        orderMain.setWriteOffStatus(1);
        orderMain.setStatus(OrderStatusConstants.WAIT_EVALUATE);
        try {
            if (RedisTool.lock(LockConstants.ORDER_REFUND_KEY + orderMain.getId())) {
                boolean hasRefund = appOrderService.selectOneById(orderMain.getId()).hasRefund();
                PayRefundApply payRefundApply = payRefundApplyService.selectByOrderIdAndInRefundStatus(orderMain.getId(),
                        Arrays.asList(PayRefundApplyStatus.RefundStatus.PROCESSING, PayRefundApplyStatus.RefundStatus.REJECTED, PayRefundApplyStatus.RefundStatus.TO_BE_REFUNDED));
                if (hasRefund && payRefundApply != null) {
                    payRefundApplyService.cancelApply(payRefundApply.getId());
                }
                return appOrderService.updateById(orderMain);
            }
            return false;
        } finally {
            RedisTool.unLock(LockConstants.ORDER_REFUND_KEY + orderMain.getId());
        }
    }


    public static LocalDateTime getUseLastTime(Goods goods, LocalDateTime orderTime) {
        // 使用倒计时（10自定义，20永久，30三个月，40一个月，50：7天）
        Integer useCountDown = goods.getUseCountDown();
        // 使用自定义时间（单位:小时）
        double useCountDownTime = goods.getUseCountDownTime().doubleValue();
        Date date = null;
        // 不是永久
        if (!CountDownConstants.FOREVER.toString().equals(useCountDown.toString())) {
            // 自定义
            if (CountDownConstants.DEFINE.toString().equals(useCountDown.toString())) {
                date = DateUtil.addMinutes(DateUtil.localDateTime2Date(orderTime), (int) (useCountDownTime * 60));
            }
            // 三个月
            if (CountDownConstants.THREE_MONTH.toString().equals(useCountDown.toString())) {
                date = DateUtil.addMonthFroDate(DateUtil.localDateTime2Date(orderTime), 3);
            }
            // 一个月
            if (CountDownConstants.ONE_MONTH.toString().equals(useCountDown.toString())) {
                date = DateUtil.addMonthFroDate(DateUtil.localDateTime2Date(orderTime), 1);

            }
            // 7天
            if (CountDownConstants.SEVEN_DAY.toString().equals(useCountDown.toString())) {
                date = DateUtil.addDayFroDate(DateUtil.localDateTime2Date(orderTime), 7);
            }
            // 自定义提货时间段
            if (CountDownConstants.DEFIND_TIME.toString().equals(useCountDown.toString())) {
                date = DateUtil.localDateTime2Date(goods.getWriteOffEndTime());
            }
        } else {
            date = DateUtil.getForeverTime();
        }
        return DateUtil.date2LocalDateTime(date);
    }
}
