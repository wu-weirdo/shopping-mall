package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Community extends Model<Community> {

    private static final long serialVersionUID = 1L;

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

    /**
     * 区县码
     */
    private String countyId;

    /**
     * 区县名
     */
    private String countyName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
