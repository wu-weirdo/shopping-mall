package com.edaochina.shopping.web.goods;

import com.aliyuncs.utils.StringUtils;
import com.edaochina.shopping.api.facade.goods.SysGoodsTypeFacade;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.goods.DeleteGoodsTypeDTO;
import com.edaochina.shopping.domain.dto.goods.GoodsTypeDTO;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsSubclassDTO;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsTypeDTO;
import io.swagger.annotations.Api;
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
 *
 * @author xww
 * @since 2018-12-26
 */
@Api(description = "系统对商品子类请求")
@RestController
@RequestMapping("/sys/goodsType")
public class SysGoodsTypeController {

    @Autowired
    private SysGoodsTypeFacade sysGoodsTypeFacade;

    /**
     * 编辑商品分类
     *
     * @param goodsTypeDTO
     * @return
     */
    @ApiOperation("编辑商品类型")
    @PostMapping("/editGoodsType")
    public AjaxResult editGoodsType(@RequestBody GoodsTypeDTO goodsTypeDTO) {
        if (goodsTypeDTO == null) {
            throw new YidaoException(ReturnData.GOODS_TYPE_PARAM_EMPTY.getCode(), ReturnData.GOODS_TYPE_PARAM_EMPTY.getMsg());
        }
        if (StringUtils.isNotEmpty(goodsTypeDTO.getId())) {
            return BaseResult.successResult(sysGoodsTypeFacade.updateGoodsType(goodsTypeDTO));
        } else {
            return BaseResult.successResult(sysGoodsTypeFacade.insertGoodsType(goodsTypeDTO));
        }
    }

    /**
     * 删除商品类型
     *
     * @param deleteGoodsTypeDTO
     * @return
     */
    @ApiOperation("删除商品子类")
    @PostMapping("/deleteGoodsTypes")
    public AjaxResult deleteGoodsTypes(@RequestBody DeleteGoodsTypeDTO deleteGoodsTypeDTO) {
        return BaseResult.successResult(sysGoodsTypeFacade.deleteGoodsTypes(deleteGoodsTypeDTO));
    }

    /**
     * 分页商品分类
     *
     * @param queryGoodsTypeDTO
     * @return
     */
    @ApiOperation("获取商品子类列表")
    @PostMapping("/getGoodsTypeList")
    public AjaxResult getGoodsTypeList(@RequestBody QueryGoodsTypeDTO queryGoodsTypeDTO) {
        return BaseResult.successResult(sysGoodsTypeFacade.getGoodsTypeList(queryGoodsTypeDTO));
    }

    /**
     * 获取种类详情
     *
     * @param queryGoodsSubclassDTO
     * @return
     */
    @ApiOperation("获取商品类型明细")
    @PostMapping("/getGoodsTypeDetail")
    public AjaxResult getGoodsTypeDetail(@RequestBody QueryGoodsSubclassDTO queryGoodsSubclassDTO) {
        return BaseResult.successResult(sysGoodsTypeFacade.getGoodsTypeDetail(queryGoodsSubclassDTO));
    }

}

