package com.edaochina.shopping.domain.entity.goods;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品详情(富文本)
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsAttach extends Model<GoodsAttach> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 商品详情
     */
    private String content;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
