package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单表 by 张志侃
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单编号
     */
    private String id;

    /**
     * 菜单父编号 -1为无父编号
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单路由
     */
    private String path;

    private Integer sort;

    /**
     * 创建时间 默认当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 数据更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false, select = false)
    private List<SysMenu> children;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
