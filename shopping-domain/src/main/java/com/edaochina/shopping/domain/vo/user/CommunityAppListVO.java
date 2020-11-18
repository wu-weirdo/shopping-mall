package com.edaochina.shopping.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 小区表 by zzk
 *
 * </p>
 *
 * @author zzk
 * @since 2019-01-07
 */
@Data
public class CommunityAppListVO implements Serializable {

    private String id;

    /**
     * 小区名称
     */
    private String name;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 地址
     */
    private String address;

    /**
     * 团长id
     */
    private String riderId;

    /**
     * 团长名称
     */
    private String riderName;

    /**
     * 省代码
     */
    private String provinceId;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市代码
     */
    private String cityId;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Double distance;

}
