package com.edaochina.shopping.domain.entity.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * 供应链发货管理(ChainRecipientDetail)实体类
 *
 * @author makejava
 * @since 2019-11-13 15:05:07
 */
@Data
public class ChainRecipientDetail implements Serializable {
    private static final long serialVersionUID = 982988112459706971L;

    private Integer id;
    /**
     * 供应链id
     */
    private String chainId;
    /**
     * 复用的商品id
     */
    private String goodsId;
    /**
     * 发货状态(0:未申请,1:已申请同意,2:已发货待收货，3:已收货，4:收货异常)
     */
    private Integer shipmentsStatus;
    /**
     * 付款状态(0:未付款，1:已付款待确认，2:已收到款，3:未收到打款)
     */
    private Integer payStatus;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 发货时间(已确定收到款时间开始算)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shipmentsTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 打款图片
     */
    private String payImage;
    /**
     * 收件人
     */
    private String recipients;
    /**
     * 收件人地址
     */
    private String consigneeAddress;
    /**
     * 收件人号码
     */
    private String receiptNumber;

    private Integer canApply;

    private String merchantId;

    private String refuseReason;

}