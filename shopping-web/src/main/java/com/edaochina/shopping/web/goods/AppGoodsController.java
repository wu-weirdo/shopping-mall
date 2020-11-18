package com.edaochina.shopping.web.goods;


import com.aliyuncs.utils.StringUtils;
import com.edaochina.shopping.api.facade.goods.AppGoodsFacade;
import com.edaochina.shopping.api.facade.goods.SysGoodsFacade;
import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.api.service.sys.SearchHistoryService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.goods.BatchGoodsDTO;
import com.edaochina.shopping.domain.dto.goods.GoodsDTO;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsDTO;
import com.edaochina.shopping.domain.vo.goods.AppCollectGoodVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodDetailVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Api(description = "小程序商品处理")
@RestController
@RequestMapping("/app/goods")
public class AppGoodsController {

    private final AppGoodsFacade appGoodsFacade;
    private final SysGoodsFacade sysGoodsFacade;
    private final CommonService commonService;
    private final SearchHistoryService searchHistoryService;

    public AppGoodsController(AppGoodsFacade appGoodsFacade, SysGoodsFacade sysGoodsFacade, CommonService commonService, SearchHistoryService searchHistoryService) {
        this.appGoodsFacade = appGoodsFacade;
        this.sysGoodsFacade = sysGoodsFacade;
        this.commonService = commonService;
        this.searchHistoryService = searchHistoryService;
    }

    /**
     * 编辑商品
     *
     * @param goodsDTO 参数
     * @return 是否成功
     */
    @ApiOperation("编辑商品")
    @PostMapping("/editGoods")
    @OperationLogMark("编辑商品")
    public AjaxResultGeneric<Boolean> editGoods(@RequestBody GoodsDTO goodsDTO) {
        sysGoodsFacade.checkGoodsDTO(goodsDTO);
        // 转换图片地址
        List<String> img = goodsDTO.getImgs().parallelStream()
                .map(imgUrl -> {
                    if (!imgUrl.contains("cd-shopping.oss-cn-hangzhou.aliyuncs.com")) {
                        return commonService.appUploadImage(imgUrl);
                    }
                    return imgUrl;
                }).collect(Collectors.toList());
        goodsDTO.setImgs(img);

        if (StringUtils.isNotEmpty(goodsDTO.getId())) {
            return BaseResult.successGenericResult(sysGoodsFacade.updateGoods(goodsDTO));
        } else {
            return BaseResult.successGenericResult(sysGoodsFacade.insertGoods(goodsDTO));
        }
    }

    @ApiOperation("批量修改商品")
    @PostMapping("/batch")
    @OperationLogMark("批量修改商品")
    public AjaxResult<? extends Boolean> batch(@RequestBody BatchGoodsDTO batchGoodsDTO) {
        return BaseResult.successResult(sysGoodsFacade.batchUpdateGoods(batchGoodsDTO));
    }

    /**
     * 获取商品列表
     *
     * @param queryAppGoodsDTO 查询参数
     * @return 商品列表
     */
    @ApiOperation("小程序获取商品列表")
    @PostMapping("/getGoodsList")
    public AjaxResultGeneric<? extends PageResult<AppGoodsVO>> getGoodsList(@RequestBody QueryAppGoodsDTO queryAppGoodsDTO) {
        return BaseResult.successGenericResult(appGoodsFacade.getGoodsList(queryAppGoodsDTO));
    }

    @ApiOperation("小程序获取会员推荐商品列表")
    @PostMapping("/getMemberGoodsList")
    public AjaxResultGeneric<PageResult> getMemberGoodsList(@RequestBody QueryAppGoodsDTO queryAppGoodsDTO) {
        return BaseResult.successGenericResult(appGoodsFacade.getMemberGoodsList(queryAppGoodsDTO));
    }

    @ApiOperation("商家管理商品列表")
    @GetMapping("/shop/getGoodsList")
    public AjaxResult getGoodsList(QueryGoodsDTO queryGoodsDTO) throws IllegalAccessException, InstantiationException {
        return BaseResult.successResult(sysGoodsFacade.getGoodsList(queryGoodsDTO));
    }

    /**
     * 获取商品列表
     *
     * @param queryAppGoodsDTO 查询参数
     * @return 商品列表
     */
    @ApiOperation("小程序获取爆品商品列表")
    @PostMapping("/v2/getGoodsList")
    public AjaxResultGeneric<? extends PageResult<AppGoodsVO>> getV2GoodsList(@RequestBody QueryAppGoodsDTO queryAppGoodsDTO) {
        queryAppGoodsDTO.setHot(true);
        return BaseResult.successGenericResult(appGoodsFacade.getGoodsList(queryAppGoodsDTO));
    }

    @ApiOperation("缓存开关")
    @PutMapping("/hasCache")
    public AjaxResultGeneric<Boolean> hasCache(@RequestBody Boolean cache) {
        return BaseResult.successGenericResult(appGoodsFacade.hasCache(cache));
    }

    @ApiOperation("小程序获取商家商品列表")
    @GetMapping("/getGoodsList/{shopId}")
    public AjaxResultGeneric<? extends List<AppGoodsVO>> getGoodsList(@PathVariable String shopId) {
        return BaseResult.successGenericResult(appGoodsFacade.getShopGoodsList(shopId));
    }

    /**
     * 获取商品的详情
     *
     * @param queryAppGoodsDTO 查询参数
     * @return 商品详细
     */
    @ApiOperation("小程序获取商品详情")
    @PostMapping("/getGoodsDetail")
    public AjaxResultGeneric<AppGoodDetailVO> getGoodsDetail(@RequestBody QueryAppGoodsDTO queryAppGoodsDTO) {
        return BaseResult.successGenericResult(appGoodsFacade.getGoodsDetail(queryAppGoodsDTO));
    }

    /**
     * 查询拼团接龙商品
     *
     * @param queryAppGoodsDTO 查询参数
     * @return 拼团接龙商品
     */
    @ApiOperation("获取接龙商品列表")
    @RequestMapping("/getCollectGoodList")
    public AjaxResultGeneric<? extends PageResult<AppCollectGoodVO>> getCollectGoodList(@RequestBody QueryAppGoodsDTO queryAppGoodsDTO) {
        return BaseResult.successGenericResult(appGoodsFacade.getCollectGoodList(queryAppGoodsDTO));
    }

    @ApiOperation("获取热搜词列表")
    @GetMapping("/hot")
    public AjaxResultGeneric<List<String>> getHotSearch(String community) {
        return BaseResult.successGenericResult(searchHistoryService.hot(community));
    }
}

