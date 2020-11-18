package com.edaochina.shopping.domain.entity.trade;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WithdrawalRecord extends Model<WithdrawalRecord> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户
     */
    private String userId;

    private String userName;

    private String phone;

    private String account;

    private String userRole;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行卡号码
     */
    private String bankNumber;

    /**
     * 申请提现金额
     */
    private BigDecimal applyMoney;

    /**
     * 手续费
     */
    private BigDecimal chargeFee;

    /**
     * 申请提现时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;

    /**
     * 状态（0-发起申请，1-审批通过，2-审批不通过）
     */
    private Integer status;

    /**
     * 审批拒绝理由
     */
    private String refuseReason;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationTime;

    /**
     * 到账金额
     */
    private BigDecimal toAccountMoney;

    /**
     * 提现类型(1提现到银行卡，2提现到零钱)
     */
    private Integer withdrawalType;


    private String openId;

    /**
     * 开户行选择
     */
    private String bankCode;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
