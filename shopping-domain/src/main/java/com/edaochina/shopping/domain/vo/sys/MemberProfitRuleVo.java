package com.edaochina.shopping.domain.vo.sys;

import java.math.BigDecimal;

/**
 * 分润规则展示
 *
 * @author jintian
 * @date 2019/3/24 17:30
 */
public class MemberProfitRuleVo {
    private String profitType;

    private BigDecimal merchantRatio;

    private BigDecimal agentRatio;

    private BigDecimal companyRatio;

    public String getProfitType() {
        return profitType;
    }

    public void setProfitType(String profitType) {
        this.profitType = profitType;
    }

    public BigDecimal getMerchantRatio() {
        return merchantRatio;
    }

    public void setMerchantRatio(BigDecimal merchantRatio) {
        this.merchantRatio = merchantRatio;
    }

    public BigDecimal getAgentRatio() {
        return agentRatio;
    }

    public void setAgentRatio(BigDecimal agentRatio) {
        this.agentRatio = agentRatio;
    }

    public BigDecimal getCompanyRatio() {
        return companyRatio;
    }

    public void setCompanyRatio(BigDecimal companyRatio) {
        this.companyRatio = companyRatio;
    }
}
