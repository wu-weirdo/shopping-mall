package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.facade.sys.RotationPicFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.RotationPicDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 轮播图管理
 *
 * @author yxw
 */
@RestController
@RequestMapping("/sys/rotation")
public class SysRotationPicController {

    @Resource
    private RotationPicFacade rotationPicFacade;

    /**
     * 查询列表
     *
     * @return
     */
    @GetMapping(value = "/list")
    public AjaxResult list() {
        try {
            return BaseResult.successResult(rotationPicFacade.list());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.failedResult();
        }
    }

    /**
     * 插入轮播图
     *
     * @return
     */
    @PostMapping(value = "/insert")
    public AjaxResult insert(@RequestBody RotationPicDTO dto) {
        try {
            return BaseResult.successResult(rotationPicFacade.insert(dto));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.failedResult();
        }
    }

    /**
     * 删除轮播图
     *
     * @return
     */
    @PostMapping(value = "/delete")
    public AjaxResult delete(@RequestBody RotationPicDTO dto) {
        try {
            return BaseResult.successResult(rotationPicFacade.delete(dto));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.failedResult();
        }
    }

    @PostMapping(value = "/update")
    public AjaxResult update(@RequestBody RotationPicDTO rotationPicDTO) {
        return rotationPicFacade.update(rotationPicDTO);
    }

    @GetMapping(value = "/detail")
    public AjaxResult detail(String id) {
        return rotationPicFacade.detail(id);
    }

    @GetMapping(value = "/swap")
    public AjaxResult swapSort(String id1, String id2) {
        return rotationPicFacade.swap(id1, id2);
    }
}
