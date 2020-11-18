package com.edaochina.shopping.domain.vo.order;

import com.edaochina.shopping.domain.entity.order.OrderErrorBack;

import java.util.List;

public class OrderErrorBackVO extends OrderErrorBack {

    /**
     * 所属区县
     */
    private String address;

    /**
     * 异常订单图片凭证
     */
    private List<String> orderErrorImgs;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getOrderErrorImgs() {
        return orderErrorImgs;
    }

    public void setOrderErrorImgs(List<String> orderErrorImgs) {
        this.orderErrorImgs = orderErrorImgs;
    }
}
