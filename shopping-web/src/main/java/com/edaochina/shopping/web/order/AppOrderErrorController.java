package com.edaochina.shopping.web.order;

import com.edaochina.shopping.api.service.order.OrderErrorBackService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.order.OrderErrorBackDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "异常订单")
@RestController
@RequestMapping("/app/orderError")
public class AppOrderErrorController {

    @Resource
    OrderErrorBackService orderErrorBackService;

    /**
     * 添加异常订单
     *
     * @param dto
     * @return
     */
    @ApiOperation("商家或用户添加异常订单")
    @RequestMapping("/add/orderError")
    public AjaxResult addOrderError(@RequestBody OrderErrorBackDTO dto) {
        return BaseResult.successResult(orderErrorBackService.insertOrderError(dto));
    }
}
