package com.edaochina.shopping.domain.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderErrorBackDTO {
    /**
     * 异常订单id
     */
    private String orderId;
    /**
     * 角色类型
     */
    private Integer origin;
    /**
     * 异常原因
     */
    private String exceptionReason;

    /**
     * 异常图片
     */
    private List<String> imgs;
}
