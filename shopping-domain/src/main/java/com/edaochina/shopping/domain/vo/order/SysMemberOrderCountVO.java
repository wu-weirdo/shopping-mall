package com.edaochina.shopping.domain.vo.order;

import java.math.BigDecimal;

/**
 * @author jintian
 * @date 2019/4/1 15:56
 */
public class SysMemberOrderCountVO {

    private String month;

    private String agentName;

    private Integer memberNum;

    private BigDecimal profitPrice;

    private Integer unMemberNum;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public BigDecimal getProfitPrice() {
        return profitPrice;
    }

    public void setProfitPrice(BigDecimal profitPrice) {
        this.profitPrice = profitPrice;
    }

    public Integer getUnMemberNum() {
        return unMemberNum;
    }

    public void setUnMemberNum(Integer unMemberNum) {
        this.unMemberNum = unMemberNum;
    }
}
