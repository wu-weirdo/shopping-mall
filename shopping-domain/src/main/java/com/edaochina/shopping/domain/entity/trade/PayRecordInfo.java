package com.edaochina.shopping.domain.entity.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PayRecordInfo {
    private String id;

    /**
     * 微信返回的prepay_id
     */
    private String prepayId;

    /**
     * 微信回调的信息，包括失败信息
     */
    private String returnPayInfo;

    /**
     * 支付人
     */
    private String payUserId;

    /**
     * 支付类型(1.商家会员费2.用户支付3.用户购物车费用)
     */
    private Integer payType;

    /**
     * 支付状态(0.已支付未回调1.已支付，回调成功2.已支付，回调失败3.支付超时)
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;
}
