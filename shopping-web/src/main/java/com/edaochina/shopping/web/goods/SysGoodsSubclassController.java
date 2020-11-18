package com.edaochina.shopping.web.goods;


import com.edaochina.shopping.api.service.goods.SysGoodsSubclassService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsSubclassDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 * 这里应该是sys/goodsSubclass,但是由于前端项目已经使用app了暂时不改
 *
 * @author xww
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/app/goodsSubclass")
public class SysGoodsSubclassController {

    @Autowired
    private SysGoodsSubclassService sysGoodsSubclassService;


    /**
     * 查询所有商品子类
     *
     * @return 商品子类
     */
    @ApiOperation("查询商品子类列表")
    @PostMapping("/getGoodsSubclassList")
    public AjaxResult getGoodsSubclassList(@RequestBody QueryGoodsSubclassDTO queryGoodsSubclassDTO) {
        return BaseResult.successResult(sysGoodsSubclassService.getGoodsSubclassList(queryGoodsSubclassDTO));
    }


    /**
     * 查询所有商品分类
     *
     * @return
     */
    @PostMapping("/getAllGoodsClassList")
    @ApiOperation("根据小区查询有商品的商品子类类型")
    @ApiImplicitParam(name = "communityId", value = "小区id")
    public AjaxResult getAllGoodsTypeList(String communityId) {
        return BaseResult.successResult(sysGoodsSubclassService.getAllGoodsClassList(communityId));
    }

}

