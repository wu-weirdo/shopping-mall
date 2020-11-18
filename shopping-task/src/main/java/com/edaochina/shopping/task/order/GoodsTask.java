package com.edaochina.shopping.task.order;

import com.edaochina.shopping.api.facade.goods.SysGoodsFacade;
import com.edaochina.shopping.common.utils.LoggerUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
@Component
public class GoodsTask {

    @Resource
    private SysGoodsFacade sysGoodsFacade;

    //  商品拼团到期后自动修改为商品为非拼团商品
    // 3.5 拼团不该为非拼团，而是自动下架
    @Scheduled(cron = "30 0/20 * * * ?")
    public void collectOverTimeTask() {
        LoggerUtils.info(GoodsTask.class, "开始跑批拼团商品结束，更改商品为普通非拼团商品");
        sysGoodsFacade.collectOverTimeTask();
        LoggerUtils.info(GoodsTask.class, "结束跑批拼团商品结束，更改商品为普通非拼团商品");
    }


    /*  *//**
     * 商品活动倒计时计时器
     * 修改成5分钟一次
     * 3.1 无商品活动倒计时了
     *//*
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateGoodsyValidStatusForTask() {
        boolean update = false;
        update = sysGoodsService.updateGoodsyValidStatus();
        if (update) {
            LoggerUtils.info(GoodsTask.class, "商品活动倒计时结束,成功更新商品");
        } else {
            LoggerUtils.info(GoodsTask.class, "商品活动倒计时结束,结束更新商品");
        }
    }
*/
}
