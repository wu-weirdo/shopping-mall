package com.edaochina.shopping.domain.vo.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author :         Jason
 * @createDate :     2018/10/10 16:25
 * @description :
 */
@Data
@Accessors(chain = true)
public class MenuVO implements Serializable {
    /**
     * 菜单编号
     */
    private String id;


    /**
     * 菜单父编号 -1为无父编号
     */
    private String parentId;

    private String title;

    /**
     * 为前端大佬准备
     */
    private Boolean high;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单路由
     */
    private String path;

    private List<MenuChildrenVO> children;
}
