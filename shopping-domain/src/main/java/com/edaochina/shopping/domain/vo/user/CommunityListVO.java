package com.edaochina.shopping.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author zzk
 * @Date 2019/1/11
 */
@Data
public class CommunityListVO implements Serializable {
    private String id;
    private String name;
    private String address;
    private String provinceName;
    private String cityName;
    private String countyName;
    private Integer merchantNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
