package com.edaochina.shopping.api.service.trade.impl;

import base.TestSupport;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.trade.TradeDetailMerchantDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jintian
 * @date 2019/3/12 16:52
 */
public class TradeDetailMerchantServiceImplTest extends TestSupport {

    @Autowired
    private TradeDetailMerchantServiceImpl tradeDetailMerchantService;

    @Test
    public void dataStatis() {
        TradeDetailMerchantDTO dto = new TradeDetailMerchantDTO();
        Pages page = new Pages();
        page.setPageIndex(1);
        dto.setMerchantId("111");
        tradeDetailMerchantService.dataStatis(dto);
    }
}
