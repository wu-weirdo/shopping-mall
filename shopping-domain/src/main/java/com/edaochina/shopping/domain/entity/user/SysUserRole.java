package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表 by 张志侃
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否禁用（0 否 1是）
     */
    private Integer disableFlag;

    /**
     * 是否删除（0 未删除 1删除）
     */
    private Integer deleteFlag;

    /**
     * 创建时间 默认当前时间
     */
    private LocalDateTime createTime;

    /**
     * 数据更新时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
