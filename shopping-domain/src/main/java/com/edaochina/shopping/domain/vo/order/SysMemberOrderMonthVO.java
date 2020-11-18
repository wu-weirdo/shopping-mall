package com.edaochina.shopping.domain.vo.order;

import com.edaochina.shopping.domain.constants.ProfitConstants;

import java.math.BigDecimal;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 视图类
 * @since : 2019/7/25
 */
public class SysMemberOrderMonthVO {

    private Integer memberNum;

    private BigDecimal profitPrice;

    private String month;

    private Integer orderType;

    private BigDecimal withdrawal;

    @Override
    public String toString() {
        return "SysMemberOrderMonthVO{" +
                "memberNum=" + memberNum +
                ", profitPrice=" + profitPrice +
                ", month='" + month + '\'' +
                ", memberType=" + orderType +
                ", withdrawal=" + withdrawal +
                '}';
    }

    public BigDecimal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(BigDecimal withdrawal) {
        this.withdrawal = withdrawal;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public static class MonthDetail {

        private Integer memberNum;

        private BigDecimal profitPrice;

        private BigDecimal profitPriceSum = BigDecimal.ZERO;

        private Integer merchantMemberNum;

        private BigDecimal merchantProfitPrice;

        private String month;

        private BigDecimal withdrawal;

        public MonthDetail bind(SysMemberOrderMonthVO vo) {
            if (vo.orderType == ProfitConstants.OrderType.USER) {
                this.memberNum = vo.memberNum;
                this.profitPrice = vo.profitPrice;
                this.month = vo.month;
                this.withdrawal = vo.withdrawal;
                this.profitPriceSum = this.profitPriceSum.add(vo.profitPrice);
            }
            if (vo.orderType == ProfitConstants.OrderType.MERCHANT) {
                this.merchantMemberNum = vo.memberNum;
                this.merchantProfitPrice = vo.profitPrice;
                this.month = vo.month;
                this.withdrawal = vo.withdrawal;
                this.profitPriceSum = this.profitPriceSum.add(vo.profitPrice);
            }
            return this;
        }

        public BigDecimal getProfitPriceSum() {
            return profitPriceSum;
        }

        public void setProfitPriceSum(BigDecimal profitPriceSum) {
            this.profitPriceSum = profitPriceSum;
        }

        public BigDecimal getWithdrawal() {
            return withdrawal;
        }

        public void setWithdrawal(BigDecimal withdrawal) {
            this.withdrawal = withdrawal;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
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

        public Integer getMerchantMemberNum() {
            return merchantMemberNum;
        }

        public void setMerchantMemberNum(Integer merchantMemberNum) {
            this.merchantMemberNum = merchantMemberNum;
        }

        public BigDecimal getMerchantProfitPrice() {
            return merchantProfitPrice;
        }

        public void setMerchantProfitPrice(BigDecimal merchantProfitPrice) {
            this.merchantProfitPrice = merchantProfitPrice;
        }
    }
}
