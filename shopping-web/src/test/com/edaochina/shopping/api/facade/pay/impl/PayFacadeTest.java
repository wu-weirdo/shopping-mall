package com.edaochina.shopping.api.facade.pay.impl;

import base.TestSupport;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : TODO
 * @since : 2019/6/28 11:41
 */
public class PayFacadeTest extends TestSupport {

    @Autowired
    PayFacadeImpl payFacade;

    @Autowired
    OrderMainMapper orderMainMapper;

    @Test
    public void test() {
//        OrderMain orderMain = orderMainMapper.selectById("1086570224209100800");
//        payFacade.updayeUserCountyInfo(orderMain);
    }

}
