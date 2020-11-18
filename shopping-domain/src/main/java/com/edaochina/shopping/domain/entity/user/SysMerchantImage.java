package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商家照片表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMerchantImage extends Model<SysMerchantImage> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 商户id
     */
    private String merchantId;

    private String image;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String businssImage;

    private String foodImage;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
