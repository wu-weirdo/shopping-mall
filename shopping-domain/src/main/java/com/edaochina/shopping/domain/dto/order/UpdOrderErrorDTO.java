package com.edaochina.shopping.domain.dto.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdOrderErrorDTO {

    private Integer id;
    private String handleUserId;
    private Integer handleUserRole;
    private Integer handleAfterStatus;
    private Integer writeOffStatus;
    private Integer handleResult;
    private LocalDateTime handleTime;
    private String remark;
}
