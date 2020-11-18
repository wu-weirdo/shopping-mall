package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统菜单分配表 by 张志侃
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMenuConf extends Model<SysMenuConf> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单分配编号
     */
    private String id;

    /**
     * 用户角色编号(1 管理员 2团长 3 商家 4 代理商)
     */
    private String roleId;

    /**
     * 菜单编号 -1为所有权限
     */
    private String menuId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
