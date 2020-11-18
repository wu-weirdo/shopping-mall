package com.edaochina.shopping.domain.entity.goods;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品拼团信息
 *
 * @author jintian
 * @date 2019/6/19 17:35
 */
@Data
public class GoodsCollectInfo implements Serializable {

    private Integer id;

    private String goodsId;

    private LocalDateTime lastValidTime;

    private Integer collectNum;

    private Integer buyNum;

    private String collectInfo;

    private LocalDateTime createTime;

    private String createUserId;

    private BigDecimal collectPrice;

    private String remark;

    private Integer collectStatus;
}
