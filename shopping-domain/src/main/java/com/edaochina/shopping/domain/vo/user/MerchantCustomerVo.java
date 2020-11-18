package com.edaochina.shopping.domain.vo.user;

import lombok.Data;


/**
 * @author jintian
 * @date 2019/10/12 14:56
 */
@Data
public class MerchantCustomerVo {

    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 0 未知 1 男 2 女
     */
    private Integer gender;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 订单次数
     */
    private Integer orderNum;
}
