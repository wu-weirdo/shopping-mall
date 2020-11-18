package com.edaochina.shopping.domain.vo.sys;

import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @since : 2019/6/21 11:34
 */
public class ExportSysOrderRefundVo extends PayRefundApply implements Serializable {

    /**
     * 绑定区县
     */
    private String address;

    /**
     * 商户交易号
     */
    private String orderNo;

    /**
     * 商家名称
     */
    private String merchantTitle;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品类型名称
     */
    private String goodsTypeName;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 订单创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderCreateTime;

    /**
     * 处理人名称
     */
    private String handleUserName;

    /**
     * 订单状态
     */
    private int orderStatus;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(LocalDateTime orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
