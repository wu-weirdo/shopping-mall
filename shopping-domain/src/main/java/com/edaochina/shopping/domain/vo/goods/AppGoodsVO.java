package com.edaochina.shopping.domain.vo.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 24h
 */
@Data
public class AppGoodsVO implements Serializable {

    private BigDecimal goodsPrice;

    private BigDecimal goodsRetailPrice;

    private Integer orderNum;

    private String shopId;

    private String goodsId;

    private String goodsName;

    private String title;

    private String img;

    private String goodsSpec;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastValidTime;

    private Boolean hot;

    private Integer collectFlag;

    /**
     * 是否联盟商家
     */
    private Integer league;

}
