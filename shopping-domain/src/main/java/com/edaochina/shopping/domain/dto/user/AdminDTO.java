package com.edaochina.shopping.domain.dto.user;

import com.edaochina.shopping.domain.base.page.Pages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author zzk
 * @Date 2019/1/10
 */
@Data
public class AdminDTO implements Serializable {
    private String id;
    private String account;
    private String password;
    private String name;
    private String phone;
    private Integer status;
    private Integer roleId;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private Pages pages;
}
