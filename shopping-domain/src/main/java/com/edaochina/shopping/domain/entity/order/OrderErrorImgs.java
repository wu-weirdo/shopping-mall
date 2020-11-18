package com.edaochina.shopping.domain.entity.order;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 异常订单图片凭证
 * </p>
 *
 * @since 2019-05-22
 */

public class OrderErrorImgs extends Model<OrderErrorImgs> {


    private Integer id;

    /**
     * 异常订单id
     */
    private Integer orderErrorId;

    /**
     * 图片url
     */
    private String imgUrl;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderErrorId() {
        return orderErrorId;
    }

    public void setOrderErrorId(Integer orderErrorId) {
        this.orderErrorId = orderErrorId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
