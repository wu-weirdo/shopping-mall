package com.edaochina.shopping.domain.constants;

/**
 * 退款订单状态枚举
 */
public interface PayRefundApplyStatus {

    final class HandleStatus {

        /**
         * 待处理
         */
        public static final int PENDING = 10;

        /**
         * 已同意
         */
        public static final int AGREED = 20;

        /**
         * 已拒绝
         */
        public static final int REJECTED = 30;

    }

    final class RefundStatus {

        /**
         * 退款申请处理中
         */
        public static final int PROCESSING = 10;

        /**
         * 已拒绝
         */
        public static final int REJECTED = 20;

        /**
         * 已同意待退款
         */
        public static final int TO_BE_REFUNDED = 30;

        /**
         * 已同意已退款
         */
        public static final int REFUNDED = 40;

        /**
         * 已取消
         */
        public static final int HAS_CANCEL = 50;

    }

    final class RefundRemitStatus {
        // 待处理
        public static final int WAIT_REMIT = 10;
        // 退款成功
        public static final int REMIT_SUCCESS = 20;
        // 已提交退款
        public static final int REMIT_SUBMMIT = 40;
        // 退款失败
        public static final int REMIT_FAIL = 30;
    }

}
