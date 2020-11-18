package com.edaochina.shopping.domain.entity.sys;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDictionaryParam extends Model<SysDictionaryParam> {
    private Integer id;

    /**
     * 字典类型
     */
    private String sysType;

    /**
     * 字典类型名称
     */
    private String sysTypeName;

    /**
     * 字典key
     */
    private String sysKey;

    /**
     * 字典值
     */
    private String sysValue;

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
     * 备注
     */
    private String remark;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

