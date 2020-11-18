package com.edaochina.shopping.domain.vo.trade;

import com.edaochina.shopping.domain.entity.trade.ShoppingCartInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jintian
 * @date 2019/4/17 16:56
 */
@Data
public class ShoppingCartInfoVo extends ShoppingCartInfo {

    private String shopId;

    private String shopName;

    private String goodsName;

    private String shopTitle;

    private String goodsSpec;

    private String imgUrl;

    private int goodsNum;

    private BigDecimal goodsPrice;

    private Integer limitFlag;

    private Integer limitBuyNum;

    private Integer buyNum;

    private String distance;

    private String shopPhone;

    private String shopAddress;

    private String longitude = "0";

    private String latitude = "0";

    /**
     * 过期标识(0未过期，1已过期)
     */
    private Integer expiredFlag;

}
