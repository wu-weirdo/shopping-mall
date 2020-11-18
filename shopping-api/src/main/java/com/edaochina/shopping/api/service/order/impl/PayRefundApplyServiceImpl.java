package com.edaochina.shopping.api.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.api.dao.order.MemberOrderMapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.dao.order.PayRefundApplyMapper;
import com.edaochina.shopping.api.dao.trade.PayRefundImgsMapper;
import com.edaochina.shopping.api.dao.trade.PayRefundInfoMapper;
import com.edaochina.shopping.api.facade.order.AppOrderFacade;
import com.edaochina.shopping.api.service.order.PayRefundApplyService;
import com.edaochina.shopping.api.service.pay.PayApi;
import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.StringUtil;
import com.edaochina.shopping.common.utils.ThreadUtils;
import com.edaochina.shopping.common.utils.date.DateUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.OrderStatusConstants;
import com.edaochina.shopping.domain.constants.OrderTypeConstants;
import com.edaochina.shopping.domain.constants.PayRefundApplyStatus;
import com.edaochina.shopping.domain.dto.order.AppMerchantRefundOrderDTO;
import com.edaochina.shopping.domain.dto.order.QueryAppOrderDTO;
import com.edaochina.shopping.domain.dto.trade.RefundApplyDTO;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.entity.trade.PayRefundImgs;
import com.edaochina.shopping.domain.entity.trade.PayRefundInfo;
import com.edaochina.shopping.domain.vo.order.AppMerchantRefundOrderVO;
import com.edaochina.shopping.domain.vo.order.AppOrderDetailVO;
import com.edaochina.shopping.domain.vo.order.AppRefundApplyVO;
import com.edaochina.shopping.domain.vo.order.AppRefundOrderDetailVO;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 退款申请表 服务实现类
 * </p>
 *
 * @since 2019-06-17
 */
@Service
public class PayRefundApplyServiceImpl implements PayRefundApplyService {

    private Logger logger = LoggerFactory.getLogger(PayRefundApplyServiceImpl.class);

    @Resource
    private PayRefundApplyMapper refundApplyMapper;

    @Resource
    private PayRefundImgsMapper refundImgsMapper;

    @Resource
    PayRefundInfoMapper payRefundInfoMapper;

    @Resource
    private OrderMainMapper orderMainMapper;

    @Autowired
    private CommonService commonService;

    @Resource
    private MemberOrderMapper memberOrderMapper;

    @Autowired
    private AppOrderFacade appOrderFacade;

    @Value("${wx.refund-call-callBack-url}")
    private String callBackUrl;

    @Autowired
    private PayApi payApi;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRefundApply(RefundApplyDTO dto) {
        logger.info("insertRefundApply req:" + JSON.toJSONString(dto));
        OrderMain order = orderMainMapper.selectById(dto.getOrderId());
        checkRefundApply(dto, order);
        PayRefundApply apply = new PayRefundApply();
        BeanUtils.copyProperties(dto, apply);
        // order.getActualPrice() 是退款金额， payRecordInfo 的money是退款订单总金额
        apply.setRefundFee(order.getActualPrice());
        apply.setTotalFee(order.getTotalPrice());
        // 设置支付单号
        apply.setOutOrderNo(order.getOrderNo());
        // 订单类型
        apply.setOrderType(OrderTypeConstants.SECKILL_ORDER.getCode());
        apply.setShoperId(order.getShoperId());
        apply.setApplyRefundTime(LocalDateTime.now());
        apply.setUserPhone(order.getPhone());
        // 更新退款为有退款状态
        orderMainMapper.updateOrderRefundStatus(order.getId(), 1);
        refundApplyMapper.insertRefundApply(apply);
        if (CollectionUtils.isNotEmpty(dto.getImgs())) {
            for (int i = 0; i < dto.getImgs().size(); i++) {
                saveRefundImg(dto.getImgs().get(i), apply.getId());
            }
        }
        return 1;
    }

