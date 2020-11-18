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
 * @author jintian
 * @date 2019/3/22 11:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MemberOrderCalcDetail extends Model<MemberOrderCalcDetail> {

    private Long id;

    /**
     * 会员订单
     */
    private String memberOrderId;

    /**
     * 会员类型(1:用户会员，2:商户会员)
     */
    private Integer memberType;

    /**
     * 分润类型(1:商家,2:代理商，3：公司自己)
     */
    private Integer profitType;

    /**
     * 分润用户
     */
    private String userId;

    /**
     * 分润结果金额
     */
    private BigDecimal profitResult;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否是补充分润记录(0:否，1:是)
     */
    private boolean supplementType = false;
    /**
     * 是否已经补充分润(0:否，1:是)
     */
    private boolean supplementStatus = false;
    /**
     * 区县id
     */
    private String countyId;
    private BigDecimal memberPrice;

    public MemberOrderCalcDetail(String memberOrderId, Integer memberType, Integer profitType, String userId, BigDecimal profitResult) {
        this.memberOrderId = memberOrderId;
        this.memberType = memberType;
        this.profitType = profitType;
        this.userId = userId;
        this.profitResult = profitResult;
    }


    public MemberOrderCalcDetail() {
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
