package com.edaochina.shopping.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author zzk
 * @Date 2019/1/10
 */
@Data
public class AdminVO implements Serializable {
    private String id;
    private String account;
    private String phone;
    private String name;
    private Integer status;
    private Integer roleId;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
