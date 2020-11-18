package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zzk
 * @since 2019/1/24
 */
@Data
public class CommunityAppListDTO implements Serializable {

    @NotNull(message = "经度不能为空")
    private Double longitude;

    @NotNull(message = "纬度不能为空")
    private Double latitude;

    private String name;

    private Integer countyId;

    private Long maxDistinct;
}
