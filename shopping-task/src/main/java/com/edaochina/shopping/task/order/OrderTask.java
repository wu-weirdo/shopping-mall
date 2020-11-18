package com.edaochina.shopping.task.order;

import com.edaochina.shopping.api.facade.order.PayRefundApplyFacede;
import com.edaochina.shopping.api.facade.order.SysOrderFacade;
import com.edaochina.shopping.api.facade.trade.WithdrawalRecordFacade;
import com.edaochina.shopping.api.service.order.SysOrderService;
import com.edaochina.shopping.common.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
@Component
public class OrderTask {

    @Resource
    private SysOrderService sysOrderService;

    @Resource
    private SysOrderFacade sysOrderFacade;

    @Autowired
    private PayRefundApplyFacede payRefundApplyFacede;

    @Resource
    private WithdrawalRecordFacade withdrawalRecordFacade;

    /**
     * 订单过期计时器
     * 5分钟执行一次，减少服务器压力
     */
    @Scheduled(cron = "30 0/30 * * * ?")
    public void updateOrderUserStatusForTask() {
        boolean update = sysOrderService.updateOrderUserStatusForTask();
        if (update) {
            LoggerUtils.info(OrderTask.class, "订单过期计时器,成功更新订单");
        } else {
            LoggerUtils.info(OrderTask.class, "订单过期计时器,结束更新订单");
        }
    }

    /**
     * 未支付的订单，修改状态
     * 5分钟执行一次，减少服务器压力
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateCollectOrderStatusForTask() {
        LoggerUtils.info(OrderTask.class, "开始处理未支付的订单，并修改状态");
        sysOrderFacade.updateCollectOrderStatusForTask();
        LoggerUtils.info(OrderTask.class, "结束未支付的订单");
    }

    /**
     * 退款打款给用户  每天下午6点左右
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void refundRemitTask() {
        LoggerUtils.info(OrderTask.class, "开始处理同意退款未到账的退款申请");
        payRefundApplyFacede.refundsRemit();
        LoggerUtils.info(OrderTask.class, "结束处理同意退款未到账的退款申请");
    }

    /**
     * 处理提现未知状态时
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void withdrawalStatusHandler() {
        LoggerUtils.info(OrderTask.class, "开始处理打款状态查询");
        withdrawalRecordFacade.withdrawalStatusHandler();
        LoggerUtils.info(OrderTask.class, "开始处理打款状态查询");
    }

}
