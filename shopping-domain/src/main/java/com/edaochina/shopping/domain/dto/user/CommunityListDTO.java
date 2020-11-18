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
public class CommunityListDTO implements Serializable {
    private String name;
    private String riderName;
    private String address;
    private String userId;
    private String roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @NotNull(message = "分页数据不能为空")
    private Pages pages;
}
