package com.edaochina.shopping.api.facade.pay.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.order.PayRefundApplyMapper;
import com.edaochina.shopping.api.dao.trade.PayRefundInfoMapper;
import com.edaochina.shopping.api.dao.user.AgentCountyInfoMapper;
import com.edaochina.shopping.api.dao.user.SysCommunityPartnerMapper;
import com.edaochina.shopping.api.facade.pay.PayFacade;
import com.edaochina.shopping.api.service.goods.AppGoodsService;
import com.edaochina.shopping.api.service.goods.GoodsCollectInfoService;
import com.edaochina.shopping.api.service.order.AppOrderService;
import com.edaochina.shopping.api.service.order.MemberOrderService;
import com.edaochina.shopping.api.service.order.ShoppingCartInfoService;
import com.edaochina.shopping.api.service.order.SysOrderService;
import com.edaochina.shopping.api.service.pay.OrderPayService;
import com.edaochina.shopping.api.service.pay.PayApi;
import com.edaochina.shopping.api.service.trade.PayRecordInfoService;
import com.edaochina.shopping.api.service.user.CommunityService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.api.service.user.UserService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.AssertUtils;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.date.DateUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.weixinpay.WxSignUtil;
import com.edaochina.shopping.common.wxpay.config.WxPayConfig;
import com.edaochina.shopping.common.wxpay.model.OrderInfo;
import com.edaochina.shopping.common.wxpay.util.WXHelp;
import com.edaochina.shopping.domain.constants.*;
import com.edaochina.shopping.domain.dto.order.*;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.goods.GoodsCollectInfo;
import com.edaochina.shopping.domain.entity.order.MemberOrder;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRecordInfo;
import com.edaochina.shopping.domain.entity.trade.PayRefundInfo;
import com.edaochina.shopping.domain.entity.trade.ShoppingCartInfo;
import com.edaochina.shopping.domain.entity.user.*;
import com.edaochina.shopping.domain.vo.sys.SysHasAgenAreaVo;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author 24h
 */
@Service("payFacadeImpl")
public class PayFacadeImpl implements PayFacade {

    private static String SUCCESS_FLAG = "SUCCESS";
    private static BigDecimal MEMBER_PRICE = BigDecimal.valueOf(9.9);
    private final OrderPayService orderPayService;
    private final UserService userService;
    private final SysOrderService sysOrderService;
    private final MemberOrderService memberOrderService;
    private final GoodsCollectInfoService goodsCollectInfoService;
    private final SysCommunityPartnerMapper sysCommunityPartnerMapper;
    private final CommunityService communityService;
    @Resource
    PayApi payApi;
    @Resource
    PayRecordInfoService payRecordInfoService;
    @Resource
    ShoppingCartInfoService shoppingCartInfoService;
    @Resource
    PayRefundApplyMapper refundApplyMapper;
    @Resource
    PayRefundInfoMapper payRefundInfoMapper;
    private Logger log = LoggerFactory.getLogger(PayFacadeImpl.class);
    @Value("${wx.pay-callBack-url}")
    private String callBackUrl;
    @Value("${wx.pay-seckill-callBack-url}")
    private String seckillOrderPayCallBackUrl;
    @Value("${wx.pay-shopping-callBack-url}")
    private String shoppingOrderPayCallBackUrl;
    @Value("${wx.pay-user-member-callBack-url}")
    private String userMemberPayCallBackUrl;
    @Resource
    private SysUserMerchantService sysUserMerchantService;
    @Resource
    private AppGoodsService appGoodsService;
    @Resource
    private AppOrderService appOrderService;
    @Resource
    private AgentCountyInfoMapper agentCountyInfoMapper;

    public PayFacadeImpl(OrderPayService orderPayService, UserService userService, SysOrderService sysOrderService,
                         MemberOrderService memberOrderService, GoodsCollectInfoService goodsCollectInfoService,
                         SysCommunityPartnerMapper sysCommunityPartnerMapper, CommunityService communityService) {
        this.orderPayService = orderPayService;
        this.userService = userService;
        this.sysOrderService = sysOrderService;
        this.memberOrderService = memberOrderService;
        this.goodsCollectInfoService = goodsCollectInfoService;
        this.sysCommunityPartnerMapper = sysCommunityPartnerMapper;
        this.communityService = communityService;
    }

