package com.edaochina.shopping.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Author zzk
 * @Date 2018/12/26
 */
@Data
public class AppMerchantRegDTO extends RegisterDTO {
    @NotBlank(message = "地址不能为空")
    private String address;
    @Deprecated
    @NotBlank(message = "绑定小区不能为空")
    private String community;
    @NotBlank(message = "商家名称不能为空")
    private String title;
    private String image;
    private Integer memberType;
    private Integer memberNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime memberTime;
    // 营业执照
    private String businssImage;

    // 经度
    private Double longitude;
    // 维度
    private Double latitude;

    /**
     * 食品经营许可证
     */
    private String foodImage;

    private String invitatCode;

    private String countyId;

    private String houseNumber;
}
