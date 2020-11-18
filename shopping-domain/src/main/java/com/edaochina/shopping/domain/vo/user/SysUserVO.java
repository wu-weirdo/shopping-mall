package com.edaochina.shopping.domain.vo.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统用户表 by 汤俊圣
 * </p>
 *
 * @author ${author}
 * @since 2018-11-13
 */
@Data
@Accessors(chain = true)
public class SysUserVO implements Serializable {

    private String id;

    /**
     * 账户
     */
    private String account;
    /**
     * 用户名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 账户类型
     */
    private String roleId;

    private String invitatCode;
}