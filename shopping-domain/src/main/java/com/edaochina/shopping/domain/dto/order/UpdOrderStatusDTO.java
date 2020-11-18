package com.edaochina.shopping.domain.dto.order;

import lombok.Data;

@Data
public class UpdOrderStatusDTO {

    private String orderId;
    private Integer status;
    private Integer writeOffStatus;
}
