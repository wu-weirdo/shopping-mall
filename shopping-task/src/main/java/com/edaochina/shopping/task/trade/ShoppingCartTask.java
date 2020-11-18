package com.edaochina.shopping.task.trade;

import com.edaochina.shopping.api.service.order.ShoppingCartInfoService;
import com.edaochina.shopping.common.utils.LoggerUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jintian
 * @date 2019/4/23 15:07
 */
@Configuration
@EnableScheduling
@Component
public class ShoppingCartTask {

    @Resource
    private ShoppingCartInfoService shoppingCartInfoService;

    /**
     * 过时购物车清除
     * 3.1 购物车定时清除取消
     */
    //@Scheduled(cron = "0 0/10 * * * ?")
    public void cleanOverTimeShoppingCart() {
        LoggerUtils.info(ShoppingCartTask.class, "清除过时购物车开始");
        shoppingCartInfoService.cleanOverTimeShoppingCart();
        LoggerUtils.info(ShoppingCartTask.class, "清除过时购物车结束");
    }
}
