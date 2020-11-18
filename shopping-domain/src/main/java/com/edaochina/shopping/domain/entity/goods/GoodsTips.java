package com.edaochina.shopping.domain.entity.goods;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsTips extends Model<GoodsTips> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品提示
     */
    private String id;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 提示内容
     */
    private String content;

    /**
     * 顺序
     */
    private Integer sort;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
