package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryOrderErrorBackDTO {

    private String orderId;
    private String merchantTitle;
    private String merchantId;
    private String merchantPhone;
    private String userId;
    private String userPhone;
    private Integer handleResult;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private Pages pages;
    private Integer origin;
    private String countyId;

}