    /**
     * 微信小程序秒杀/拼团订单支付
     *
     * @param initiateTeamDTO 订单信息
     * @return 支付结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> seckillOrderPay(InitiateTeamDTO initiateTeamDTO) {
        log.info("seckillOrderPay params:" + JSON.toJSONString(initiateTeamDTO));
        // 获取用户信息
        SysUser sysUser = userService.getById(initiateTeamDTO.getUserId());
        // 获取商品信息
        Goods goods = appGoodsService.getById(initiateTeamDTO.getGoodsId());
        log.info("seckillOrderPay goods:" + (goods == null));
        // 校验订单金额
        checkSeckillPayMoney(goods, sysUser, initiateTeamDTO);
        try {
            // 支付处理生成订单
            OrderMain orderMain = orderPayService.orderHandler(sysUser, initiateTeamDTO.getGoodsId(), initiateTeamDTO.getGoodsNum());

            orderMain.setTotalPrice(new BigDecimal(initiateTeamDTO.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            // 获取商家信息
            SysUserMerchant sysUserMerchant = sysUserMerchantService.getById(goods.getShopId());
            // 如果是拼团商品，拼团数量购买数量
            if (goods.getCollectFlag() == 1) {
                goodsCollectInfoService.addBuyNum(goods.getId(), initiateTeamDTO.getGoodsNum());
                orderMain.setCollectFlag(1);
                GoodsCollectInfo collectInfo = goodsCollectInfoService.getCollectInfoByGoodsId(goods.getId());
                orderMain.setCollectInfoId(Long.valueOf(collectInfo.getId()));
            }
            String payRecordNo = IdUtils.nextId();
            orderMain.setOrderNo(payRecordNo);
            // 提交支付
            OrderInfo orderInfo = createSeckillOrderInfo(sysUser, orderMain, payRecordNo, initiateTeamDTO.getIsAppPay());
            // 组装订单请求
            Map<String, String> payResult = payApi.pay(orderInfo);
            /* orderMain.setPrepayId(payResult.get("prepayId"));*/
            // 保存订单信息
            appOrderService.save(orderMain);
            log.info("sysUser memberType :" + sysUser.getMemberType());
            // 保存用户会员订单信息
            if (sysUser.getMemberType() == null || sysUser.getMemberType() == 0) {
                // 用户会员状态更新为已支付待回调状态
                userService.updateUserMemberType(sysUser.getId(), "2", sysUserMerchant.getName(), sysUserMerchant.getCommunityUserId());
                MemberOrder userMemberOrder = new MemberOrder(1, initiateTeamDTO.getUserId(), payRecordNo, BigDecimal.valueOf(9.9d));
                userMemberOrder.setCountyId(sysUserMerchant.getCountyId());
                userMemberOrder.setPartenerId(sysUserMerchant.getCommunityUserId());
                // 设置区县合伙人id
                AgentCountyInfo agentCountyInfo = agentCountyInfoMapper.queryByCountyId(sysUserMerchant.getCountyId());
                userMemberOrder.setAgentId(agentCountyInfo == null ? "" : agentCountyInfo.getAgentId());
                // 插入会员支付记录
                memberOrderService.insertMemberOrder(userMemberOrder);
            }
            payResult.put("orderId", orderMain.getId());
            log.info("seckillOrderPay payResult:" + payResult);
            return payResult;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private void checkSeckillPayMoney(Goods goods, SysUser sysUser, InitiateTeamDTO initiateTeamDTO) {
        if (goods == null) {
            throw new YidaoException(ReturnData.GOODS_IS_NOT_EXIST);
        }
        if (sysUser == null) {
            throw new YidaoException(ReturnData.USER_NOT_EXIST);
        }
        //获取商品总价（商品数量*单个商品总价）
        // 如果是拼团商品价格取拼团价
        if (goods.getCollectFlag() == 1) {
            GoodsCollectInfo goodsCollectInfo = goodsCollectInfoService.getCollectInfoByGoodsId(goods.getId());
            goods.setGoodsPrice(goodsCollectInfo.getCollectPrice());
        }
        double actualPrice = goods.getGoodsPrice().doubleValue() * initiateTeamDTO.getGoodsNum();
        // 检查会员费
        double memberPrice = 0d;
        log.info("checkOrder sysUser memberType:" + sysUser.getMemberType());
        if (sysUser.getMemberType() == null || sysUser.getMemberType() == 0) {
            memberPrice = 9.9d;
        } else if (Objects.equals(sysUser.getMemberType(), MemberTypeStatus.UNKNOWN_STATUS)) {
            throw new YidaoException(ReturnData.USER_MEMBR_ERROR.getCode(), ReturnData.USER_MEMBR_ERROR.getMsg());
        }
        log.info("actualPrice:" + actualPrice + ",memberPrice:" + memberPrice + ".totalPrice" + initiateTeamDTO.getTotalPrice());
        log.info("need money:" + new BigDecimal(actualPrice + memberPrice).setScale(2, BigDecimal.ROUND_HALF_UP) + ",has pay money:" + new BigDecimal(initiateTeamDTO.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        //  检查商品价格 * 数量 + 会员费和支付总金额是否相符
        if (new BigDecimal(actualPrice + memberPrice).setScale(MemberTypeStatus.UNKNOWN_STATUS, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(initiateTeamDTO.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP)) != 0) {
            throw new YidaoException(ReturnData.ORDER_PRICE_ERROR.getCode(), ReturnData.ORDER_PRICE_ERROR.getMsg());
        }
    }

    @Override
    public String notifySeckillWxPay(Map<String, String> params) {
        LoggerUtils.info(this.getClass(), "notifySeckillWxPay params=" + params.toString());
        if (!WXHelp.cheakSign(params)) {
            LoggerUtils.error(this.getClass(), "验证微信签名失败！");
            return "fail";
        }
        String attach = params.get("attach");// 拼单是否成功
        String resultCode = params.get("result_code");// SUCCESS/FAIL
        String transactionId = params.get("transaction_id");// 微信支付订单号
        String orderId = params.get("out_trade_no");// 商户系统的订单号，与请求一致
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderId);

        PayRecordInfo payRecordInfo = payRecordInfoService.queryById(orderId);
        OrderMain dbOrderMain = sysOrderService.getOne(queryWrapper);
        log.info("notifySeckillWxPay dbOrderMain status :" + (dbOrderMain == null ? null : dbOrderMain.getStatus()));
        // 判断订单是否处理过了，幂等性处理
        if (dbOrderMain == null) {
            return "fail";
        } else if (StringUtils.isNotBlank(payRecordInfo.getReturnPayInfo())) {
            return "success";
        }
        log.info("notifySeckillWxPay pay order no:" + orderId + ",resultCode:" + resultCode + ",pay status :" + payRecordInfo.getPayStatus());
        if (payRecordInfo.getPayStatus() != null && payRecordInfo.getPayStatus() == 0) {
            payRecordInfo.setReturnPayInfo(transactionId);
            payRecordInfo.setPayStatus(SUCCESS_FLAG.equals(resultCode) ? 1 : 2);
            payRecordInfoService.updatePayRecord(payRecordInfo);
        }
        String[] attachArr = attach.split(",");
        if (SUCCESS_FLAG.equals(resultCode)) {
            // 微信回调支付成功
            // 支付成功就更新用户的会员信息和会员过期时间
            // 加上订单为失败状态判断，防止小程序错误回调取消支付
            if (dbOrderMain.getStatus() != null && (dbOrderMain.getStatus().equals(OrderStatusConstants.WAIT_PAY)
                    || dbOrderMain.getStatus().equals(OrderStatusConstants.WAIT_CALLBACK)
                    || dbOrderMain.getStatus().equals(OrderStatusConstants.PAY_FAIL)
                    || dbOrderMain.getStatus().equals(OrderStatusConstants.COLLECT_WAIT_CALLBACK))) {
                Date date = new Date();
                log.info("notifySeckillWxPay pay attachArr:" + attach);
                // 非会员
                if (!MemberTypeStatus.IS_MEMBER.toString().equals(attachArr[2])) {
                    date = DateUtil.addYearFroDate(new Date(), 1);
                }
                String userId = attachArr[1];
                SysUser sysUser = userService.getById(userId);
                log.info("sysUser memberType:" + (sysUser == null ? null : sysUser.getMemberType()));
                if (sysUser != null) {
                    if (sysUser.getMemberType() != 1) {
                        // 如果不是会员
                        SysUser sysUser1 = new SysUser();
                        sysUser1.setId(userId);
                        sysUser1.setMemberType(1);
                        sysUser1.setMemberTime(DateUtil.date2LocalDateTime(date));
                        // 设置授权来源
                        sysUser1.setOrigin(dbOrderMain.getShoperId());
                        userService.updateById(sysUser1);
                        // 更新用户区县信息
                        updayeUserCountyInfo(dbOrderMain);
                        // 更新用户会员充值记录
                        memberOrderService.updatePayStatusByOrderId(orderId, 1);
                    }
                }
                // 更新订单支付状态
                OrderMain orderMain = new OrderMain();
                //  orderMain.setReturnPayInfo(transactionId);// 用于退款使用
                orderMain.setId(dbOrderMain.getId());
                if (dbOrderMain.getCollectFlag() == 1) {
                    orderMain.setStatus(OrderStatusConstants.COLLECT_WAIT_USE);
                } else {
                    orderMain.setStatus(OrderStatusConstants.WAIT_USE);
                }

                sysOrderService.updateById(orderMain);
                // 如果是错误的调用取消支付则要讲订单加回去
                if (dbOrderMain.getStatus().equals(OrderStatusConstants.PAY_FAIL)) {
                    appGoodsService.updateGoodsSurplusNumAndOrderNum(orderMain.getGoodsId(), orderMain.getGoodsNum(), 1);
                    goodsCollectInfoService.addBuyNum(orderMain.getGoodsId(), orderMain.getGoodsNum());
                }
            }
            return "success";
        }
        if ("FAIL".equals(resultCode)) {
            LoggerUtils.info(this.getClass(), "微信交易失败！");
            // 更新订单支付状态
            appOrderService.handleOrderToPayFail(dbOrderMain, transactionId);
            return "success";
        }
        return "fail";
    }


    /**
     * 微信小程序秒杀/拼团订单支付
     *
     * @param initiateTeamDTO 订单信息
     * @return 支付结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> seckillOrderPayV2(InitiateTeamDTO initiateTeamDTO) {
        // 获取用户信息
        SysUser sysUser = userService.getById(initiateTeamDTO.getUserId());
        log.info("seckillOrderPay user:" + sysUser);
        // 获取商品信息
        Goods goods = appGoodsService.getById(initiateTeamDTO.getGoodsId());
        log.info("seckillOrderPay goods:" + goods);
        // 校验订单金额
        BigDecimal actualPrice = checkSeckillPayMoneyV2(goods, sysUser, initiateTeamDTO);
        try {
            // 支付处理生成订单
            OrderMain orderMain = orderPayService.orderHandler(sysUser, initiateTeamDTO.getGoodsId(), initiateTeamDTO.getGoodsNum(), actualPrice);
            // 获取商家信息
            SysUserMerchant sysUserMerchant = sysUserMerchantService.getById(goods.getShopId());

            if (StringUtils.isNotBlank(initiateTeamDTO.getShareMerchantId())) {
                SysUserMerchant shareMerchant = sysUserMerchantService.getById(initiateTeamDTO.getShareMerchantId());
                // 添加分享退款费用,
                // 分享商家不为空，
                // 分享商家是联盟商家，
                // 分享商品是推广商品 ,
                // 分享商家不是自己
                // 商品归属商家是联盟商家  # 不需要已确认
                // 商品不是供应链商品
                log.info("merchantId :" + initiateTeamDTO.getShareMerchantId() + " ,league:" + sysUserMerchant.getLeague()
                        + " ,goods promotion:" + goods.getPromotion()
                        + " ,goods shopId:" + goods.getShopId()
                        + " shareMerchant league :" + shareMerchant.getLeague());
                if (StringUtils.isNotBlank(initiateTeamDTO.getShareMerchantId())
                        && goods.getSupplyChain() == 0
                        // && sysUserMerchant.getLeague() == 1
                        && goods.getPromotion() == 1
                        && shareMerchant.getLeague() == 1
                        && !initiateTeamDTO.getShareMerchantId().equals(goods.getShopId())) {
                    orderMain.setShareMerchantId(initiateTeamDTO.getShareMerchantId());
                    orderMain.setShareRate(goods.getPromotionCosts());
                }
            }
            log.info("seckillOrderPay merchant:" + sysUserMerchant);
            // 如果是拼团商品，拼团数量购买数量
            if (goods.getCollectFlag() == 1) {
                goodsCollectInfoService.addBuyNum(goods.getId(), initiateTeamDTO.getGoodsNum());
                orderMain.setCollectFlag(1);
                GoodsCollectInfo collectInfo = goodsCollectInfoService.getCollectInfoByGoodsId(goods.getId());
                log.info("seckillOrderPay collectInfo:" + collectInfo);
                orderMain.setCollectInfoId(Long.valueOf(collectInfo.getId()));
            }
            String payRecordNo = IdUtils.nextId();
            orderMain.setOrderNo(payRecordNo);
            // 提交支付
            OrderInfo orderInfo = createSeckillOrderInfoV2(sysUser, orderMain, payRecordNo, initiateTeamDTO.getIsAppPay());
            // 设置商品类型
            setOrderMainGoodsType(orderMain, goods);
            // 组装订单请求
            Map<String, String> payResult = payApi.pay(orderInfo);
            /* orderMain.setPrepayId(payResult.get("prepayId"));*/
            // 保存订单信息
            log.info("seckillOrderPay orderMain:" + orderMain);
            appOrderService.save(orderMain);
            payResult.put("orderId", orderMain.getId());
            log.info("seckillOrderPay payResult:" + payResult);
            return payResult;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 设置订单商品类型
     *
     * @param orderMain 订单
     * @param goods     商品
     */
    private void setOrderMainGoodsType(OrderMain orderMain, Goods goods) {
        if (goods.getCollectFlag() == 1 && goods.getSupplyChain() == 1) {
            orderMain.setGoodsType(GoodsTypeConstants.CHAIN_COLLECT_GOODS);
        } else if (goods.getCollectFlag() == 1) {
            orderMain.setGoodsType(GoodsTypeConstants.COMMON_COLLECT_GOODS);
        } else if (goods.getHot()) {
            orderMain.setGoodsType(GoodsTypeConstants.COMMON_HOT_GOODS);
        } else {
            orderMain.setGoodsType(GoodsTypeConstants.COMMON_GOODS);
        }
    }

    private BigDecimal checkSeckillPayMoneyV2(Goods goods, SysUser sysUser, InitiateTeamDTO initiateTeamDTO) {
        if (goods == null) {
            throw new YidaoException(ReturnData.GOODS_IS_NOT_EXIST);
        }
        if (sysUser == null) {
            throw new YidaoException(ReturnData.USER_NOT_EXIST);
        }
        //获取商品总价（商品数量*单个商品总价）
        // 如果是拼团商品价格取拼团价
        if (goods.getCollectFlag() == 1) {
            GoodsCollectInfo goodsCollectInfo = goodsCollectInfoService.getCollectInfoByGoodsId(goods.getId());
            goods.setGoodsPrice(goodsCollectInfo.getCollectPrice());
        }

        checkDiscountMoney(sysUser, goods, initiateTeamDTO);

        double actualPrice = goods.getGoodsPrice().doubleValue() * initiateTeamDTO.getGoodsNum();
        // 检查会员
        if (Objects.equals(sysUser.getMemberType(), MemberTypeStatus.UNKNOWN_STATUS)) {
            throw new YidaoException(ReturnData.USER_MEMBR_ERROR.getCode(), ReturnData.USER_MEMBR_ERROR.getMsg());
        }
        log.info("actualPrice:" + actualPrice + ".totalPrice" + initiateTeamDTO.getTotalPrice());
        log.info("need money:" + new BigDecimal(actualPrice).setScale(2, BigDecimal.ROUND_HALF_UP) + ",has pay money:" + new BigDecimal(initiateTeamDTO.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        //  检查商品价格 * 数量 + 会员费和支付总金额是否相符
        if (new BigDecimal(actualPrice).setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(initiateTeamDTO.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP)) != 0) {
            throw new YidaoException(ReturnData.ORDER_PRICE_ERROR.getCode(), ReturnData.ORDER_PRICE_ERROR.getMsg());
        }
        return BigDecimal.valueOf(initiateTeamDTO.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 如果用户非会员且已优惠金额大于会员费9.9,则不享受会员价
     *
     * @param user            用户
     * @param goods           商品
     * @param initiateTeamDTO 订单信息
     */
    private void checkDiscountMoney(SysUser user, Goods goods, InitiateTeamDTO initiateTeamDTO) {
        if (user.isNotMember()) {
            if (user.getDiscountMoney().compareTo(MEMBER_PRICE) >= 0 || (user.getBuyNum() != null && user.getBuyNum() >= 2)) {
                goods.setGoodsPrice(goods.getGoodsRetailPrice());
            }
        }
    }

    @Override
    public String notifySeckillWxPayV2(Map<String, String> params) {
        LoggerUtils.info(this.getClass(), "notifySeckillWxPay params=" + params.toString());
        if (!WXHelp.cheakSign(params)) {
            LoggerUtils.error(this.getClass(), "验证微信签名失败！");
            return "fail";
        }
        String attach = params.get("attach");// 拼单是否成功
        String resultCode = params.get("result_code");// SUCCESS/FAIL
        String transactionId = params.get("transaction_id");// 微信支付订单号
        String orderId = params.get("out_trade_no");// 商户系统的订单号，与请求一致
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderId);

        PayRecordInfo payRecordInfo = payRecordInfoService.queryById(orderId);
        OrderMain dbOrderMain = sysOrderService.getOne(queryWrapper);
        log.info("notifySeckillWxPay dbOrderMain status :" + (dbOrderMain == null ? null : dbOrderMain.getStatus()));
        // 判断订单是否处理过了，幂等性处理
        if (dbOrderMain == null) {
            return "fail";
        } else if (StringUtils.isNotBlank(payRecordInfo.getReturnPayInfo())) {
            return "success";
        }
        log.info("notifySeckillWxPay pay order no:" + orderId + ",resultCode:" + resultCode + ",pay status :" + payRecordInfo.getPayStatus());
        if (payRecordInfo.getPayStatus() != null && payRecordInfo.getPayStatus() == 0) {
            payRecordInfo.setReturnPayInfo(transactionId);
            payRecordInfo.setPayStatus(SUCCESS_FLAG.equals(resultCode) ? 1 : 2);
            payRecordInfoService.updatePayRecord(payRecordInfo);
        }
        String[] attachArr = attach.split(",");
        if (SUCCESS_FLAG.equals(resultCode)) {
            // 微信回调支付成功
            // 支付成功就更新用户的会员信息和会员过期时间
            // 加上订单为失败状态判断，防止小程序错误回调取消支付
            if (dbOrderMain.getStatus() != null && (dbOrderMain.getStatus().equals(OrderStatusConstants.WAIT_PAY)
                    || dbOrderMain.getStatus().equals(OrderStatusConstants.WAIT_CALLBACK)
                    || dbOrderMain.getStatus().equals(OrderStatusConstants.PAY_FAIL)
                    || dbOrderMain.getStatus().equals(OrderStatusConstants.COLLECT_WAIT_CALLBACK))) {

                log.info("notifySeckillWxPay pay attachArr:" + attach);
                String userId = attachArr[1];
                SysUser sysUser = userService.getById(userId);
                log.info("sysUser memberType:" + (sysUser == null ? null : sysUser.getMemberType()));
                // 更新订单支付状态
                OrderMain orderMain = new OrderMain();
                orderMain.setId(dbOrderMain.getId());
                if (dbOrderMain.getCollectFlag() == 1) {
                    orderMain.setStatus(OrderStatusConstants.COLLECT_WAIT_USE);
                } else {
                    orderMain.setStatus(OrderStatusConstants.WAIT_USE);
                }

                saveDiscountMoney(dbOrderMain, sysUser);

                sysOrderService.updateById(orderMain);
                // 如果是错误的调用取消支付则要讲订单加回去
                if (dbOrderMain.getStatus().equals(OrderStatusConstants.PAY_FAIL)) {
                    appGoodsService.updateGoodsSurplusNumAndOrderNum(orderMain.getGoodsId(), orderMain.getGoodsNum(), 1);
                    goodsCollectInfoService.addBuyNum(orderMain.getGoodsId(), orderMain.getGoodsNum());
                }
            }
            return "success";
        }
        if ("FAIL".equals(resultCode)) {
            LoggerUtils.info(this.getClass(), "微信交易失败！");
            // 更新订单支付状态
            appOrderService.handleOrderToPayFail(dbOrderMain, transactionId);
            return "success";
        }
        return "fail";
    }

    private void saveDiscountMoney(OrderMain orderMain, SysUser user) {
        Goods goods = appGoodsService.getById(orderMain.getGoodsId());
        BigDecimal discountMoney = goods.getGoodsRetailPrice().subtract(goods.getGoodsPrice())
                .multiply(BigDecimal.valueOf(orderMain.getGoodsNum())).add(user.getDiscountMoney());
        SysUser save = new SysUser();
        save.setId(user.getId());
        // 设置已优惠金额，有并发风险
        save.setDiscountMoney(discountMoney);
        // 设置已优惠次数，有并发风险
        save.setBuyNum(user.getBuyNum() + 1);
        userService.updateById(save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> shoppingCartPay(ShoppingPayDTO shoppingPayDTO) {
        log.info("shoppingCartPay req:" + JSON.toJSONString(shoppingPayDTO));
        if (shoppingPayDTO.getSelectedGoods() == null || shoppingPayDTO.getSelectedGoods().size() == 0) {
            throw new YidaoException(ReturnData.SHOPPING_NULL_ERROR);
        }
        // 获取用户信息
        SysUser sysUser = userService.getById(shoppingPayDTO.getUserId());
        // 对购物车支付进行校验
        checkShoppingCartPay(sysUser, shoppingPayDTO);
        // 订单金额
        BigDecimal shouldPay = new BigDecimal(0);
        String payRecordNo = IdUtils.nextId();
        log.info("shoppingCartPay payRecordNo :" + payRecordNo);
        try {
            for (ShoppingGoods shoppingGoods : shoppingPayDTO.getSelectedGoods()) {
                ShoppingCartInfo shoppingCartInfo = shoppingCartInfoService.queryById(shoppingGoods.getShoppingCartId());
                if (shoppingCartInfo == null) {
                    throw new YidaoException(ReturnData.CART_HAS_DEL);
                }
                //  校验用户 是否和购物车中一样
                if (!shoppingPayDTO.getUserId().equals(shoppingCartInfo.getUserId())) {
                    throw new YidaoException(ReturnData.CART_USER_ID_ERROR.getCode(), ReturnData.CART_USER_ID_ERROR.getMsg());
                }
                Goods goods = appGoodsService.getById(shoppingCartInfo.getGoodId());
                log.info("shoppingCartPay goods:" + (goods == null));

                // 支付处理生成订单
                OrderMain orderMain = orderPayService.orderHandler(sysUser, shoppingCartInfo.getGoodId(), shoppingGoods.getBuyNum());
                orderMain.setOrderNo(payRecordNo);
                // 保存订单信息
                appOrderService.save(orderMain);
                // 删除购物车信息 主键是shoppingGoods.getShoppingCartId()
                shoppingCartInfoService.updateShoppingCartStatus(shoppingGoods.getShoppingCartId(), 20);
                shouldPay = shouldPay.add(goods.getGoodsPrice().multiply(new BigDecimal(shoppingGoods.getBuyNum())));
            }
            // 校验订单金额
            if (shouldPay.setScale(2, BigDecimal.ROUND_UP).compareTo(shoppingPayDTO.getTotalPrice().setScale(2, BigDecimal.ROUND_UP)) != 0) {
                throw new YidaoException(ReturnData.ORDER_PRICE_ERROR.getCode(), ReturnData.ORDER_PRICE_ERROR.getMsg());
            }
            // 组装发送到微信请求
            OrderInfo orderInfo = createSCOrderInfo(payRecordNo, shoppingPayDTO.getTotalPrice(), sysUser, shoppingPayDTO.getIsAppPay());
            // 组装订单请求
            Map<String, String> payResult = payApi.pay(orderInfo);

            return payResult;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private void checkShoppingCartPay(SysUser sysUser, ShoppingPayDTO shoppingPayDTO) {
        log.info("shoppingCartPay user:" + JSON.toJSONString(sysUser));
        if (sysUser == null) {
            throw new YidaoException(ReturnData.NOT_USER);
        }
        //  校验用户是否是会员
        if (sysUser.getMemberType().equals(MemberTypeStatus.IS_NOT_MEMBER)) {
            throw new YidaoException(ReturnData.USER_NOT_MEMBER.getCode(), ReturnData.USER_NOT_MEMBER.getMsg());
        }
        // 清查请求信息
        for (ShoppingGoods shoppingGoods : shoppingPayDTO.getSelectedGoods()) {
            if (StringUtils.isBlank(shoppingGoods.getGoodsId()) || shoppingGoods.getBuyNum() == null) {
                throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER);
            }
        }
    }

    /**
     * 购物车支付回调
     *
     * @param params
     * @return
     */
    @Override
    public String notifyShoppingWxPay(Map<String, String> params) {
        LoggerUtils.info(this.getClass(), "notifyShoppingWxPay params=" + params.toString());
        if (!WXHelp.cheakSign(params)) {
            LoggerUtils.error(this.getClass(), "验证微信签名失败！");
            return "fail";
        }
        String resultCode = params.get("result_code");// SUCCESS/FAIL
        String transactionId = params.get("transaction_id");// 微信支付订单号
        String orderId = params.get("out_trade_no");// 商户系统的订单号，与请求一致
        PayRecordInfo payRecordInfo = payRecordInfoService.queryById(orderId);
        log.info("notifyShoppingWxPay payRecordInfo :" + JSON.toJSONString(payRecordInfo));
        // 判断订单是否处理过了，幂等性处理
        if (payRecordInfo == null) {
            return "fail";
        } else if (StringUtils.isNotBlank(payRecordInfo.getReturnPayInfo())) {
            return "success";
        }
        log.info("notifyShoppingWxPay pay order no:" + orderId + ",resultCode:" + resultCode + ",pay status :" + payRecordInfo.getPayStatus());
        if (payRecordInfo.getPayStatus() != null && payRecordInfo.getPayStatus() == 0) {
            payRecordInfo.setReturnPayInfo(transactionId);
            payRecordInfo.setPayStatus(SUCCESS_FLAG.equals(resultCode) ? 1 : 2);
            payRecordInfoService.updatePayRecord(payRecordInfo);
        }
        if (SUCCESS_FLAG.equals(resultCode)) {
            // 微信回调支付成功
            // 支付成功就更新用户的会员信息和会员过期时间
            List<OrderMain> orderMainList = appOrderService.queryByOrderId(orderId);
            for (OrderMain dbOrderMain : orderMainList) {
                // 加上订单为失败状态判断，防止小程序错误回调取消支付
                if (dbOrderMain.getStatus() != null && (dbOrderMain.getStatus().equals(OrderStatusConstants.WAIT_PAY)
                        || dbOrderMain.getStatus().equals(OrderStatusConstants.WAIT_CALLBACK) || dbOrderMain.getStatus().equals(OrderStatusConstants.PAY_FAIL))) {
                    // 更新订单支付状态
                    OrderMain orderMain = new OrderMain();
                    //orderMain.setReturnPayInfo(transactionId);// 用于退款使用
                    orderMain.setId(dbOrderMain.getId());
                    orderMain.setStatus(OrderStatusConstants.WAIT_USE);
                    sysOrderService.updateById(orderMain);
                    // 如果是错误的调用取消支付则要讲订单加回去
                    if (dbOrderMain.getStatus().equals(OrderStatusConstants.PAY_FAIL)) {
                        appGoodsService.updateGoodsSurplusNumAndOrderNum(orderMain.getGoodsId(), orderMain.getGoodsNum(), 1);
                        //goodsCollectInfoService.addBuyNum(orderMain.getGoodsId(),orderMain.getGoodsNum());
                    }
                }
            }
            return "success";
        }
        if ("FAIL".equals(resultCode)) {
            LoggerUtils.info(this.getClass(), "微信交易失败！");
            List<OrderMain> orderMainList = appOrderService.queryByOrderId(orderId);
            for (OrderMain dbOrderMain : orderMainList) {
                appOrderService.handleOrderToPayFail(dbOrderMain, transactionId);
            }
            return "success";
        }
        return "fail";
    }

    @Override
    public int appPayCallBack(OrderPayDTO orderPayDTO) {
        log.info("appPayCallBack req:" + JSON.toJSONString(orderPayDTO));
        MemberOrder memberOrder = memberOrderService.selectMemberOrderByOutOrderNo(orderPayDTO.getOrderId());
        List<OrderMain> orderMains = appOrderService.queryByOrderId(orderPayDTO.getOrderId());
        log.info("orderMains size:" + (orderMains == null ? 0 : orderMains.size()));
        if (orderMains != null && orderMains.size() > 0) {
            // 判断订单是否处理过了，处理过了则不做处理
            if (!OrderStatusConstants.WAIT_PAY.equals(orderMains.get(0).getStatus())) {
                return 0;
            }
        }
        // 有会员信息则改成会员
        log.info("memberOrder:" + memberOrder);
        if (memberOrder != null) {
            if (memberOrder.getOrderType() == ProfitConstants.OrderType.USER) {
                // 如果不是会员
                SysUser sysUser1 = new SysUser();
                sysUser1.setId(memberOrder.getUserId());
                sysUser1.setMemberType(1);
                sysUser1.setMemberTime(DateUtil.date2LocalDateTime(DateUtil.addYearFroDate(new Date(), 1)));
                //sysUser1.setOrigin(orderMains.get(0).getShoperId());
                //userService.updateById(sysUser1);
                /* List<OrderMain> dbOrderMains = appOrderService.queryByOrderId(orderPayDTO.getOrderId());*/
                // 更新用户区县信息
                /*updayeUserCountyInfo(dbOrderMains.get(0));*/
                updateUserCountyInfoByCountyId(memberOrder.getCountyId(), sysUser1);
            } else {
                SysUserMerchant sysUserMerchant = new SysUserMerchant();
                sysUserMerchant.setId(memberOrder.getUserId());
                sysUserMerchant.setMemberType(1);
                sysUserMerchant.setMemberTime(DateUtil.date2LocalDateTime(DateUtil.addYearFroDate(new Date(), 1)));
                sysUserMerchantService.updateById(sysUserMerchant);
            }
            // 更新用户会员充值记录
            memberOrderService.updatePayStatusByOrderId(orderPayDTO.getOrderId(), 1);
        }
        for (OrderMain orderMain : orderMains) {
            if (orderMain.getCollectFlag() == 1) {
                orderMain.setStatus(OrderStatusConstants.COLLECT_WAIT_USE);
            } else {
                orderMain.setStatus(OrderStatusConstants.WAIT_USE);
            }
            appOrderService.updateById(orderMain);
        }
        return 0;
        //return appOrderService.updateOrderToHasPayByOrderNo(orderPayDTO.getOrderId());
    }

    @Override
    public int appPayCallBackV2(OrderPayDTO orderPayDTO) {
        log.info("appPayCallBack req:" + JSON.toJSONString(orderPayDTO));
        MemberOrder memberOrder = memberOrderService.selectMemberOrderByOutOrderNo(orderPayDTO.getOrderId());
        List<OrderMain> orderMains = appOrderService.queryByOrderId(orderPayDTO.getOrderId());
        log.info("orderMains size:" + (orderMains == null ? 0 : orderMains.size()));
        if (orderMains != null && orderMains.size() > 0) {
            // 判断订单是否处理过了，处理过了则不做处理
            if (!OrderStatusConstants.WAIT_PAY.equals(orderMains.get(0).getStatus())) {
                return 0;
            }
        } else {
            return 0;
        }

        saveDiscountMoney(orderMains.get(0), userService.getById(orderPayDTO.getUserId()));

        for (OrderMain orderMain : orderMains) {
            if (orderMain.getCollectFlag() == 1) {
                orderMain.setStatus(OrderStatusConstants.COLLECT_WAIT_USE);
            } else {
                orderMain.setStatus(OrderStatusConstants.WAIT_USE);
            }
            appOrderService.updateById(orderMain);
        }
        return 0;
    }

    private OrderInfo createSCOrderInfo(String payRecordNo, BigDecimal totalMoney, SysUser user, Integer isAppPay) {
        OrderInfo orderInfo = new OrderInfo();
        // 组装参数
        if (isAppPay == null || isAppPay == 0) {
            orderInfo.setAppId(WxPayConfig.getAppId());
        } else {
            orderInfo.setAppId(WxPayConfig.APP_APP_ID);
        }
        orderInfo.setMchId(WxPayConfig.getMchId());
        // 随机字符串
        String nonceStr = WXHelp.CreateNoncestr();
        orderInfo.setNonceStr(nonceStr);
        // 传递拼单是否成功的数据
        orderInfo.setAttach("");
        orderInfo.setOutTradeNo(payRecordNo);
        orderInfo.setBody("成典电商");
        orderInfo.setDeviceInfo(WxPayConfig.getDeviceInfoWeb());
        orderInfo.setTotalFee(totalMoney.multiply(new BigDecimal(100)).longValue());
        orderInfo.setSpbillCreateIp("127.0.0.1");
        orderInfo.setOrderType(OrderTypeConstants.SHOPING_CART_ORDER.getCode());
        orderInfo.setNotifyUrl(shoppingOrderPayCallBackUrl);
        orderInfo.setTradeType(WxPayConfig.getTradetypeJsapi());
        orderInfo.setUserId(user.getId());
        orderInfo.setOpenId(user.getOpenId());
        return orderInfo;
    }

    /**
     * 生成秒杀微信的请求订单
     *
     * @return
     */
    private OrderInfo createSeckillOrderInfoV2(SysUser sysUser, OrderMain order, String orderNo, Integer isAppPay) {
        log.info("createSeckillOrderInfo req:" + "[sysUser:" + sysUser + "order:" + order + "orderNo:" + orderNo + "]");
        OrderInfo orderInfo = new OrderInfo();
        // 组装参数
        if (isAppPay == null || isAppPay == 0) {
            orderInfo.setAppId(WxPayConfig.getAppId());
        } else {
            orderInfo.setAppId(WxPayConfig.APP_APP_ID);
        }

        orderInfo.setMchId(WxPayConfig.getMchId());
        // 随机字符串
        String nonceStr = WXHelp.CreateNoncestr();
        orderInfo.setNonceStr(nonceStr);
        // 传递拼单是否成功的数据
        orderInfo.setAttach(SuccessFlagConstants.SUCCESS + "," + sysUser.getId() + "," + sysUser.getMemberType());
        orderInfo.setOutTradeNo(orderNo);
        orderInfo.setUserId(sysUser.getId());
        orderInfo.setBody("成典电商");
        orderInfo.setDeviceInfo(WxPayConfig.getDeviceInfoWeb());
        orderInfo.setTotalFee(order.getTotalPrice().multiply(new BigDecimal(100)).longValue());
        orderInfo.setSpbillCreateIp("127.0.0.1");
        orderInfo.setNotifyUrl(seckillOrderPayCallBackUrl + "V2");
        if (isAppPay == null || isAppPay == 0) {
            orderInfo.setTradeType(WxPayConfig.getTradetypeJsapi());
        } else {
            orderInfo.setTradeType(WxPayConfig.TRADETYPE_APP);
        }

        orderInfo.setOpenId(sysUser.getOpenId());
        orderInfo.setOrderType(OrderTypeConstants.SECKILL_ORDER.getCode());
        return orderInfo;
    }

    /**
     * 生成秒杀微信的请求订单
     *
     * @return
     */
    private OrderInfo createSeckillOrderInfo(SysUser sysUser, OrderMain order, String orderNo, Integer isAppPay) {
        log.info("createSeckillOrderInfo req:" + "[sysUser:" + sysUser + "order:" + order + "orderNo:" + orderNo + "]");
        OrderInfo orderInfo = new OrderInfo();
        // 组装参数
        if (isAppPay == null || isAppPay == 0) {
            orderInfo.setAppId(WxPayConfig.getAppId());
        } else {
            orderInfo.setAppId(WxPayConfig.APP_APP_ID);
        }

        orderInfo.setMchId(WxPayConfig.getMchId());
        // 随机字符串
        String nonceStr = WXHelp.CreateNoncestr();
        orderInfo.setNonceStr(nonceStr);
        // 传递拼单是否成功的数据
        orderInfo.setAttach(SuccessFlagConstants.SUCCESS + "," + sysUser.getId() + "," + sysUser.getMemberType());
        orderInfo.setOutTradeNo(orderNo);
        orderInfo.setUserId(sysUser.getId());
        orderInfo.setBody("成典电商");
        orderInfo.setDeviceInfo(WxPayConfig.getDeviceInfoWeb());
        orderInfo.setTotalFee(order.getTotalPrice().multiply(new BigDecimal(100)).longValue());
        orderInfo.setSpbillCreateIp("127.0.0.1");
        orderInfo.setNotifyUrl(seckillOrderPayCallBackUrl);
        if (isAppPay == null || isAppPay == 0) {
            orderInfo.setTradeType(WxPayConfig.getTradetypeJsapi());
        } else {
            orderInfo.setTradeType(WxPayConfig.TRADETYPE_APP);
        }

        orderInfo.setOpenId(sysUser.getOpenId());
        orderInfo.setOrderType(OrderTypeConstants.SECKILL_ORDER.getCode());
        return orderInfo;
    }

    @Override
    public Map<String, String> merchantMemberPay(OrderPayDTO dto) {
        log.info("merchantMemberPay req:" + JSON.toJSONString(dto));
        OrderInfo orderInfo = new OrderInfo();
        // 判断用户是否是支付过或者是否正在支付中
        SysUserMerchant sysUserMerchant = sysUserMerchantService.getById(dto.getUserId());
        log.info("merchant is:" + (sysUserMerchant == null ? null : sysUserMerchant.getMemberType()));
        if (sysUserMerchant == null || sysUserMerchant.getMemberType() != 0) {
            throw new YidaoException(ReturnData.USER_MEMBR_ERROR.getCode(), ReturnData.USER_MEMBR_ERROR.getMsg());
        }
        // 随机字符串
        String nonceStr = WXHelp.CreateNoncestr();
        // 组装参数
        if (dto.getIsAppPay() == null || dto.getIsAppPay() == 0) {
            orderInfo.setAppId(WxPayConfig.getAppId());
        } else {
            orderInfo.setAppId(WxPayConfig.APP_APP_ID);
        }
        orderInfo.setMchId(WxPayConfig.getMchId());
        orderInfo.setNonceStr(nonceStr);
        // 传递拼单是否成功的数据
        orderInfo.setAttach("isMerchant" + "," + dto.getUserId());
        String orderId = IdUtils.nextId();
        orderInfo.setOutTradeNo(orderId);
        orderInfo.setBody("成典电商");
        orderInfo.setDeviceInfo(WxPayConfig.getDeviceInfoWeb());
        orderInfo.setTotalFee(new BigDecimal("365").multiply(new BigDecimal(100)).longValue());
        orderInfo.setSpbillCreateIp("127.0.0.1");
        orderInfo.setNotifyUrl(callBackUrl);
        orderInfo.setOrderType(OrderTypeConstants.MERCHANT_MEMBER_ORDER.getCode());
        orderInfo.setUserId(dto.getUserId());
        orderInfo.setTradeType(WxPayConfig.getTradetypeJsapi());
        //获取用户的openid
        orderInfo.setOpenId(dto.getOpenId());
        try {
            SysUserMerchant sysUserMerchant1 = new SysUserMerchant();
            // 邀请码校验
            if (StringUtils.isNotBlank(dto.getInvitatCode())) {
                int n = sysCommunityPartnerMapper.checkInvitatCodeAndCounty(dto.getInvitatCode(), sysUserMerchant.getCountyId());
                if (n == 0) {
                    throw new YidaoException(ReturnData.IDENTITY_CODE_ERROR);
                }
                SysCommunityPartner sysCommunityPartner = sysCommunityPartnerMapper.queryByInvitatCode(dto.getInvitatCode());
                sysUserMerchant1.setCommunityUserId(sysCommunityPartner.getId());
            }
            Map<String, String> payResult = payApi.pay(orderInfo);

            sysUserMerchant1.setId(dto.getUserId());
            sysUserMerchant1.setMemberType(2);
            sysUserMerchant1.setMemberTime(DateUtil.date2LocalDateTime(new Date()));
            sysUserMerchant1.setInvitatCode(dto.getInvitatCode());
            sysUserMerchantService.updateById(sysUserMerchant1);
            // 插入一天商户会员支付记录
            MemberOrder memberOrder = new MemberOrder();
            memberOrder.setOrderType(2);
            memberOrder.setUserId(dto.getUserId());
            memberOrder.setMemberPrice(new BigDecimal(365));
            memberOrder.setOutOrderNo(orderId);
            memberOrder.setCountyId(sysUserMerchant.getCountyId());
            // 设置群社代理人id
            memberOrder.setPartenerId(sysUserMerchant1.getCommunityUserId());
            // 设置区县合伙人id
            AgentCountyInfo agentCountyInfo = agentCountyInfoMapper.queryByCountyId(sysUserMerchant.getCountyId());
            memberOrder.setAgentId(agentCountyInfo == null ? "" : agentCountyInfo.getAgentId());
            memberOrderService.insertMemberOrder(memberOrder);
            return payResult;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 微信回调函数
     *
     * @param params
     * @return
     */
    @Override
    public String notifyWxPay(Map<String, String> params) {
        LoggerUtils.error(this.getClass(), "微信异步通知参数：params={}" + params.toString());
        if (!WXHelp.cheakSign(params)) {
            LoggerUtils.error(this.getClass(), "验证微信签名失败！");
            return "fail";
        }
        String attach = params.get("attach");// 拼单是否成功
        String resultCode = params.get("result_code");// SUCCESS/FAIL
        String orderId = params.get("out_trade_no");// 商户系统的订单号，与请求一致
        if (attach.contains("isMerchant")) {
            String[] attachArr = attach.split(",");
            PayRecordInfo payRecordInfo = payRecordInfoService.queryById(orderId);
            String transactionId = params.get("transaction_id");// 微信支付订单号
            payRecordInfo.setReturnPayInfo(transactionId);
            payRecordInfo.setPayStatus("SUCCESS".equals(resultCode) ? 1 : 2);
            payRecordInfoService.updatePayRecord(payRecordInfo);
            if ("SUCCESS".equals(resultCode)) {
                SysUserMerchant sysUserMerchant = sysUserMerchantService.getById(attachArr[1]);
                if (sysUserMerchant != null) {
                    if (sysUserMerchant.getMemberType() == null || sysUserMerchant.getMemberType() != 1) {
                        SysUserMerchant sysUserMerchant1 = new SysUserMerchant();
                        sysUserMerchant1.setId(attachArr[1]);
                        Date date = DateUtil.addYearFroDate(new Date(), 1);
                        sysUserMerchant1.setMemberType(1);
                        sysUserMerchant1.setMemberTime(DateUtil.date2LocalDateTime(date));
                        sysUserMerchantService.updateById(sysUserMerchant1);
                        // 更新商家会员充值记录
                        memberOrderService.updatePayStatusByOrderId(orderId, 1);
                        return "success";
                    }
                }
            }
        }
        return "fail";
    }

    /**
     * 购买会员
     *
     * @param dto 参数,店铺id可不传
     * @return 支付调用返回
     */
    @Override
    public Map<String, String> userMemberPay(UserMemberPayDTO dto) {
        log.info(dto.toString());
        renewalMember(dto);
        OrderInfo orderInfo = new OrderInfo();
        String orderId = IdUtils.nextId();
        BeanUtils.copyProperties(dto, orderInfo);
        orderInfo.setNotifyUrl(userMemberPayCallBackUrl);
        orderInfo.setOutTradeNo(orderId);
        orderInfo.setTotalFee(orderInfo.getTotalPrice().multiply(new BigDecimal(100)).longValue());
        // 组装参数
        orderInfo.setAppId(WxPayConfig.getAppId());
        orderInfo.setMchId(WxPayConfig.getMchId());
        // 随机字符串
        String nonceStr = WXHelp.CreateNoncestr();
        orderInfo.setNonceStr(nonceStr);
        // 传递拼单是否成功的数据
        orderInfo.setBody("成典电商");
        orderInfo.setDeviceInfo(WxPayConfig.getDeviceInfoWeb());
        orderInfo.setSpbillCreateIp("127.0.0.1");
        orderInfo.setTradeType(WxPayConfig.getTradetypeJsapi());
        handleMemberOrder(dto, orderId);
        String attach = dto.getCountyId() + "," + dto.getShopId();
        orderInfo.setAttach(attach);
        // TODO 代码待调整以及优化
        Map<String, String> result = null;
        try {
            result = payApi.pay(orderInfo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

     /*   JSONObject jsonObject = (JSONObject) JSON.toJSON(result);
        Map<String,String> parameterMap2 = new HashMap<>();
        jsonObject.put("appId", orderInfo.getAppId());
        jsonObject.put("package", "prepay_id=" + result.get);
        jsonObject.put("nonceStr", orderInfo.getNonceStr());
        String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        jsonObject.put("timeStamp", timeStamp);
        jsonObject.put("signType", "MD5");
        sign2 = WXHelp.creatSign_md5(parameterMap2);*/

        return result;
    }

    /**
     * 会员续费处理
     *
     * @param dto 参数
     */
    private void renewalMember(UserMemberPayDTO dto) {
        SysUser user = userService.getById(dto.getUserId());
        AssertUtils.checkNotNull(user, "用户不存在");
        AssertUtils.checkArgument(user.isNotMember(), "用户已经是会员或已支付会员，请勿继续支付会员");
    }

    private void handleMemberOrder(UserMemberPayDTO dto, String orderId) {
        // 插入一天商户会员支付记录
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setOrderType(1);
        memberOrder.setUserId(dto.getUserId());
        memberOrder.setMemberPrice(BigDecimal.valueOf(9.9));
        memberOrder.setOutOrderNo(orderId);
        memberOrder.setCountyId(dto.getCountyId());
        if (!isEmpty(dto.getCommuniyId())) {
            Community community = communityService.getDetail(dto.getCommuniyId());
            AssertUtils.checkNotNull(community, "小区不存在或已删除!");
            dto.setCountyId(community.getCountyId());
            memberOrder.setCountyId(community.getCountyId());
            // 设置区县合伙人id
            AgentCountyInfo agentCountyInfo = agentCountyInfoMapper.queryByCountyId(community.getCountyId());
            memberOrder.setAgentId(agentCountyInfo == null ? "" : agentCountyInfo.getAgentId());
        }
        if (!isEmpty(dto.getShopId())) {
            SysUserMerchant merchant = sysUserMerchantService.getById(dto.getShopId());
            AssertUtils.checkNotNull(merchant, "商家不存在或已删除!");
            // 设置群社代理人id
            memberOrder.setPartenerId(merchant.getCommunityUserId());
            // 非直接购买设置区县id
            memberOrder.setCountyId(merchant.getCountyId());
            // 设置区县合伙人id
            AgentCountyInfo agentCountyInfo = agentCountyInfoMapper.queryByCountyId(merchant.getCountyId());
            memberOrder.setAgentId(agentCountyInfo == null ? "" : agentCountyInfo.getAgentId());
        }
        memberOrderService.insertMemberOrder(memberOrder);
    }

    /**
     * 处理购买会员回调
     *
     * @param xmlData 回调数据
     * @throws WxPayException 支付异常
     */
    @Override
    public String notifyUserMemberPay(String xmlData) throws WxPayException {
        WxPayOrderNotifyResult result = payApi.parseOrderNotifyResult(xmlData);
        log.info(result.toString());
        String orderId = result.getOutTradeNo();

        PayRecordInfo payRecordInfo = payRecordInfoService.queryById(orderId);
        if (payRecordInfo == null) {
            throw new YidaoException("支付回调错误,PayRecordInfo不存在!");
        }
        if (payRecordInfo.getPayStatus() != null && payRecordInfo.getPayStatus() == 0) {
            payRecordInfo.setReturnPayInfo(result.getTransactionId());
            payRecordInfo.setPayStatus(SUCCESS_FLAG.equals(result.getResultCode()) ? 1 : 2);
            payRecordInfoService.updatePayRecord(payRecordInfo);
        } else {
            return WxPayNotifyResponse.success("成功");
        }

        completeUser(result);
        // 更新用户会员充值记录
        memberOrderService.updatePayStatusByOrderId(orderId, 1);
        return WxPayNotifyResponse.success("成功");
    }

    private void completeUser(WxPayOrderNotifyResult result) {
        String[] attach = result.getAttach().split(",");
        String shopId = attach.length > 1 ? attach[1] : null;
        String countyId = attach[0];
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(SysUser::getOpenId, result.getOpenid());
        SysUser user = userService.getOne(userQueryWrapper);
        if (user == null) {
            throw new YidaoException("支付回调错误,用户不存在!");
        }
        SysUser sysUser1 = new SysUser();
        sysUser1.setId(user.getId());
        sysUser1.setMemberType(MemberTypeStatus.IS_MEMBER);
        if (user.isNotMember()) {
            sysUser1.setMemberTime(LocalDateTime.now().plusYears(1));
        } else {
            sysUser1.setMemberTime(user.getMemberTime().plusYears(1));
        }
        // 设置授权来源
        sysUser1.setOrigin(shopId);
        // 更新用户区县信息
        updateUserCountyInfoByCountyId(countyId, sysUser1);
    }

    @Override
    public String refundCallBack(Map<String, String> params) {
        //  处理退款回调内容
        String reqInfo = params.get("req_info");
        try {
            String reqResult = WXHelp.decryptRefundCallBack(reqInfo, WxPayConfig.getKEY());
            Map<String, String> resultMap = WxSignUtil.parseXmlStr(reqResult);
            // 退款请求单号
            String outRefundNo = resultMap.get("out_refund_no");
            // 退款状态
            String refundStatus = resultMap.get("refund_status");
            PayRefundInfo refundInfo = payRefundInfoMapper.queryByOutTradeNo(outRefundNo);
            if ("SUCCESS".equals(refundStatus)) {
                //  更新退款请求的回调内容以及请求的结果
                payRefundInfoMapper.updateRefundStatus(refundInfo.getId(), CommonConstants.ONE_INT, reqResult);
                // 更新打款状态
                refundApplyMapper.updateRemitStatus(refundInfo.getRefundApplyId(), PayRefundApplyStatus.RefundRemitStatus.REMIT_SUCCESS);
                refundApplyMapper.updateFefundStatus(refundInfo.getRefundApplyId(), PayRefundApplyStatus.RefundStatus.REFUNDED);
            } else {
                payRefundInfoMapper.updateRefundStatus(refundInfo.getId(), CommonConstants.ZERO_INT, reqResult);
                refundApplyMapper.updateRemitStatus(refundInfo.getRefundApplyId(), PayRefundApplyStatus.RefundRemitStatus.REMIT_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 先固定返回
        return "<xml> \n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    /**
     * 插入数据到支付记录表
     *
     * @param orderMain
     */
    private void updayeUserCountyInfo(OrderMain orderMain) {
        log.info("updayeUserCountyInfo req:" + JSON.toJSONString(orderMain));
        SysUser sysUser = userService.getById(orderMain.getUserId());
        SysUserMerchant merchant = sysUserMerchantService.getById(orderMain.getShoperId());
        updateUserCountyInfoByCountyId(merchant.getCountyId(), sysUser);
    }

    private void updateUserCountyInfoByCountyId(String countyId, SysUser sysUser) {
        SysHasAgenAreaVo sysHasAgenAreaVo = sysUserMerchantService.selectAddress(countyId);
        // 更新用户的区县地址信息
        sysUser.setProvinceCode(sysHasAgenAreaVo.getProvinceCode());
        sysUser.setProvinceName(sysHasAgenAreaVo.getProvinceName());
        sysUser.setCityCode(sysHasAgenAreaVo.getCityCode());
        sysUser.setCityName(sysHasAgenAreaVo.getCityName());
        sysUser.setCountyCode(sysHasAgenAreaVo.getCountyCode());
        sysUser.setCountyName(sysHasAgenAreaVo.getCountyName());
        String userCityAddress = sysHasAgenAreaVo.getProvinceName() + sysHasAgenAreaVo.getCityName() + sysHasAgenAreaVo.getCountyName();
        if (userCityAddress.contains("null")) {
            userCityAddress = null;
        }
        sysUser.setUserCityAddress(userCityAddress);
        // 更新用户的省市区地址信息
        userService.updateById(sysUser);
    }
}
