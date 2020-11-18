package goods;

import base.TestSupport;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.domain.vo.goods.AppGoodDetailVO;
import com.edaochina.shopping.web.goods.AppGoodsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : app商品控制器测试
 * @since : 2019/7/11 11:06
 */
public class AppGoodsControllerTest extends TestSupport {

    @Autowired
    AppGoodsController appGoodsController;

    @Before
    public void before() {
        fake.fakeGoods();
        fake.fakeUser();
    }

    /**
     * 获取商品列表
     */
    @Test
    public void getGoodsList() {
        QueryAppGoodsDTO queryAppGoodsDTO = new QueryAppGoodsDTO();
        queryAppGoodsDTO.setPages(new Pages());
        AjaxResultGeneric ajaxResult = appGoodsController.getGoodsList(queryAppGoodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 获取商品的详情
     */
    @Test
    public void getGoodsDetail() {
        QueryAppGoodsDTO queryAppGoodsDTO = new QueryAppGoodsDTO();
        queryAppGoodsDTO.setGoodsId(fake.getGoods().getId());
        queryAppGoodsDTO.setUserId(fake.getUser().getId());
        AjaxResultGeneric<AppGoodDetailVO> ajaxResult = appGoodsController.getGoodsDetail(queryAppGoodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }


    /**
     * 查询拼团接龙商品
     */
    @Test
    public void getCollectGoodList() {
        QueryAppGoodsDTO queryAppGoodsDTO = new QueryAppGoodsDTO();
        queryAppGoodsDTO.setPages(new Pages());
        queryAppGoodsDTO.setCommunity(fake.getUser().getBindCommunity());
        AjaxResultGeneric ajaxResult = appGoodsController.getCollectGoodList(queryAppGoodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }


}
