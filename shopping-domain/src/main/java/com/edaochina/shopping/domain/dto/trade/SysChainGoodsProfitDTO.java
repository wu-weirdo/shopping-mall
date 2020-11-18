package com.edaochina.shopping.domain.dto.trade;

import com.edaochina.shopping.domain.base.page.Pages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jintian
 * @date 2019/11/14 18:14
 */
@Data
public class SysChainGoodsProfitDTO {
    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 类型id
     */
    private String goodsTypeId;

    /**
     * 子类id
     */
    private String goodsSubClassId;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 区县id
     */
    private String countyId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 分页信息
     */
    private Pages pages;
}
