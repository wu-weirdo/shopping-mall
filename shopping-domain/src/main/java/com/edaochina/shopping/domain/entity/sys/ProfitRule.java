package com.edaochina.shopping.domain.entity.sys;

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
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProfitRule extends Model<ProfitRule> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 商家分润比例
     */
    private BigDecimal merchantRatio;

    /**
     * 来源商家分润比例
     */
    private BigDecimal sourceMerchantRatio;

    /**
     * 团长（骑手）分润比例
     */
    private BigDecimal riderRatio;

    /**
     * 代理商分润比例
     */
    private BigDecimal agentRatio;

    /**
     * 平台分润比例
     */
    private BigDecimal platformRatio;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
