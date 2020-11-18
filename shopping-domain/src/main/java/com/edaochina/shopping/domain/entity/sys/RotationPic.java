package com.edaochina.shopping.domain.entity.sys;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xww
 * @since 2019-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RotationPic extends Model<RotationPic> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 轮播图图片名称
     */
    private String picName;

    /**
     * 图片链接地址
     */
    private String picUrl;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertTime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 跳转地址
     */
    private String jumpUrl;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
