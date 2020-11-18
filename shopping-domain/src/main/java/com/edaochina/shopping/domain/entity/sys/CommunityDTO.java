package com.edaochina.shopping.domain.entity.sys;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author zzk
 * @Date 2019/1/11
 */
@Data
public class CommunityDTO implements Serializable {
    /**
     * 小区名称
     */
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 经度
     */
    @NotNull(message = "经度不能为空")
    private Double longitude;

    /**
     * 纬度
     */
    @NotNull(message = "纬度不能为空")
    private Double latitude;
    /**
     * 地址
     */
    @NotBlank(message = "地址不能为空")
    private String address;


    /**
     * 省代码
     */
    @NotBlank(message = "省代码不能为空")
    private String provinceId;

    /**
     * 市代码
     */
    @NotBlank(message = "市代码不能为空")
    private String cityId;


    /**
     * 县代码
     */
    @NotBlank(message = "县代码不能为空")
    private String countyId;

}
