package com.edaochina.shopping.domain.vo.goods;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 拼团商品展示内容
 *
 * @author jintian
 * @date 2019/6/19 14:25
 */
@Data
public class AppCollectGoodVO {

    /**
     * 商品id
     */
    private String id;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 商品价
     */
    private BigDecimal goodsPrice;

    /**
     * 商品原价
     */
    private BigDecimal goodsRetailPrice;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 成团数量
     */
    private int collectNum;

    /**
     * 已下单数量
     */
    private int collectedNum;

    private Integer chainFlag;

}
