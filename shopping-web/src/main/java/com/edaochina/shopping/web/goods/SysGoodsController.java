package com.edaochina.shopping.web.goods;


import com.aliyuncs.utils.StringUtils;
import com.edaochina.shopping.api.facade.goods.SysGoodsFacade;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.annotation.PermissionsMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.goods.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统查询商品
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Api(description = "系统查询商品")
@RestController
@RequestMapping("/sys/goods")
public class SysGoodsController {

    @Autowired
    private SysGoodsFacade sysGoodsFacade;

    /**
     * 编辑商品
     *
     * @param goodsDTO 参数
     * @return 是否成功
     */
    @ApiOperation("编辑商品")
    @PostMapping("/editGoods")
    @OperationLogMark("编辑商品")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE, RoleConstants.MERCHANT_ROLE, RoleConstants.COMMUNITY_PARTENER})
    public AjaxResult editGoods(@RequestBody GoodsDTO goodsDTO) {
        sysGoodsFacade.checkGoodsDTO(goodsDTO);
        if (StringUtils.isNotEmpty(goodsDTO.getId())) {
            return BaseResult.successResult(sysGoodsFacade.updateGoods(goodsDTO));
        } else {
            return BaseResult.successResult(sysGoodsFacade.insertGoods(goodsDTO));
        }
    }

    /**
     * 批量删除商品
     *
     * @param deleteGoodsDTO
     * @return
     */
    @ApiOperation("批量删除商品")
    @PostMapping("/deleteGoods")
    @OperationLogMark("批量删除商品")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE, RoleConstants.MERCHANT_ROLE, RoleConstants.COMMUNITY_PARTENER})
    public AjaxResult deleteGoods(@RequestBody DeleteGoodsDTO deleteGoodsDTO) {
        return BaseResult.successResult(sysGoodsFacade.deleteGoods(deleteGoodsDTO));
    }

    /**
     * 查询商品详情
     *
     * @param querySysGoodsDTO
     * @return
     */
    @ApiOperation("获取商品详情")
    @PostMapping("/getGoodsDetail")
    public AjaxResult getGoodsDetail(@RequestBody QuerySysGoodsDTO querySysGoodsDTO) {
        return BaseResult.successResult(sysGoodsFacade.getGoodsDetail(querySysGoodsDTO));
    }

    /**
     * 查询商品列表
     *
     * @param queryGoodsDTO
     * @return
     */
    @ApiOperation("获取商品列表")
    @PostMapping("/getGoodsList")
    public AjaxResult getGoodsList(@RequestBody QueryGoodsDTO queryGoodsDTO) throws IllegalAccessException, InstantiationException {
        return BaseResult.successResult(sysGoodsFacade.getGoodsList(queryGoodsDTO));
    }

    /**
     * 审批商品
     *
     * @param approvalGoodsDTO
     * @return
     */
    @PostMapping("/approvalGoods")
    @OperationLogMark("审批商品")
    public AjaxResult approvalGoods(@RequestBody ApprovalGoodsDTO approvalGoodsDTO) {
        return BaseResult.successResult(sysGoodsFacade.approvalGoods(approvalGoodsDTO));
    }

    @PutMapping("/batch")
    @OperationLogMark("批量商品上下架")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE, RoleConstants.MERCHANT_ROLE, RoleConstants.COMMUNITY_PARTENER})
    public AjaxResult batch(@RequestBody BatchGoodsDTO batchGoodsDTO) {
        return BaseResult.successResult(sysGoodsFacade.batchUpdateGoods(batchGoodsDTO));
    }

    @PutMapping("/ripening")
    @OperationLogMark("立即拼团成功")
    @PermissionsMark({RoleConstants.ADMIN_ROLE, RoleConstants.AGENT_ROLE})
    public AjaxResultGeneric<Boolean> ripening(@RequestBody String goodsId) {
        return BaseResult.successGenericResult(sysGoodsFacade.ripening(goodsId));
    }


    /**
     * 审批商品
     *
     * @param dto
     * @return
     */
    @PostMapping("/batchApprovalGoods")
    @OperationLogMark("审批商品")
    public AjaxResult batchApprovalGoods(@RequestBody SysBatchApprovalGoodsDTO dto) {
        return BaseResult.successResult(sysGoodsFacade.batchApprovalGoods(dto));
    }

}

