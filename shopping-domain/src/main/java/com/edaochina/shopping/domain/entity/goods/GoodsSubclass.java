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
public class GoodsSubclass extends Model<GoodsSubclass> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品子类表
     */
    private String id;

    /**
     * 商品种类id
     */
    private String typeId;

    /**
     * 商品子类名称
     */
    private String subclassName;

    /**
     * 顺序
     */
    private Integer sort;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
