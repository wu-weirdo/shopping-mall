package com.edaochina.shopping.domain.dto.trade;

import com.edaochina.shopping.domain.base.page.Pages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 推广商品收入请求
 *
 * @author jintian
 * @date 2019/11/5 15:58
 */
@Data
public class SysShareGoodsProfitDTO {
    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 分享的商家id
     */
    private String shareMerchantId;

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

    /**
     * 1:拼团订单，2：供应链商品，3：爆品
     */
    private Integer orderType;

    /**
     * 分享标识（1：查询分享的，2：查询被分享的）
     */
    private Integer shareFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 分页信息
     */
    private Pages pages;
}
