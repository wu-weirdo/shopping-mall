package com.edaochina.shopping.domain.entity.trade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 退款申请表
 * </p>
 *
 * @since 2019-06-17
 */
@Data
public class PayRefundApply {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 处理状态(10待处理，20已同意，30已拒绝，40待联系用户，50已联系用户)
     */
    private Integer handleStatus;

    /**
     * 退款进度(10退款申请处理中，20已拒绝，30已同意待退款，40已同意已退款，50已取消)
     */
    private Integer refundStatus;

    /**
     * 申请退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyRefundTime;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 订单金额
     */
    private BigDecimal totalFee;

    /**
     * 退款方式
     */
    private String refundMethod;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 申请退款金额
     */
    private String applyFee;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;

    /**
     * 退款说明
     */
    private String refundExplain;

    /**
     * 处理退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime handleRefundTime;

    /**
     * 处理人id
     */
    private String handleUserId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商家处理状态
     */
    private Integer shoperHandleStatus;

    /**
     * 商家id
     */
    private String shoperId;

    /**
     * 申请退款状态(10未取消，已取消)
     */
    private Integer applyRefundStatus;

    /**
     * 商家拒绝原因
     */
    private String shoperRefuseReason;

    private Integer collectUserStatus;

    private Integer collectMerchantStatus;

    private String outOrderNo;

    private Integer orderType;


    private Integer remitStatus;

    public String getOutOrderNo() {
        if (outOrderNo == null) {
            return orderId;
        }
        return outOrderNo;
    }

    public PayRefundApply setId(Integer id) {
        this.id = id;
        return this;
    }

}
