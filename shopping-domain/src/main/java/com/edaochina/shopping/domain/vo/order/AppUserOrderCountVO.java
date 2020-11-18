package com.edaochina.shopping.domain.vo.order;

/**
 * 小程序用户订单数
 */
public class AppUserOrderCountVO {

    /**
     * 商家名称（便于跳转商品搜索页用的）
     */
    private String shopName;
    /**
     * 已使用订单数
     */
    private Integer usedOrderNum;

    /**
     * 未使用订单数
     */
    private Integer unusedOrderNum;

    /**
     * 用户总订单数
     */
    private Integer orderNum;

    public Integer getUsedOrderNum() {
        return usedOrderNum;
    }

    public void setUsedOrderNum(Integer usedOrderNum) {
        this.usedOrderNum = usedOrderNum;
    }

    public Integer getUnusedOrderNum() {
        return unusedOrderNum;
    }

    public void setUnusedOrderNum(Integer unusedOrderNum) {
        this.unusedOrderNum = unusedOrderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
