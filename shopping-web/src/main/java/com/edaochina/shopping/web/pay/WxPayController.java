package com.edaochina.shopping.web.pay;

import com.alibaba.fastjson.JSON;
import com.edaochina.shopping.api.facade.pay.PayFacade;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.weixinpay.WxSignUtil;
import com.edaochina.shopping.common.wxpay.util.WXHelp;
import com.edaochina.shopping.domain.constants.OrderTypeConstants;
import com.edaochina.shopping.domain.dto.order.InitiateTeamDTO;
import com.edaochina.shopping.domain.dto.order.OrderPayDTO;
import com.edaochina.shopping.domain.dto.order.ShoppingPayDTO;
import com.edaochina.shopping.domain.dto.order.UserMemberPayDTO;
import com.github.binarywang.wxpay.exception.WxPayException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 微信支付相关
 *
 * @Author xww
 * @Date 2019/01/08
 */
@Api(tags = "微信支付相关")
@RestController
@RequestMapping("/app/wxpay")
public class WxPayController {

    private Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Resource
    private PayFacade payFacade;

    /**
     * 微信秒杀支付
     *
     * @return
     */
    @ApiOperation("微信秒杀支付")
    @PostMapping("/miniPay")
    public AjaxResult miniPay(@RequestBody InitiateTeamDTO initiateTeamDTO) {
        logger.info("miniPay req:" + JSON.toJSONString(initiateTeamDTO));
        // 发起/加入拼单
        if (initiateTeamDTO == null) {
            throw new YidaoException(ReturnData.ORDER_PARAM_EMPTY.getCode(), ReturnData.ORDER_PARAM_EMPTY.getMsg());
        }
        return BaseResult.successResult(payFacade.seckillOrderPay(initiateTeamDTO));
    }

    /**
     * 微信秒杀支付
     *
     * @return
     */
    @ApiOperation("微信秒杀支付V2,不包含会员费版本")
    @PostMapping("v2/miniPay")
    public AjaxResult v2miniPay(@RequestBody InitiateTeamDTO initiateTeamDTO) {
        logger.info("miniPay req:" + JSON.toJSONString(initiateTeamDTO));
        // 发起/加入拼单
        if (initiateTeamDTO == null) {
            throw new YidaoException(ReturnData.ORDER_PARAM_EMPTY.getCode(), ReturnData.ORDER_PARAM_EMPTY.getMsg());
        }
        return BaseResult.successResult(payFacade.seckillOrderPayV2(initiateTeamDTO));
    }


    /**
     * 购物车支付
     *
     * @return
     */
    @ApiOperation("购物车支付")
    @PostMapping("/shoppingCartPay")
    public AjaxResult shoppingCartPay(@RequestBody ShoppingPayDTO shoppingPayDTO) {
        logger.info("shoppingCatPay req:" + JSON.toJSONString(shoppingPayDTO));
        if (shoppingPayDTO == null) {
            throw new YidaoException(ReturnData.ORDER_PARAM_EMPTY.getCode(), ReturnData.ORDER_PARAM_EMPTY.getMsg());
        }
        return BaseResult.successResult(payFacade.shoppingCartPay(shoppingPayDTO));
    }


    /**
     * 商户微信起调支付接口
     *
     * @return
     */
    @ApiOperation("商户微信起调支付接口")
    @PostMapping("/merchantMemberPay")
    public AjaxResult merchantMemberPay(@RequestBody OrderPayDTO dto) {
        return BaseResult.successResult(payFacade.merchantMemberPay(dto));
    }

    /**
     * 用户会员费微信起调支付接口
     *
     * @return 是否成功
     */
    @ApiOperation("用户会员费微信起调支付接口")
    @PostMapping("/userMemberPay")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult userMemberPay(@RequestBody UserMemberPayDTO dto) {
        dto.setTotalPrice(BigDecimal.valueOf(9.9));
        dto.setOrderType(OrderTypeConstants.USER_MEMBER_ORDER.getCode());
        return BaseResult.successResult(payFacade.userMemberPay(dto));
    }

    /**
     * 用户会员费微信起调支付异步回调方法请求
     */
    @ResponseBody
    @RequestMapping(value = "/notifyUserMemberPay")
    @Transactional(rollbackFor = Exception.class)
    public String notifyUserMemberPay(HttpServletRequest request) throws WxPayException, IOException {
        BufferedReader reader = request.getReader();
        // 组装异步回调信息
        StringBuilder xmlStr = WXHelp.getXmlStr(reader);
        return payFacade.notifyUserMemberPay(xmlStr.toString());
    }


    /**
     * 微信秒杀商品支付异步回调方法请求
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notifySeckillWxPayV2")
    public String notifySeckillWxPayV2(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        // 组装异步回调信息
        StringBuilder xmlStr = WXHelp.getXmlStr(reader);
        // 解析结果存储在HashMap
        Map<String, String> params = WxSignUtil.parseNotify(xmlStr.toString());
        return payFacade.notifySeckillWxPayV2(params);
    }

    /**
     * 微信秒杀商品支付异步回调方法请求
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notifySeckillWxPay")
    public String notifySeckillWxPay(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        // 组装异步回调信息
        StringBuilder xmlStr = WXHelp.getXmlStr(reader);
        // 解析结果存储在HashMap
        Map<String, String> params = WxSignUtil.parseNotify(xmlStr.toString());
        return payFacade.notifySeckillWxPay(params);
    }


    /**
     * 微信异步回调方法请求
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notifyWxPay")
    public String notifyWxPay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoggerUtils.info(WxPayController.class, "微信支付异步通知进入");
        // 获取微信POST过来反馈信息
        BufferedReader reader = request.getReader();
        // 组装异步回调信息
        StringBuilder xmlStr = WXHelp.getXmlStr(reader);
        // 解析结果存储在HashMap
        Map<String, String> params = WxSignUtil.parseNotify(xmlStr.toString());
        return payFacade.notifyWxPay(params);
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "notifyShoppingWxPay")
    public String notifyShoppingWxPay(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        // 组装异步回调信息
        StringBuilder xmlStr = WXHelp.getXmlStr(reader);
        // 解析结果存储在HashMap
        Map<String, String> params = WxSignUtil.parseNotify(xmlStr.toString());
        return payFacade.notifyShoppingWxPay(params);
    }

    /**
     * 微信小程序支付后回调接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("appPayCallBack")
    public AjaxResult appPayCallBack(@RequestBody OrderPayDTO orderPayDTO) {
        return BaseResult.successResult(payFacade.appPayCallBack(orderPayDTO));
    }

    /**
     * 微信小程序支付后回调接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("v2/appPayCallBack")
    public AjaxResult appPayCallBackV2(@RequestBody OrderPayDTO orderPayDTO) {
        return BaseResult.successResult(payFacade.appPayCallBackV2(orderPayDTO));
    }

    /**
     * 退款回调
     *
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("refundCallBack")
    public String refundCallBack(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        // 组装异步回调信息
        StringBuilder xmlStr = WXHelp.getXmlStr(reader);
        logger.info("refundCallBack req:" + xmlStr);
        // 解析结果存储在HashMap
        Map<String, String> params = WxSignUtil.parseXmlStr(xmlStr.toString());
        return payFacade.refundCallBack(params);
    }
}
