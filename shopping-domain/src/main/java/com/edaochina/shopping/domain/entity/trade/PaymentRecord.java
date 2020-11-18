package com.edaochina.shopping.domain.entity.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentRecord implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.payment_money
     *
     * @mbggenerated
     */
    private BigDecimal paymentMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.payment_status
     *
     * @mbggenerated
     */
    private Integer paymentStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.payment_type
     *
     * @mbggenerated
     */
    private Integer paymentType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.payment_return
     *
     * @mbggenerated
     */
    private String paymentReturn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.last_query_return
     *
     * @mbggenerated
     */
    private String lastQueryReturn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.create_time
     *
     * @mbggenerated
     */
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.last_query_time
     *
     * @mbggenerated
     */
    private LocalDateTime lastQueryTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.update_time
     *
     * @mbggenerated
     */
    private LocalDateTime updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_record.remark
     *
     * @mbggenerated
     */
    private String remark;


    private String withdrawalRecordId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table payment_record
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_record.id
     *
     * @return the value of payment_record.id
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_record.id
     *
     * @param id the value for payment_record.id
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentReturn() {
        return paymentReturn;
    }

    public void setPaymentReturn(String paymentReturn) {
        this.paymentReturn = paymentReturn;
    }

    public String getLastQueryReturn() {
        return lastQueryReturn;
    }

    public void setLastQueryReturn(String lastQueryReturn) {
        this.lastQueryReturn = lastQueryReturn;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastQueryTime() {
        return lastQueryTime;
    }

    public void setLastQueryTime(LocalDateTime lastQueryTime) {
        this.lastQueryTime = lastQueryTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWithdrawalRecordId() {
        return withdrawalRecordId;
    }

    public void setWithdrawalRecordId(String withdrawalRecordId) {
        this.withdrawalRecordId = withdrawalRecordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_record
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PaymentRecord other = (PaymentRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPaymentMoney() == null ? other.getPaymentMoney() == null : this.getPaymentMoney().equals(other.getPaymentMoney()))
                && (this.getPaymentStatus() == null ? other.getPaymentStatus() == null : this.getPaymentStatus().equals(other.getPaymentStatus()))
                && (this.getPaymentType() == null ? other.getPaymentType() == null : this.getPaymentType().equals(other.getPaymentType()))
                && (this.getPaymentReturn() == null ? other.getPaymentReturn() == null : this.getPaymentReturn().equals(other.getPaymentReturn()))
                && (this.getLastQueryReturn() == null ? other.getLastQueryReturn() == null : this.getLastQueryReturn().equals(other.getLastQueryReturn()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getLastQueryTime() == null ? other.getLastQueryTime() == null : this.getLastQueryTime().equals(other.getLastQueryTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_record
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPaymentMoney() == null) ? 0 : getPaymentMoney().hashCode());
        result = prime * result + ((getPaymentStatus() == null) ? 0 : getPaymentStatus().hashCode());
        result = prime * result + ((getPaymentType() == null) ? 0 : getPaymentType().hashCode());
        result = prime * result + ((getPaymentReturn() == null) ? 0 : getPaymentReturn().hashCode());
        result = prime * result + ((getLastQueryReturn() == null) ? 0 : getLastQueryReturn().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLastQueryTime() == null) ? 0 : getLastQueryTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_record
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", paymentMoney=").append(paymentMoney);
        sb.append(", paymentStatus=").append(paymentStatus);
        sb.append(", paymentType=").append(paymentType);
        sb.append(", paymentReturn=").append(paymentReturn);
        sb.append(", lastQueryReturn=").append(lastQueryReturn);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastQueryTime=").append(lastQueryTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}