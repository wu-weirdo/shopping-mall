package com.edaochina.shopping.domain.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单表 by 汤俊圣
 * </p>
 *
 * @author ${author}
 * @since 2018-11-13
 */
@Data
@Accessors(chain = true)
public class SysMenuDTO implements Serializable {

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
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单路由
     */
    private String path;
}
