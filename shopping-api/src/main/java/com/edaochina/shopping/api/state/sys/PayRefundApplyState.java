package com.edaochina.shopping.api.state.sys;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 退款订单状态枚举
 * @since : 2019/6/25 10:24
 */
public enum PayRefundApplyState {

    PENDING(10, "PENDING", "待处理"),

    REFUNDED(20, "REFUNDED", "已同意"),

    REJECTED(30, "REJECTED", "已拒绝"),

    TO_BE_COORDINATE(40, "TO_BE_COORDINATE", "待联络"),

    COORDINATED(50, "COORDINATED", "已联络");

    private int code;
    private String key;
    private String text;

    PayRefundApplyState(int code, String key, String text) {
        this.code = code;
        this.key = key;
        this.text = text;
    }

    public static String getText(int code) {
        PayRefundApplyState[] payRefundApplyStates = values();
        for (PayRefundApplyState payRefundApplyState : payRefundApplyStates) {
            if (payRefundApplyState.getCode() == code) {
                return payRefundApplyState.getText();
            }
        }
        return null;
    }

    public static PayRefundApplyState valueOf(int code) {
        PayRefundApplyState[] payRefundApplyStates = values();
        for (PayRefundApplyState payRefundApplyState : payRefundApplyStates) {
            if (payRefundApplyState.getCode() == code) {
                return payRefundApplyState;
            }
        }
        return null;
    }

    public static String getKey(int code) {
        PayRefundApplyState[] payRefundApplyStates = values();
        for (PayRefundApplyState payRefundApplyState : payRefundApplyStates) {
            if (payRefundApplyState.getCode() == code) {
                return payRefundApplyState.getKey();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
