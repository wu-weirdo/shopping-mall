package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 24h
 */
@Data
public class UpdUserDTO implements Serializable {

    private String id;


    private String userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String name;

    private Integer gender;
}
