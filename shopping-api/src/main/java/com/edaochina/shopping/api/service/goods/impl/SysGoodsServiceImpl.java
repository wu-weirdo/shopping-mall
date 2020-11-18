package com.edaochina.shopping.api.service.goods.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.goods.GoodsCollectInfoMapper;
import com.edaochina.shopping.api.dao.goods.GoodsMapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.dao.order.PayRefundApplyMapper;
import com.edaochina.shopping.api.dao.trade.ChainRecipientDetailDao;
import com.edaochina.shopping.api.service.goods.SysGoodsService;
import com.edaochina.shopping.api.service.order.ApprovalRecordService;
import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.ThreadUtils;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.constants.PayRefundApplyStatus;
import com.edaochina.shopping.domain.dto.goods.ApprovalGoodsDTO;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.goods.GoodsCollectInfo;
import com.edaochina.shopping.domain.entity.order.ApprovalRecord;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.vo.goods.SysGoodsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Service
public class SysGoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements SysGoodsService {

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    OrderMainMapper orderMainMapper;

    @Resource
    GoodsCollectInfoMapper goodsCollectInfoMapper;

    @Resource
    PayRefundApplyMapper payRefundApplyMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ApprovalRecordService approvalRecordService;

    @Resource
    private ChainRecipientDetailDao detailDao;


    @Override
    public int querySticNumByCommuntyId(String communtyId) {
        return goodsMapper.querySticNumByCommuntyId(communtyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGoodsToCollectSuccess(GoodsCollectInfo goodsCollectInfo) {
        // 更改订单为待提货
        updateOrderToCollectSuccess(goodsCollectInfo.getId());
        //orderMainMapper.updateOrderToCollectSuccess(goodsCollectInfo.getId());
        // 更改拼团信息为已删除
        //goodsCollectInfoMapper.updateTodel(goodsCollectInfo.getId());
        // 拼团更改为结束
        goodsCollectInfoMapper.updateToEnd(goodsCollectInfo.getId());
        // 更改商品为普通商品
        //goodsMapper.updateCollectFlag(goodsCollectInfo.getGoodsId(), 0);
        // 更改为可发货
        detailDao.updateCanApplyStatus(goodsCollectInfo.getGoodsId(), 1);
        goodsMapper.updatePutawayStatus(goodsCollectInfo.getGoodsId(), 20);
    }

    private void updateOrderToCollectSuccess(int collectInfoId) {
        List<String> ids = orderMainMapper.queryByCollectInfo(collectInfoId)
                .stream().map(OrderMain::getId).collect(Collectors.toList());
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(OrderMain::getId, ids).in(OrderMain::getStatus, 21, 91);
        OrderMain orderMain = new OrderMain();
        orderMain.setStatus(20);
        orderMainMapper.update(orderMain, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGoodsToCollectFail(GoodsCollectInfo goodsCollectInfo) {
        // 申请退款
        collectOrderApplyRefund(goodsCollectInfo.getId());
        // 更改拼团信息为已删除
        //goodsCollectInfoMapper.updateTodel(goodsCollectInfo.getId());
        // 拼团更改为结束
        goodsCollectInfoMapper.updateToEnd(goodsCollectInfo.getId());
        // 更改商品为普通商品
        //goodsMapper.updateCollectFlag(goodsCollectInfo.getGoodsId(), 0);
        // 更改商品为下架
        goodsMapper.updatePutawayStatus(goodsCollectInfo.getGoodsId(), 20);
    }

    @Override
    public int queryMerchantPutawayNum(String merchantId) {
        return goodsMapper.queryMerchantPutawayNum(merchantId);
    }

    @Override
    public List<SysGoodsVO> getSysGoodsList(QueryGoodsDTO queryGoodsDTO) {
        return goodsMapper.getSysGoodsList(queryGoodsDTO);
    }

    @Override
    public void approvalGoods(String id, ApprovalGoodsDTO dto) {
        Goods goods = getById(id);
        checkHot(goods);
        // 修改商品审批状态
        Goods updateGoods = new Goods();
        updateGoods.setId(id);
        updateGoods.setApprovalFlag(dto.getApprovalFlag());
        updateGoods.setReason(dto.getReason());
        updateGoods.setUpdateTime(LocalDateTime.now());
        updateById(updateGoods);

        // 添加商品审批记录
        ApprovalRecord approvalRecord = new ApprovalRecord();
        BeanUtils.copyProperties(goods, approvalRecord);
        approvalRecord.setId(IdUtils.nextId());
        approvalRecord.setGoodsId(id);
        approvalRecord.setApprovalFlag(dto.getApprovalFlag());
        approvalRecord.setReason(dto.getReason());
        approvalRecord.setApprovalTime(LocalDateTime.now());
        approvalRecordService.save(approvalRecord);
    }

    @Override
    public void setQrCode(String goodsId) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                String goodsQrCode = commonService.getGoodsQrImage(goodsId);
                Goods goods1 = new Goods();
                goods1.setId(goodsId);
                goods1.setQrCode(goodsQrCode);
                updateById(goods1);
            }
        });
    }

    private void collectOrderApplyRefund(Integer id) {
        List<OrderMain> orderMains = orderMainMapper.queryByCollectInfo(id);
        orderMains.forEach(orderMain -> {
            PayRefundApply apply = new PayRefundApply();
            apply.setOrderId(orderMain.getId());
            apply.setHandleStatus(PayRefundApplyStatus.HandleStatus.AGREED);
            apply.setRefundStatus(PayRefundApplyStatus.RefundStatus.TO_BE_REFUNDED);
            apply.setShoperHandleStatus(30);
            apply.setApplyRefundTime(LocalDateTime.now());
            apply.setUserId(orderMain.getUserId());
            apply.setUserPhone(orderMain.getPhone());
            apply.setTotalFee(orderMain.getActualPrice());
            apply.setRefundMethod("拼团失败自动退款");
            apply.setRefundReason("拼团失败");
            apply.setApplyFee(orderMain.getActualPrice().toString());
            apply.setRefundFee(orderMain.getActualPrice());
            apply.setShoperId(orderMain.getShoperId());
            payRefundApplyMapper.insertRefundApply(apply);
            // 订单改为有退款
            orderMainMapper.updateOrderRefundStatus(orderMain.getId(), 3);
        });
    }

    private void checkHot(Goods goods) {
        JWTBean bean = JWTThreadLocalHelper.get();
        if (!bean.isAdmin() && !bean.isAgent() && goods.getHot()) {
            throw new YidaoException("管理员与区县合伙人才能审核爆品商品!");
        }
    }
}
