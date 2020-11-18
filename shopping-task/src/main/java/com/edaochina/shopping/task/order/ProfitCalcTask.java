package com.edaochina.shopping.task.order;

import com.edaochina.shopping.api.facade.profit.MemberOrderProfitFacade;
import com.edaochina.shopping.api.facade.profit.ProfitFacade;
import com.edaochina.shopping.common.utils.LoggerUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 分润计算定时器
 *
 * @author laoyu
 */
@Configuration
@EnableScheduling
@Component
public class ProfitCalcTask {

    @Resource
    ProfitFacade payRecordFacade;

    @Resource
    MemberOrderProfitFacade memberOrderProfitFacade;

    /**
     * 订单明细处理 (1小时处理一次)
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void calcProfitInsertDetail() {
        LoggerUtils.info(ProfitCalcTask.class, "分润计算定时器开始执行");
        payRecordFacade.calcProfitAndInsertDetail();
        LoggerUtils.info(ProfitCalcTask.class, "分润计算定时器执行完成");
    }

    /**
     * 会员分润 1天1次,次日的凌晨00:30
     */
    @Scheduled(cron = "0 30 0 * * ?")
    //@Scheduled(cron = "0 30 18  * * ?")
    public void calcMemberInsertDetail() {
        LoggerUtils.info(ProfitCalcTask.class, "会员分润计算定时器开始执行");
        memberOrderProfitFacade.calcMemberInsertDetail();
        LoggerUtils.info(ProfitCalcTask.class, "会员分润计算定时器执行完成");
    }


    /**
     * 区域分润跑批
     */
    @Scheduled(cron = "0 1 0 * * ?")
    //@Scheduled(cron = "0 15 18  * * ?")
    public void areaProfitTask() {
        LoggerUtils.info(ProfitCalcTask.class, "年终补充会员分润计算定时器开始执行");
        memberOrderProfitFacade.areaProfitCalculate();
        LoggerUtils.info(ProfitCalcTask.class, "年终补充会员分润计算定时器执行完成");
    }

}
