package com.edaochina.shopping.api.service.order.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.goods.GoodsMapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.service.order.PayRefundApplyService;
import com.edaochina.shopping.api.service.order.SysOrderService;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.date.DateUtil;
import com.edaochina.shopping.domain.constants.CountDownConstants;
import com.edaochina.shopping.domain.dto.order.QuerySysWriteOffOrderDTO;
import com.edaochina.shopping.domain.dto.order.SysOrderDto;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.vo.order.ExportSysOrderVO;
import com.edaochina.shopping.domain.vo.order.SysOrderVO;
import com.edaochina.shopping.domain.vo.trade.SysShareProfitVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Service
public class SysOrderServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements SysOrderService {

    private Logger logger = LoggerFactory.getLogger(SysOrderServiceImpl.class);

    @Resource
    OrderMainMapper orderMainMapper;

    @Resource
    GoodsMapper goodsMapper;


    @Autowired
    private PayRefundApplyService payRefundApplyService;

    /**
     * 订单过期计时器
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderUserStatusForTask() {
        LoggerUtils.info(SysOrderServiceImpl.class, "订单过期计时器,开始更新订单");
        int num = 0;
        do {
            List<OrderMain> list = orderMainMapper.queryUnUsedOrder();
            for (OrderMain order : list) {
                Goods goods = goodsMapper.selectById(order.getGoodsId());
                if (goods != null) {
                    // 使用倒计时（10自定义，20永久，30三个月，40一个月，50：7天）
                    Integer useCountDown = goods.getUseCountDown();
                    // 使用自定义时间（单位:小时）
                    double useCountDownTime = goods.getUseCountDownTime().doubleValue();
                    // 获取下单时间
                    LocalDateTime collectTime = order.getCreateTime();
                    // 不是永久
                    if (!CountDownConstants.FOREVER.toString().equals(useCountDown.toString())) {
                        // 自定义
                        if (CountDownConstants.DEFINE.toString().equals(useCountDown.toString())) {
                            Date date = DateUtil.addMinutes(DateUtil.localDateTime2Date(collectTime), (int) (useCountDownTime * 60));
                            if (new Date().after(date)) {
                                //  申请退款
                                payRefundApplyService.overTimeApplyRefund(order);
                                updateById(order);
                            }
                        }
                        // 三个月
                        if (CountDownConstants.THREE_MONTH.toString().equals(useCountDown.toString())) {
                            Date date = DateUtil.addMonthFroDate(DateUtil.localDateTime2Date(collectTime), 3);
                            if (new Date().after(date)) {
                                //  申请退款
                                payRefundApplyService.overTimeApplyRefund(order);
                                updateById(order);
                            }
                        }
                        // 一个月
                        if (CountDownConstants.ONE_MONTH.toString().equals(useCountDown.toString())) {
                            Date date = DateUtil.addMonthFroDate(DateUtil.localDateTime2Date(collectTime), 1);
                            if (new Date().after(date)) {
                                //  申请退款
                                payRefundApplyService.overTimeApplyRefund(order);
                                updateById(order);
                            }
                        }
                        // 7天
                        if (CountDownConstants.SEVEN_DAY.toString().equals(useCountDown.toString())) {
                            Date date = DateUtil.addDayFroDate(DateUtil.localDateTime2Date(collectTime), 7);
                            if (new Date().after(date)) {
                                //  申请退款
                                payRefundApplyService.overTimeApplyRefund(order);
                                updateById(order);
                            }
                        }
                        // 自定义时间段
                        if (CountDownConstants.DEFIND_TIME.toString().equals(useCountDown.toString())) {
                            if (new Date().after(DateUtil.localDateTime2Date(goods.getWriteOffEndTime()))) {
                                //  申请退款
                                payRefundApplyService.overTimeApplyRefund(order);
                                updateById(order);
                            }
                        }
                    }
                }
            }
        } while (num == 100);
        return true;
    }

    @Override
    public List<ExportSysOrderVO> getExportOrderList(SysOrderDto sysOrderDto) {
        return orderMainMapper.getExportOrderList(sysOrderDto);
    }

    @Override
    public Integer getExportOrderCount(SysOrderDto sysOrderDto) {
        return orderMainMapper.getExportOrderCount(sysOrderDto);
    }

    /**
     * 查询未分润的订单
     *
     * @return
     */
    @Override
    public List<OrderMain> queryHasNotCalcOrder() {
        return orderMainMapper.queryHasNotCalcOrder();

    }


    @Override
    public List<SysOrderVO> sysQueryOrders(SysOrderDto querySysOrderDTO) {
        return orderMainMapper.sysQueryOrders(querySysOrderDTO);
    }

    @Override
    public List<OrderMain> getWriteOffList(QuerySysWriteOffOrderDTO querySysOrderDTO) {
        return orderMainMapper.getWriteOffList(querySysOrderDTO);
    }

    @Override
    public List<SysShareProfitVo> getShareOrder(SysShareGoodsProfitDTO dto) {
        return orderMainMapper.getShareOrder(dto);
    }
}