package com.edaochina.shopping.domain.entity.sys;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jintian
 * @date 2019/4/2 11:55
 */
public class AreaAgentProfit {

    private Integer id;

    private BigDecimal profitRatio;

    private String countyId;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private Integer supplementStatus = 0;

    private Integer profitType;

    public AreaAgentProfit() {
    }

    public AreaAgentProfit(BigDecimal profitRatio, String countyId, Integer profitType) {
        this.profitRatio = profitRatio;
        this.countyId = countyId;
        this.profitType = profitType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getProfitRatio() {
        return profitRatio;
    }

    public void setProfitRatio(BigDecimal profitRatio) {
        this.profitRatio = profitRatio;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getProfitType() {
        return profitType;
    }

    public void setProfitType(Integer profitType) {
        this.profitType = profitType;
    }

    public Integer getSupplementStatus() {
        return supplementStatus;
    }

    public void setSupplementStatus(Integer supplementStatus) {
        this.supplementStatus = supplementStatus;
    }
}
