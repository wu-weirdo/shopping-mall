package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.api.facade.sys.RotationPicFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/rotation")
public class AppRotationPicController {

    @Resource
    RotationPicFacade rotationPicFacade;

    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxResult list() {
        return BaseResult.successResult(rotationPicFacade.list());
    }

}
