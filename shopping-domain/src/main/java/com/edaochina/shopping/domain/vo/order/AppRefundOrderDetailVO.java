package com.edaochina.shopping.domain.vo.order;

public class AppRefundOrderDetailVO {

    private AppOrderDetailVO appOrderDetailVO;

    private AppRefundApplyVO appRefundApplyVO;

    public AppOrderDetailVO getAppOrderDetailVO() {
        return appOrderDetailVO;
    }

    public void setAppOrderDetailVO(AppOrderDetailVO appOrderDetailVO) {
        this.appOrderDetailVO = appOrderDetailVO;
    }

    public AppRefundApplyVO getAppRefundApplyVO() {
        return appRefundApplyVO;
    }

    public void setAppRefundApplyVO(AppRefundApplyVO appRefundApplyVO) {
        this.appRefundApplyVO = appRefundApplyVO;
    }
}
