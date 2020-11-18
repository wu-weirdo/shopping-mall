package com.edaochina.shopping.domain.entity.order;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 会员订单
 *
 * @author jintian
 * @date 2019/3/21 15:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MemberOrder extends Model<MemberOrder> {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 订单类型(0:用户,1：商家)
     */
    private Integer orderType;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 外部交易订单号
     */
    private String outOrderNo;

    /**
     * 是否分润(0:未分润，1：已分润)
     */
    private Integer calcStatus;

    /**
     * 支付状态(0:已支付未回调，1：已支付成功，3:支付失败)
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 核销时间
     */
    private LocalDateTime calcTime;

    /**
     * 会员费
     */
    private BigDecimal memberPrice;

    /**
     * 区县id
     */
    private String countyId;

    /**
     * 合伙人id
     */
    private String partenerId;

    /**
     * 所属区县合伙人id
     *
     * @param orderType
     * @param userId
     * @param outOrderNo
     * @param memberPrice
     */
    private String agentId;

    public MemberOrder(Integer orderType, String userId, String outOrderNo, BigDecimal memberPrice) {
        this.orderType = orderType;
        this.userId = userId;
        this.outOrderNo = outOrderNo;
        this.memberPrice = memberPrice;
    }

    public MemberOrder() {
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