    private void checkRefundApply(RefundApplyDTO dto, OrderMain order) {
        if (order.getStatus() != 20 && order.getStatus() != 60 && order.getStatus() != 90) {
            throw new YidaoException(ReturnData.REFUND_ERROR.getCode(), ReturnData.REFUND_ERROR.getMsg());
        }
        // 判断是否有退款
        if (0 != order.getHasRefund()) {
            throw new YidaoException(ReturnData.REFUND_HAS_ERROR);
        }
        if (StringUtil.containsEmoji(dto.getRefundExplain())) {
            throw new YidaoException(ReturnData.ERROR_ORDER_REASON_ERROR);
        }
    }

    private void saveRefundImg(String imgUrl, Integer refundApplyId) {
        ThreadUtils.execute(() -> {
            PayRefundImgs imgs = new PayRefundImgs();
            imgs.setRefundApplyId(refundApplyId);
            // 针对app进行改造
            if (imgUrl.startsWith("http://cd-shopping.oss-cn-hangzhou.aliyuncs.com") || imgUrl.startsWith("https://cd-shopping.oss-cn-hangzhou.aliyuncs.com")) {
                imgs.setImgUrl(imgUrl);
            } else {
                imgs.setImgUrl(commonService.appUploadImage(imgUrl));
            }
            refundImgsMapper.insertImg(imgs);
        });
    }

    @Override
    public AppRefundApplyVO refundApplyInfo(Integer id) {
        AppRefundApplyVO refundApplyVO = refundApplyMapper.refundApplyInfo(id);
        List<String> imgs = refundImgsMapper.selectByRefundApplyId(refundApplyVO.getId());
        refundApplyVO.setRefundImgs(imgs);
        return refundApplyVO;
    }

    @Override
    public int cancelApply(Integer id) {
        logger.info("cancelApply req:" + id);
        PayRefundApply refundApply = refundApplyMapper.selectById(id);
        // 非处理中的订单不可撤销
        if (PayRefundApplyStatus.RefundRemitStatus.WAIT_REMIT != refundApply.getRemitStatus()) {
            throw new YidaoException(ReturnData.REFUND_HAS_HANDLER);
        }
        // 取消退款则将订单退款修改为无退款
        orderMainMapper.updateOrderRefundStatus(refundApply.getOrderId(), 0);
        return refundApplyMapper.updateApplyStatus(id, 20);
    }

    @Override
    public PageResult merchantRefundOrders(AppMerchantRefundOrderDTO dto) {
        logger.info("merchantRefundOrders req:" + JSON.toJSONString(dto));
        PageHelperUtils.setPageHelper(dto.getPages());
        List<AppMerchantRefundOrderVO> refundOrderVOList = refundApplyMapper.merchantRefundOrders(dto);
        return PageHelperUtils.parseToPageResult(refundOrderVOList);
    }

    @Override
    public int merchantRefundOrderCount(AppMerchantRefundOrderDTO dto) {
        logger.info("merchantRefundOrderCount req:" + JSON.toJSONString(dto));
        dto.setParamStatus("0");
        return refundApplyMapper.merchantRefundOrderCount(dto);
    }

    @Override
    public AppRefundOrderDetailVO RefundOrderDetail(Integer id) {
        logger.info("RefundOrderDetail req refundApplyId:" + id);
        QueryAppOrderDTO queryAppOrderDTO = new QueryAppOrderDTO();
        PayRefundApply refundApply = refundApplyMapper.selectById(id);
        if (refundApply == null) {
            throw new YidaoException("没有此id的退款信息!");
        }
        queryAppOrderDTO.setId(refundApply.getOrderId());
        queryAppOrderDTO.setOrigin("2");
        AppOrderDetailVO appOrderDetailVO = appOrderFacade.getOrderDetail(queryAppOrderDTO);
        AppRefundOrderDetailVO detailVO = new AppRefundOrderDetailVO();
        detailVO.setAppOrderDetailVO(appOrderDetailVO);
        AppRefundApplyVO applyVO = refundApplyInfo(id);
        detailVO.setAppRefundApplyVO(applyVO);
        return detailVO;
    }

