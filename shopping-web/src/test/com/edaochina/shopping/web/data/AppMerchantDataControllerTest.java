package com.edaochina.shopping.web.data;

import base.TestSupport;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailMerchantDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppMerchantDataControllerTest extends TestSupport {

    @Autowired
    AppMerchantDataController controller;

    @Test
    public void dataStatis() {
        TradeDetailMerchantDTO detailAgentDTO = new TradeDetailMerchantDTO();
        detailAgentDTO.setMonth("201910");
        detailAgentDTO.setMerchantId("1103837586276745216");
        AjaxResult ajaxResult = controller.dataStatis(detailAgentDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    @Test
    public void dataStatis2() {
        TradeDetailMerchantDTO detailAgentDTO = new TradeDetailMerchantDTO();
        detailAgentDTO.setMerchantId("1103837586276745216");
        AjaxResult ajaxResult = controller.dataStatis(detailAgentDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }
}