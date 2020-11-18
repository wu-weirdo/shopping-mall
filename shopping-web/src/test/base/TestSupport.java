package base;

import com.edaochina.shopping.api.dao.goods.GoodsMapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.dao.order.PayRefundApplyMapper;
import com.edaochina.shopping.api.dao.user.CommunityMapper;
import com.edaochina.shopping.api.dao.user.SysUserMerchantMapper;
import com.edaochina.shopping.api.dao.user.UserMapper;
import com.edaochina.shopping.web.WebApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;


/**
 * 类TestSupport.java的实现描述：单元测试支持类
 *
 * @author weihui 2018/10/25 10:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback()
@Transactional(transactionManager = "transactionManager")
@SpringBootTest(classes = WebApplication.class)
public class TestSupport {

    protected Fake fake;
    @Autowired
    PayRefundApplyMapper payRefundApplyMapper;
    @Autowired
    OrderMainMapper orderMainMapper;
    @Autowired
    SysUserMerchantMapper merchantMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CommunityMapper communityMapper;

    @PostConstruct
    public void init() {
        fake = new Fake(payRefundApplyMapper, orderMainMapper, merchantMapper, userMapper, goodsMapper, communityMapper);
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 单元测试打印
     *
     * @param obj
     * @author weihui 2017年7月27日 上午10:04:12
     */
    protected void printLog(Object obj) {
        logger.info(obj.toString());
    }

    @Test
    @Ignore
    public void testIgnore() {

    }

}
