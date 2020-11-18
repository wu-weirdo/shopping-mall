package goods;

import base.TestSupport;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.goods.*;
import com.edaochina.shopping.web.goods.SysGoodsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * SysGoodsControllerTest
 *
 * @author wangpenglei
 * @since 2019/7/30 11:34
 */
public class SysGoodsControllerTest extends TestSupport {

    @Autowired
    SysGoodsController sysGoodsController;

    @Before
    public void before() {
        fake.fakeGoods();
        fake.fakeUser();
    }

    /**
     * 编辑商品
     */
    @Test
    public void editGoods() {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setShopId(fake.getGoods().getShopId());
        AjaxResult ajaxResult = sysGoodsController.editGoods(goodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 批量删除商品
     */
    @Test
    public void deleteGoods() {
        DeleteGoodsDTO deleteGoodsDTO = new DeleteGoodsDTO();
        AjaxResult ajaxResult = sysGoodsController.deleteGoods(deleteGoodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 查询商品详情
     */
    @Test
    public void getGoodsDetail() {
        QuerySysGoodsDTO querySysGoodsDTO = new QuerySysGoodsDTO();
        querySysGoodsDTO.setId(fake.getGoods().getId());
        AjaxResult ajaxResult = sysGoodsController.getGoodsDetail(querySysGoodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 查询商品列表
     */
    @Test
    public void getGoodsList() throws IllegalAccessException, InstantiationException {
        QueryGoodsDTO queryGoodsDTO = new QueryGoodsDTO();
        queryGoodsDTO.setPages(new Pages());
        AjaxResult ajaxResult = sysGoodsController.getGoodsList(queryGoodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    /**
     * 审批商品
     */
    @Test
    public void approvalGoods() {
        ApprovalGoodsDTO approvalGoodsDTO = new ApprovalGoodsDTO();
        approvalGoodsDTO.setId(fake.getGoods().getId());
        AjaxResult ajaxResult = sysGoodsController.approvalGoods(approvalGoodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

    @Test
    public void batch() {
        BatchGoodsDTO batchGoodsDTO = new BatchGoodsDTO();
        batchGoodsDTO.setIds(Collections.singletonList(fake.getGoods().getId()));
        batchGoodsDTO.setDeleteFlag(20);
        batchGoodsDTO.setPutawayStatus(20);
        AjaxResult ajaxResult = sysGoodsController.batch(batchGoodsDTO);
        Assert.assertEquals(200, ajaxResult.getCode());
    }

}
