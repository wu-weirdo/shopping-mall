package com.edaochina.shopping.domain.entity.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShoppingCartInfo {

    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private String goodId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 是否删除（10否，20是）
     */
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 购物车有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastValidTime;
}
