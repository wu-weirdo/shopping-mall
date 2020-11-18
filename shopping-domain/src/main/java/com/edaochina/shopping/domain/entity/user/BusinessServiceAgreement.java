package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 商家服务协议
 * @since : 2019/7/9 15:14
 */
public class BusinessServiceAgreement {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer version;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer synchronization;

    public void increase() {
        ++version;
    }

    public void sync() {
        // todo 更新fadada合同内容
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getSynchronization() {
        return synchronization;
    }

    public void setSynchronization(Integer synchronization) {
        this.synchronization = synchronization;
    }
}