    @Override
    public int refuseRefund(Integer id, String refuseReason) {
        logger.info("refuseRefund req:" + "[id:" + id + ",refuseReason:" + refuseReason + "]");
        //  根据id查询退款
        PayRefundApply payRefundApply = refundApplyMapper.selectById(id);
        // 校验
        if (payRefundApply.getRefundStatus() != PayRefundApplyStatus.RefundStatus.PROCESSING && payRefundApply.getShoperHandleStatus() != 10) {
            throw new YidaoException(ReturnData.REFUND_CANNOT_REFUSE);
        }

        //  判断退款金额是否小于100元
        if (payRefundApply.getRefundFee().compareTo(new BigDecimal(100)) < 0) {
            refundApplyMapper.updateFefundStatus(id, PayRefundApplyStatus.RefundStatus.REJECTED);
            // 更改订单中退款状态
            orderMainMapper.updateOrderRefundStatus(payRefundApply.getOrderId(), 2);
        }
        return refundApplyMapper.refuseRefund(id, refuseReason);
    }

    @Override
    public int agreeRefund(Integer id) {
        logger.info("agreeRefund req:" + id);
        //  根据id查询退款
        PayRefundApply payRefundApply = refundApplyMapper.selectById(id);
        if (payRefundApply == null) {
            throw new YidaoException("退款信息不存在");
        }
        // 校验
        if (payRefundApply.getRefundStatus() != PayRefundApplyStatus.RefundStatus.PROCESSING && payRefundApply.getShoperHandleStatus() != 10) {
            throw new YidaoException(ReturnData.REFUND_CANNOT_AGREE);
        }
        //  判断退款金额是否小于100元
        if (payRefundApply.getRefundFee().compareTo(new BigDecimal(100)) < 0) {
            refundApplyMapper.updateFefundStatus(id, PayRefundApplyStatus.RefundStatus.TO_BE_REFUNDED);
        }
        // 小于100元将退款状态改为同意退款
        return refundApplyMapper.agreeRefund(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundRemit(PayRefundApply payRefundApply) throws WxPayException {
        logger.info("refunRemit params:" + JSON.toJSONString(payRefundApply));
        //  检查退款打款，如果可以打款则打款，如果不可以则取消退款申请
        boolean checkRefundResult = checkRefundRemit(payRefundApply);
        if (!checkRefundResult) {
            // 取消退款
            cancelApply(payRefundApply.getId());
            return;
        }

        String outRefundNo = IdUtils.nextId();
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        refundRequest.setNotifyUrl(callBackUrl);
        refundRequest.setOutTradeNo(payRefundApply.getOutOrderNo());
        refundRequest.setTotalFee(payRefundApply.getTotalFee().multiply(BigDecimal.valueOf(100)).intValue());
        refundRequest.setRefundFee(payRefundApply.getRefundFee().multiply(BigDecimal.valueOf(100)).intValue());
        refundRequest.setOutRefundNo(outRefundNo);
        WxPayRefundResult result = payApi.refund(refundRequest);
        //保存退款记录
        PayRefundInfo payRefundInfo = new PayRefundInfo();
        payRefundInfo.setTransactionId(result.getTransactionId());
        payRefundInfo.setOutTradeNo(result.getOutTradeNo());
        payRefundInfo.setOutRefundNo(result.getOutRefundNo());
        payRefundInfo.setTotalFee(new BigDecimal(result.getTotalFee()));
        payRefundInfo.setRefundFee(new BigDecimal(result.getRefundFee()));
        payRefundInfo.setReturnResult(JSONObject.toJSONString(result));

        payRefundInfo.setRefundApplyId(payRefundApply.getId());
        // TODO 待改成发送事件异步修改订单或者会员订单状态
        if (payRefundApply.getOrderType() == OrderTypeConstants.SECKILL_ORDER.getCode()) {
            OrderMain orderMain = new OrderMain();
            orderMain.setId(payRefundApply.getOrderId());
            orderMain.setStatus(OrderStatusConstants.REFUNDED);
            orderMainMapper.updateById(orderMain);
        } else if (payRefundApply.getOrderType() == OrderTypeConstants.USER_MEMBER_ORDER.getCode()) {
            // 更新支付状态
            memberOrderMapper.updatePayStatusByOrderId(payRefundApply.getOutOrderNo(), 2);
        }
        logger.info("refundResult:" + result);
        if (result != null && "SUCCESS".equals(result.getReturnCode()) && "SUCCESS".equals(result.getReturnCode())) {
            LoggerUtils.info(this.getClass(), "微信退款成功：" + result.getReturnCode());
            payRefundInfo.setRefundResult(CommonConstants.TWO_INT);
            payRefundInfoMapper.insertRefundInfo(payRefundInfo);
            // 回调时更新更新退款状态
            updateToRemitedCommit(payRefundApply.getId());
        } else {
            payRefundInfo.setRefundResult(CommonConstants.ZERO_INT);
            payRefundInfoMapper.insertRefundInfo(payRefundInfo);
            updateToRemitedFail(payRefundApply.getId());
        }
    }

    /**
     * 检查退款打款
     *
     * @param payRefundApply
     * @return 是否可以打款，false不可以打款取消打款申请,true可以打款
     */
    private boolean checkRefundRemit(PayRefundApply payRefundApply) {
        if (payRefundApply.getOrderType() == OrderTypeConstants.SECKILL_ORDER.getCode()) {
            OrderMain orderMain = orderMainMapper.selectById(payRefundApply.getOrderId());
            return orderMain.getStatus() == 20 || orderMain.getStatus() == 90 || orderMain.getStatus() == 21 || orderMain.getStatus() == 91;
        } else {
            return true;
        }
    }

    @Override
    public List<PayRefundApply> queryWaitRemit() {
        return refundApplyMapper.queryWaitRemit();
    }

    @Override
    public List<PayRefundApply> queryMerchantUnHandler() {
        return refundApplyMapper.queryByMerchantAndHandlerStatus(PayRefundApplyStatus.RefundStatus.PROCESSING, 10, 100);
    }

    @Override
    public void updateMerchantUnHandlerToUnConnect() {
        Date data = DateUtil.addDayFroDate(new Date(), -1);
        refundApplyMapper.updateMerchantUnHandlerToUnConnect(data);
    }

    @Override
    public AppRefundApplyVO queryRefundApplyInfoByOrderId(String id) {
        AppRefundApplyVO refundApplyVO = refundApplyMapper.queryRefundApplyInfoByOrderId(id);
        if (refundApplyVO == null) {
            return refundApplyVO;
        }
        List<String> imgs = refundImgsMapper.selectByRefundApplyId(refundApplyVO.getId());
        refundApplyVO.setRefundImgs(imgs);
        return refundApplyVO;
    }

    /**
     * 更新退款打款为已提交
     *
     * @param refundApplyId
     */
    private void updateToRemitedCommit(Integer refundApplyId) {
        refundApplyMapper.updateRemitStatus(refundApplyId, PayRefundApplyStatus.RefundRemitStatus.REMIT_SUBMMIT);
    }

    /**
     * 更新退款未打款失败
     *
     * @param refundApplyId
     */
    private void updateToRemitedFail(Integer refundApplyId) {
        refundApplyMapper.updateRemitStatus(refundApplyId, PayRefundApplyStatus.RefundRemitStatus.REMIT_FAIL);
    }


    @Override
    public void overTimeApplyRefund(OrderMain orderMain) {
        PayRefundApply apply = new PayRefundApply();
        apply.setOrderId(orderMain.getId());
        apply.setHandleStatus(PayRefundApplyStatus.HandleStatus.PENDING);
        apply.setRefundStatus(PayRefundApplyStatus.RefundStatus.PROCESSING);
        apply.setShoperHandleStatus(10);
        apply.setApplyRefundTime(LocalDateTime.now());
        apply.setUserId(orderMain.getUserId());
        apply.setUserPhone(orderMain.getPhone());
        apply.setTotalFee(orderMain.getActualPrice());
        apply.setRefundMethod("订单过期自动退款");
        apply.setRefundReason("订单过期");
        apply.setApplyFee(orderMain.getActualPrice().toString());
        apply.setRefundFee(orderMain.getActualPrice());
        apply.setShoperId(orderMain.getShoperId());
        refundApplyMapper.insertRefundApply(apply);
        // 订单改为有退款
        orderMainMapper.updateOrderRefundStatus(orderMain.getId(), 3);
    }
}

