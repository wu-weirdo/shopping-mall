package com.edaochina.shopping.domain.constants;

/**
 * 提现状态
 *
 * @author jintian
 * @date 2019/7/23 19:36
 */
public interface WithdrawalConstants {
    /**
     * 退款申请中
     */
    int APPLYING = 0;

    /**
     * 退款已到账
     */
    int HAS_TO_ACCOUNT = 1;

    /**
     * 退款已拒绝
     */
    int REFUSE = 2;

    /**
     * 退款等待到账
     */
    int AGREE_WAIT_TO_ACCOUNT = 3;

    /**
     * 退款到账失败
     */
    int FAIL_TO_ACCOUNT = 4;
}
