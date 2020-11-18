package com.edaochina.shopping.web.live;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.service.live.LivingCurriculumService;
import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.JsonUtils;
import com.edaochina.shopping.domain.dto.live.CheckDTO;
import com.edaochina.shopping.domain.entity.live.LivingCurriculum;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 直播课程(LivingCurriculum)表控制层
 *
 * @author wangpenglei
 * @since 2019-08-26 17:24:54
 */
@RestController
@RequestMapping("app/live/curriculum")
@Api(tags = "课程")
public class AppLivingCurriculumController extends ApiController {

    private final Logger logger = LoggerFactory.getLogger(AppLivingCurriculumController.class);
    private final CommonService commonService;
    /**
     * 服务对象
     */
    @Resource
    private LivingCurriculumService livingCurriculumService;

    public AppLivingCurriculumController(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 是否成功
     */
    @PostMapping("/upload")
    public AjaxResultGeneric<Boolean> upload(@RequestBody MultipartFile file) {
        if (file == null) {
            return BaseResult.successGenericResult(false);
        }
        String url = commonService.uploadImage(file);
        Map<String, String> map = new HashMap<>(2);
        map.put("msg", url);
        map.put("type", file.getContentType());
        LiveWebSocket.sendTextMessage(JsonUtils.toJson(map));
        return BaseResult.successGenericResult(true);
    }

    /**
     * 分页查询所有数据
     *
     * @param page             分页对象
     * @param livingCurriculum 查询实体
     * @return 所有数据
     */
    @GetMapping
    public AjaxResultGeneric<IPage<LivingCurriculum>> selectAll(Page<LivingCurriculum> page, LivingCurriculum livingCurriculum) {
        return BaseResult.successGenericResult(this.livingCurriculumService.page(page, new QueryWrapper<>(livingCurriculum)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResultGeneric<LivingCurriculum> selectOne(@PathVariable Serializable id) {
        return BaseResult.successGenericResult(this.livingCurriculumService.getById(id));
    }


    /**
     * 检查课程验证码
     *
     * @param dto 验证码
     * @param id  课程id
     * @return 是否正确
     */
    @PostMapping("{id}")
    public AjaxResultGeneric<Boolean> check(@Valid @RequestBody CheckDTO dto, @PathVariable int id) {
        LivingCurriculum livingCurriculum = this.livingCurriculumService.getById(id);
        return BaseResult.successGenericResult(livingCurriculum != null && dto.getCode().equals(livingCurriculum.getCode()));
    }
}