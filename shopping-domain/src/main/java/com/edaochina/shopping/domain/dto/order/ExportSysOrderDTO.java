package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 24h
 */
public class ExportSysOrderDTO implements Serializable {

    private Date startTime;

    private Date endTime;

    private String countyCode;

    private String roleId;

    private String userId;

    private Integer deleteFlag;

    private Integer collectFlag;

    private Pages pages;

    private String goodsTypeId;

    @Override
    public String toString() {
        return "ExportSysOrderDTO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", countyCode='" + countyCode + '\'' +
                ", roleId='" + roleId + '\'' +
                ", userId='" + userId + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", collectFlag=" + collectFlag +
                ", pages=" + pages +
                ", goodsTypeId='" + goodsTypeId + '\'' +
                '}';
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }
}
