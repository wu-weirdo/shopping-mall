package com.edaochina.shopping.web.order;


import com.edaochina.shopping.api.service.order.OrderErrorBackService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.order.QueryOrderErrorBackDTO;
import com.edaochina.shopping.domain.dto.order.UpdOrderErrorDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 异常订单反馈表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/orderErrorBack")
public class OrderErrorBackController {

    @Resource
    OrderErrorBackService orderErrorBackService;

    /**
     * 异常订单列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("/list/orderError")
    public AjaxResult orderErrorList(@RequestBody QueryOrderErrorBackDTO dto) {
        return BaseResult.successResult(orderErrorBackService.orderErrorBackList(dto));
    }

    /**
     * 处理异常订单
     *
     * @param dto
     * @return
     */
    @RequestMapping("/edit/orderError")
    @OperationLogMark("处理异常订单")
    public AjaxResult editOrderError(@RequestBody UpdOrderErrorDTO dto) {
        return BaseResult.successResult(orderErrorBackService.updateOrderErrorBack(dto));
    }

    /**
     * 异常订单详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/detail/orderError")
    public AjaxResult orderErrorDetail(Integer id) {
        return BaseResult.successResult(orderErrorBackService.selectOrderErrorById(id));
    }
}

