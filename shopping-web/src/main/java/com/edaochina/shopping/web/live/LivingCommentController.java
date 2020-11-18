package com.edaochina.shopping.web.live;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.service.live.LivingCommentService;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.entity.live.LivingComment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 直播评论(LivingComment)表控制层
 *
 * @author wangpenglei
 * @since 2019-08-26 17:47:57
 */
@RestController
@RequestMapping("live/comment")
@Api(tags = "评论")
public class LivingCommentController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private LivingCommentService livingCommentService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param livingComment 查询实体
     * @return 所有数据
     */
    @ApiOperation("分页查询所有数据")
    @GetMapping
    public AjaxResultGeneric<? extends IPage<LivingComment>> selectAll(Page<LivingComment> page, LivingComment livingComment) {
        if (livingComment != null && livingComment.getDelFlag() == null) {
            livingComment.setDelFlag(0);
        }
        return BaseResult.successGenericResult(this.livingCommentService.page(page, new QueryWrapper<>(livingComment)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public AjaxResultGeneric<? extends LivingComment> selectOne(@PathVariable Serializable id) {
        return BaseResult.successGenericResult(this.livingCommentService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param livingComment 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据-黑名单用户的评论自动设置为已删除")
    @PostMapping
    public AjaxResultGeneric<? extends Boolean> insert(@RequestBody LivingComment livingComment) {
        return BaseResult.successGenericResult(this.livingCommentService.saveAndCheck(livingComment));
    }

    /**
     * 修改数据
     *
     * @param livingComment 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping
    public AjaxResultGeneric<? extends Boolean> update(@RequestBody LivingComment livingComment) {
        return BaseResult.successGenericResult(this.livingCommentService.updateById(livingComment));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping
    public AjaxResultGeneric<? extends Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return BaseResult.successGenericResult(this.livingCommentService.removeByIds(idList));
    }
}