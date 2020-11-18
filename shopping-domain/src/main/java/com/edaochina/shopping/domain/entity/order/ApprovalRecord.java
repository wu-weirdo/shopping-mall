package com.edaochina.shopping.domain.entity.order;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ApprovalRecord extends Model<ApprovalRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private String id;

    /**
     * 商家id
     */
    private String shopId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品种类
     */
    private String goodsTypeId;

    /**
     * 商品子类
     */
    private String goodsSubclassId;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品门市价
     */
    private BigDecimal goodsRetailPrice;

    /**
     * 商品成本价
     */
    private BigDecimal goodsCostPrice;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 是否审批通过（10未审批，20不通过，30通过）
     */
    private Integer approvalFlag;

    /**
     * 拒绝理由
     */
    private String reason;

    /**
     * 审核时间
     */
    private LocalDateTime approvalTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
