package com.edaochina.shopping.domain.vo.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统菜单分配表 by 汤俊圣
 * </p>
 *
 * @author ${author}
 * @since 2018-11-13
 */
@Data
@Accessors(chain = true)
public class SysMenuConfVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单分配编号
     */
    private String id;

    /**
     * 菜单编号 -1为所有权限
     */
    private String menuId;

    /**
     * 用户角色编号
     */
    private String roleId;
}
