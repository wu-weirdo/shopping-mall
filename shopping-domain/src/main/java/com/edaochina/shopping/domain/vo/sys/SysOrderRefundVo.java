package com.edaochina.shopping.domain.vo.sys;

import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.entity.trade.PayRefundImgs;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @since : 2019/6/18 14:29
 */
public class SysOrderRefundVo extends PayRefundApply {

    private int orderStatus;

    private String shoperPhone;

    private String handleUserName;

    private int status;

    private String statusText = "";

    public SysOrderRefundVo computeStatus() {
        long hours = Duration.between(getApplyRefundTime(), LocalDateTime.now()).toHours();
        switch (getRefundStatus()) {
            // 协商判断
            case 20:
                if (getCollectUserStatus() == 10) {
                    this.status = 70;
                    this.statusText = "待协商";
                    return this;
                } else {
                    this.status = 80;
                    this.statusText = "已协商";
                    return this;
                }
                //
            case 50:
                this.status = 100;
                this.statusText = "已取消";
                return this;
            case 30:
                this.status = 50;
                this.statusText = "已退款";
                return this;
            case 40:
                this.status = 50;
                this.statusText = "已退款";
                return this;
            case 10:
                if (hours < 24) {
                    if (getShoperHandleStatus() == 20 || getShoperHandleStatus() == 30) {
                        this.status = 10;
                        this.statusText = "待处理";
                        return this;
                    } else {
                        this.status = 90;
                        this.statusText = "退款申请处理中";
                        return this;
                    }
                } else if (hours < 48) {
                    if (getShoperHandleStatus() == 10 && getCollectMerchantStatus() == 10) {
                        this.status = 20;
                        this.statusText = "待联络";
                        return this;
                    } else if (getShoperHandleStatus() == 10 && getCollectMerchantStatus() == 20) {
                        this.status = 30;
                        this.statusText = "已联络";
                        return this;
                    } else if (getShoperHandleStatus() == 20 || getShoperHandleStatus() == 30) {
                        this.status = 10;
                        this.statusText = "待处理";
                        return this;
                    }
                } else {
                    this.status = 10;
                    this.statusText = "待处理";
                    return this;
                }
            default:
                this.status = 10;
                this.statusText = "待处理";
                return this;
        }
    }

    /**
     * 10 平台待处理
     * 20 待联络商家
     * 30 已联络商家
     * 50 已退款
     * 60 已拒绝
     * 70 待协商
     * 80 已协商
     * 90 退款申请处理中
     * 100 已取消
     */


    public int getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    private List<PayRefundImgs> imgs = new ArrayList<>();

    public static SysOrderRefundVo build() {
        return new SysOrderRefundVo();
    }

    public List<PayRefundImgs> getImgs() {
        return imgs;
    }

    public SysOrderRefundVo setImgs(List<PayRefundImgs> imgs) {
        this.imgs = imgs;
        return this;
    }

    public String getShoperPhone() {
        return shoperPhone;
    }

    public void setShoperPhone(String shoperPhone) {
        this.shoperPhone = shoperPhone;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

}
