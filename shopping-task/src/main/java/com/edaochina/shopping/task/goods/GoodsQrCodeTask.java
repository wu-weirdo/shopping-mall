package com.edaochina.shopping.task.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.service.goods.SysGoodsService;
import com.edaochina.shopping.domain.entity.goods.Goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jintian
 * @date 2019/5/14 13:58
 */
@Configuration
@EnableScheduling
@Component
public class GoodsQrCodeTask {
    private Logger logger = LoggerFactory.getLogger(GoodsQrCodeTask.class);

    @Autowired
    private SysGoodsService goodsService;

    private static boolean QR_CODE_INIT = false;

    //@Scheduled(cron = "0 * * * * ?")
    public void initMerchantQrCode() {
        logger.info("initMerchantQrCode start");
        if (QR_CODE_INIT) {
            return;
        }
        QR_CODE_INIT = true;
        List<Goods> goodsList = null;
        do {
            QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("delete_flag", 10);
            queryWrapper.eq("qr_code", "");
            goodsList = goodsService.list(queryWrapper);
            goodsList.forEach(goods -> {
                goodsService.setQrCode(goods.getId());
            });
        } while (goodsList.size() > 0);
        QR_CODE_INIT = false;
        logger.info("initMerchantQrCode start");
    }
}
