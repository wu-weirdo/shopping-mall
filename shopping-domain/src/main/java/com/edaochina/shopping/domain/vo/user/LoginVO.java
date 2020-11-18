package com.edaochina.shopping.domain.vo.user;

import com.edaochina.shopping.domain.entity.user.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 代理商表 by 张志侃
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Data
public class LoginVO implements Serializable {


    private String id;

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * jwt token
     */
    private String token;

    private String roleId;
    /**
     * 菜单列表
     */
    private List<SysMenu> menuVOList;

    /**
     * 按钮列表
     */
    private List<String> buttonAliasList;

    /**
     * 页面版本号
     */
    private String version;

}
