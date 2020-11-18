package com.edaochina.shopping.domain.dto.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RotationPicDTO implements Serializable {

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
}
