package com.edaochina.shopping.domain.entity.order;

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
 * @author xww
 * @since 2018-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderMain extends Model<OrderMain> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    private String id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商家id
     */
    private String shoperId;

    /**
     * 商家名称
     */
    private String shoperName;

    /**
     * 商家地址
     */
    private String shoperAddress;

    /**
     * 商家小区
     */
    private String shoperArea;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;


    /**
     * 用户id
     */
    private String userId;

    /**
     * 联系人
     */
    private String userName;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 商品数量
     */
    private Integer goodsNum;


    /**
     * 实际费用
     */
    private BigDecimal actualPrice;

    /**
     * 成本总价
     */
    private BigDecimal costPrice;

    /**
     * 合计总价
     */
    private BigDecimal totalPrice;


    /**
     * 订单状态（10待分享，20待使用，30待评价，40已退款，50已评价，60已过期，70待支付，80付款失败）
     */
    private Integer status;


    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除（10否，20是）
     */
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    /**
     * 核销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffTime;

    /**
     * 退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 核销状态
     */
    private Integer writeOffStatus;

    /**
     * 分润状态
     */
    private Integer calcStatus;

    /**
     * 是否有退款(0:无退款，1:有退款)
     */
    private int hasRefund;

    public boolean hasRefund() {
        return this.hasRefund == 1;
    }

    private Integer collectFlag;

    private Long collectInfoId;

    /**
     * 分享的商家
     */
    private String shareMerchantId;
    /**
     * 分享的费率
     */
    private BigDecimal shareRate;

    /**
     * 商品类型
     */
    private Integer goodsType;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
