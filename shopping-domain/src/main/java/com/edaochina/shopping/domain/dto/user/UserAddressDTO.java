package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserAddressDTO implements Serializable {

    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 小区id
     */
    private String communityId;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 门牌号
     */
    private String houseNumber;

    /**
     * 是否删除 ( 0 可用 1删除 )
     */
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
