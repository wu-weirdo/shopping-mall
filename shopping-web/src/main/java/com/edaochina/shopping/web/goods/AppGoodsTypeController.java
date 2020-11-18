package com.edaochina.shopping.web.goods;

import com.edaochina.shopping.api.facade.goods.SysGoodsTypeFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Api(description = "商品类型")
@RestController
@RequestMapping("/app/goodsType")
public class AppGoodsTypeController {

    @Autowired
    private SysGoodsTypeFacade sysGoodsTypeFacade;

    /**
     * 查询所有商品分类
     *
     * @return
     */
    @PostMapping("/getAllGoodsTypeList")
    @ApiOperation("根据小区查询有商品的商品类型")
    @ApiImplicitParam(name = "communityId", value = "小区id")
    public AjaxResult getAllGoodsTypeList(String communityId) {
        return BaseResult.successResult(sysGoodsTypeFacade.getAllGoodsTypeList(communityId));
    }

    @PostMapping("/hotGoodsType")
    @ApiOperation("根据小区查询有爆品的商品类型")
    @ApiImplicitParam(name = "communityId", value = "小区id", required = true)
    public AjaxResult getAllHotGoodsTypeList(String communityId) {
        return BaseResult.successResult(sysGoodsTypeFacade.getAllHotGoodsTypeList(communityId));
    }
}

