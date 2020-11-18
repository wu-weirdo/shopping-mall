package com.edaochina.shopping.domain.entity.trade;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 供应链交易分润明细(TradeDetailChainProfit)实体类
 *
 * @author makejava
 * @since 2019-11-13 13:48:43
 */
@Data
public class TradeDetailChainProfit implements Serializable {
    private static final long serialVersionUID = -38524749531643017L;

    private Integer id;
    /**
     * 订单主键
     */
    private String orderId;
    /**
     * 用户角色类型(3:商家，4:代理商)
     */
    private Integer userType;
    /**
     * 分润用户id
     */
    private String userId;
    /**
     * 分润金额
     */
    private BigDecimal chainProfit;
    /**
     * 分润到账金额
     */
    private BigDecimal toAccountMoney;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}