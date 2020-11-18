package com.edaochina.shopping.web.data;

import base.TestSupport;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppAgentDataControllerTest extends TestSupport {

    @Autowired
    AppAgentDataController controller;

    @Test
    public void dataStatis() {
        TradeDetailAgentDTO detailAgentDTO = new TradeDetailAgentDTO();
        detailAgentDTO.setMonth("201910");
        detailAgentDTO.setAgentId("1196250571707777024");
        AjaxResult ajaxResult = controller.dataStatis(detailAgentDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    @Test
    public void dataStatis2() {
        TradeDetailAgentDTO detailAgentDTO = new TradeDetailAgentDTO();
        detailAgentDTO.setAgentId("1196250571707777024");
        AjaxResult ajaxResult = controller.dataStatis(detailAgentDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }
}