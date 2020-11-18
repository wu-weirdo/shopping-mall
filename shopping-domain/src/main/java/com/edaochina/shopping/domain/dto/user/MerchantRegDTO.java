package com.edaochina.shopping.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author zzk
 * @Date 2018/12/26
 */
@Data
public class MerchantRegDTO extends RegisterDTO {
    @NotBlank(message = "地址不能为空")
    private String address;
    @NotNull(message = "经度不能为空")
    private Double longitude;
    @NotNull(message = "纬度不能为空")
    private Double latitude;
    @NotBlank(message = "绑定小区不能为空")
    private String community;
    @NotBlank(message = "商家名称不能为空")
    private String title;
    private String image;
    private Integer memberType;
    private Integer memberNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime memberTime;
    private String businssImage;

    private String foodImage;

    private String countyId;

    private String[] communitys;

    private String invitatCode;

    private String communityUserId;

    private Integer status;

    private String houseNumber;
}
