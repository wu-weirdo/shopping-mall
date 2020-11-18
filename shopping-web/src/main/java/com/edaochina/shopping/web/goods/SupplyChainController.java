package com.edaochina.shopping.web.goods;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.service.goods.GoodsImgsService;
import com.edaochina.shopping.api.service.goods.SupplyChainService;
import com.edaochina.shopping.common.annotation.PermissionsMark;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.goods.SupplyChainCopyDTO;
import com.edaochina.shopping.domain.dto.goods.SupplyChainInsertDTO;
import com.edaochina.shopping.domain.entity.goods.SupplyChain;
import com.edaochina.shopping.domain.vo.goods.GoodsImgsVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * (SupplyChain)表控制层
 *
 * @author wangpenglei
 * @since 2019-11-05 19:00:30
 */
@RestController
@RequestMapping("sys/supplyChain")
@Transactional(rollbackFor = Exception.class)
public class SupplyChainController extends ApiController {
    /**
     * 服务对象
     */
    private final SupplyChainService supplyChainService;

    private final GoodsImgsService goodsImgsService;

    public SupplyChainController(SupplyChainService supplyChainService, GoodsImgsService goodsImgsService) {
        this.supplyChainService = supplyChainService;
        this.goodsImgsService = goodsImgsService;
    }

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param supplyChain 查询实体
     * @return 所有数据
     */
    @GetMapping
    public AjaxResultGeneric selectAll(Page<SupplyChain> page, SupplyChain supplyChain) {
        IPage<SupplyChain> supplyChainPage = this.supplyChainService.page(page, buildQueryWrapper(supplyChain).lambda().gt(SupplyChain::getGoodsCount, 0));
        if (supplyChainPage.getRecords().size() != page.getSize()) {
            supplyChainPage.getRecords().addAll(this.supplyChainService.list(buildQueryWrapper(supplyChain).lambda().eq(SupplyChain::getGoodsCount, 0)));
        }
        return BaseResult.successGenericResult(supplyChainPage);
    }

    private QueryWrapper<SupplyChain> buildQueryWrapper(SupplyChain supplyChain) {
        QueryWrapper<SupplyChain> queryWrapper = new QueryWrapper<>(supplyChain);
        queryWrapper.lambda().orderByDesc(SupplyChain::getCreateTime);
        return queryWrapper;
    }

    /**
     * 导出
     *
     * @param supplyChain 查询实体
     * @return excel文件
     */
    @GetMapping("export")
    public AjaxResultGeneric export(SupplyChain supplyChain) {
        return BaseResult.successGenericResult(this.supplyChainService.export(supplyChain));
    }

    @GetMapping("images/{id}")
    public AjaxResultGeneric<List<GoodsImgsVO>> selectImages(@PathVariable String id) {
        return BaseResult.successGenericResult(this.goodsImgsService.getGoodsImgsList(id));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResultGeneric selectOne(@PathVariable Serializable id) {
        return BaseResult.successGenericResult(this.supplyChainService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param dto 实体对象
     * @return 新增结果
     */
    @PostMapping
    @PermissionsMark({RoleConstants.ADMIN_ROLE})
    public AjaxResultGeneric insert(@RequestBody SupplyChainInsertDTO dto) {
        return BaseResult.successGenericResult(this.supplyChainService.save(dto));
    }

    @PostMapping("copy")
    @PermissionsMark({RoleConstants.MERCHANT_ROLE})
    public AjaxResultGeneric copy(@RequestBody @Valid SupplyChainCopyDTO dto) {
        return BaseResult.successGenericResult(this.supplyChainService.copy(dto));
    }

    /**
     * 修改数据
     *
     * @param dto 实体对象
     * @return 修改结果
     */
    @PutMapping
    @PermissionsMark({RoleConstants.ADMIN_ROLE})
    public AjaxResultGeneric update(@RequestBody SupplyChainInsertDTO dto) {
        return BaseResult.successGenericResult(this.supplyChainService.update(dto));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @PermissionsMark({RoleConstants.ADMIN_ROLE})
    public AjaxResultGeneric delete(@RequestParam("idList") List<Long> idList) {
        return BaseResult.successGenericResult(this.supplyChainService.removeByIds(idList));
    }
}