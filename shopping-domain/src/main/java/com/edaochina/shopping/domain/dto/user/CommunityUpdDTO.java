package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zzk
 * @Date 2019/1/2
 */
@Data
public class CommunityUpdDTO implements Serializable {
    private String id;
    private String name;
    private Double longitude;
    private Double latitude;
    private String address;
    private String provinceId;
    private String cityId;
    private String countyId;

}
