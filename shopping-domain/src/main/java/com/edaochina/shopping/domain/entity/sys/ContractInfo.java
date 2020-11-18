package com.edaochina.shopping.domain.entity.sys;

import java.time.LocalDateTime;

/**
 * <p>
 * 合同信息表
 * </p>
 *
 * @since 2019-05-31
 */

public class ContractInfo {


    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户类型(3.商家，5用户)
     */
    private Integer userType;

    /**
     * 合同模板id
     */
    private Integer contractTemplateId;

    /**
     * 合同模板名称
     */
    private String contractName;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户签署交易号
     */
    private String userSignTransactionId;

    /**
     * 用户签署结果
     */
    private String userSignResult;

    /**
     * 自动签署交易号
     */
    private String autoSignTransactionId;

    /**
     * 自动签署结果
     */
    private String autoSignResult;

    /**
     * 合同模板下载url
     */
    private String downloadContractUrl;

    /**
     * 合同模板展示url
     */
    private String showContractUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getContractTemplateId() {
        return contractTemplateId;
    }

    public void setContractTemplateId(Integer contractTemplateId) {
        this.contractTemplateId = contractTemplateId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserSignTransactionId() {
        return userSignTransactionId;
    }

    public void setUserSignTransactionId(String userSignTransactionId) {
        this.userSignTransactionId = userSignTransactionId;
    }

    public String getUserSignResult() {
        return userSignResult;
    }

    public void setUserSignResult(String userSignResult) {
        this.userSignResult = userSignResult;
    }

    public String getAutoSignTransactionId() {
        return autoSignTransactionId;
    }

    public void setAutoSignTransactionId(String autoSignTransactionId) {
        this.autoSignTransactionId = autoSignTransactionId;
    }

    public String getAutoSignResult() {
        return autoSignResult;
    }

    public void setAutoSignResult(String autoSignResult) {
        this.autoSignResult = autoSignResult;
    }

    public String getDownloadContractUrl() {
        return downloadContractUrl;
    }

    public void setDownloadContractUrl(String downloadContractUrl) {
        this.downloadContractUrl = downloadContractUrl;
    }

    public String getShowContractUrl() {
        return showContractUrl;
    }

    public void setShowContractUrl(String showContractUrl) {
        this.showContractUrl = showContractUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
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
}
