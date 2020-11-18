package com.edaochina.shopping.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;

/**
 * @Author zzk
 * @Date 2019/1/2
 */
@Data
public class UpdMerchantDTO implements Serializable {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String identityNo;
    private Double longitude;
    private Double latitude;
    private String title;
    private String community;
    private Integer status;
    private String image;
    private Integer memberType;
    private Integer memberNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime memberTime;
    private String password;
    private String businssImage;
    /**
     * 食品经营许可证
     */
    private String foodImage;
    private Integer checkStatus;
    private String countyId;
    private String[] communitys;
    private String houseNumber;
    private Time startBusiness;
    private Time endBusiness;
}
