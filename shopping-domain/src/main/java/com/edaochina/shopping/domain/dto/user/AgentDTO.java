package com.edaochina.shopping.domain.dto.user;

import com.edaochina.shopping.domain.base.page.Pages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author zzk
 * @Date 2019/1/2
 */
@Data
public class AgentDTO implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String cityCode;
    private String countyCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    /**
     * 是否付款（10：未付款，20：已付款）
     */
    private int payment;
    @NotNull(message = "分页数据不能为空")
    private Pages pages;

    private Integer startNum;

    private Integer pageSize;
}
