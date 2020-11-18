package com.edaochina.shopping.domain.dto.sys;

import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @since : 2019/6/18 14:29
 */
public class SysOrderRefundDTO extends PayRefundApply {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginApplyRefundTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endApplyRefundTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginHandleRefundTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endHandleRefundTime;

    private String merchantTitle;

    private String goodsName;

    private String merchantPhone;

    private String userCityAddress;

    private String shopId;

    @Override
    public String toString() {
        return "SysOrderRefundDTO{" +
                "beginApplyRefundTime=" + beginApplyRefundTime +
                ", endApplyRefundTime=" + endApplyRefundTime +
                ", beginHandleRefundTime=" + beginHandleRefundTime +
                ", endHandleRefundTime=" + endHandleRefundTime +
                ", merchantTitle='" + merchantTitle + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", merchantPhone='" + merchantPhone + '\'' +
                ", userCityAddress='" + userCityAddress + '\'' +
                ", shopId='" + shopId + '\'' +
                ", pages=" + pages +
                "} " + super.toString();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    private Pages pages = new Pages();

    public static SysOrderRefundDTO build() {
        return new SysOrderRefundDTO();
    }

    public PayRefundApply toEntity() {
        PayRefundApply payRefundApply = new PayRefundApply();
        BeanUtils.copyProperties(this, payRefundApply);
        return payRefundApply;
    }

    @Override
    public SysOrderRefundDTO setId(Integer id) {
        return (SysOrderRefundDTO) super.setId(id);
    }

    public String getUserCityAddress() {
        return userCityAddress;
    }

    public void setUserCityAddress(String userCityAddress) {
        this.userCityAddress = userCityAddress;
    }

    public String getMerchantTitle() {
        return merchantTitle;
    }

    public void setMerchantTitle(String merchantTitle) {
        this.merchantTitle = merchantTitle;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public LocalDateTime getBeginApplyRefundTime() {
        return beginApplyRefundTime;
    }

    public void setBeginApplyRefundTime(LocalDateTime beginApplyRefundTime) {
        this.beginApplyRefundTime = beginApplyRefundTime;
    }

    public LocalDateTime getEndApplyRefundTime() {
        return endApplyRefundTime;
    }

    public void setEndApplyRefundTime(LocalDateTime endApplyRefundTime) {
        this.endApplyRefundTime = endApplyRefundTime;
    }

    public LocalDateTime getBeginHandleRefundTime() {
        return beginHandleRefundTime;
    }

    public void setBeginHandleRefundTime(LocalDateTime beginHandleRefundTime) {
        this.beginHandleRefundTime = beginHandleRefundTime;
    }

    public LocalDateTime getEndHandleRefundTime() {
        return endHandleRefundTime;
    }

    public void setEndHandleRefundTime(LocalDateTime endHandleRefundTime) {
        this.endHandleRefundTime = endHandleRefundTime;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

}
