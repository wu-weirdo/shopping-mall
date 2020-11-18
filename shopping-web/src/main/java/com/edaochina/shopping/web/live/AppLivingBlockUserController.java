package com.edaochina.shopping.web.live;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.service.live.LivingBlockUserService;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.entity.live.LivingBlockUser;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 直播拉黑用户列表(LivingBlockUser)表控制层
 *
 * @author wangpenglei
 * @since 2019-08-26 17:48:12
 */
@RestController
@RequestMapping("app/live/block")
@Api(tags = "黑名单")
public class AppLivingBlockUserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private LivingBlockUserService livingBlockUserService;

    /**
     * 分页查询所有数据
     *
     * @param page            分页对象
     * @param livingBlockUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public AjaxResultGeneric<? extends IPage<LivingBlockUser>> selectAll(Page<LivingBlockUser> page, LivingBlockUser livingBlockUser) {
        return BaseResult.successGenericResult(this.livingBlockUserService.page(page, new QueryWrapper<>(livingBlockUser)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResultGeneric<? extends LivingBlockUser> selectOne(@PathVariable Serializable id) {
        return BaseResult.successGenericResult(this.livingBlockUserService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param livingBlockUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    public AjaxResultGeneric<? extends Boolean> insert(@RequestBody LivingBlockUser livingBlockUser) {
        return BaseResult.successGenericResult(this.livingBlockUserService.save(livingBlockUser));
    }

    /**
     * 修改数据
     *
     * @param livingBlockUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public AjaxResultGeneric<? extends Boolean> update(@RequestBody LivingBlockUser livingBlockUser) {
        return BaseResult.successGenericResult(this.livingBlockUserService.updateById(livingBlockUser));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public AjaxResultGeneric<? extends Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return BaseResult.successGenericResult(this.livingBlockUserService.removeByIds(idList));
    }
}