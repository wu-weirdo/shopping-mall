package com.edaochina.shopping.domain.dto.sys;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysAddressDTO implements Serializable {

    /**
     * 编号
     */
    private String code;

    /**
     * 名字
     */
    private String name;

    /**
     * 父编号
     */
    private String parentCode;

    /**
     * 等级
     */
    private String level;

    private String roleId;

    private String userId;
}
