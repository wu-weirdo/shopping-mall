package com.edaochina.shopping.web.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.edaochina.shopping.api.service.sys.OperationLogService;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.OperationLogDTO;
import com.edaochina.shopping.domain.entity.sys.OperationLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 操作记录控制器
 * @since : 19:00
 */
@RestController
@RequestMapping("sys/log")
public class OperationLogController {

    private final OperationLogService service;

    public OperationLogController(OperationLogService service) {
        this.service = service;
    }

    @GetMapping
    public AjaxResultGeneric<IPage<OperationLog>> list(OperationLogDTO dto) {
        return BaseResult.successGenericResult(service.list(dto));
    }

    @GetMapping("users")
    public AjaxResultGeneric<List<OperationLog>> users() {
        return BaseResult.successGenericResult(service.users());
    }
}
