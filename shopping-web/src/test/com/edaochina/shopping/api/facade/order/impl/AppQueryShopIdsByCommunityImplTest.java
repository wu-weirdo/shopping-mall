package com.edaochina.shopping.api.facade.order.impl;

import base.TestSupport;
import com.edaochina.shopping.api.facade.order.AppOrderFacade;
import com.edaochina.shopping.domain.dto.order.WriteOffDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @since : 2019/6/24 11:22
 */
public class AppQueryShopIdsByCommunityImplTest extends TestSupport {

    @Autowired
    private AppOrderFacade appOrderFacade;

    @Test
    public void test() {
        WriteOffDTO dto = new WriteOffDTO();
        dto.setId("1086570224209100800");
        appOrderFacade.writeOff(dto);
    }

}
