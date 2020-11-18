package com.edaochina.shopping.domain.entity.live;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 直播拉黑用户列表(LivingBlockUser)表实体类
 *
 * @author wangpenglei
 * @since 2019-08-26 17:48:12
 */
@ApiModel(description = "黑名单")
public class LivingBlockUser extends Model<LivingBlockUser> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("直播id")
    private Integer livingId;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String blockUserId;

    @ApiModelProperty("用户类型(普通用户:5,社群合伙人：6，代理商：4，商户：3)")
    private Integer userType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty("是否删除(0:未删除，1：已删除)")
    private Integer delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLivingId() {
        return livingId;
    }

    public void setLivingId(Integer livingId) {
        this.livingId = livingId;
    }

    public String getBlockUserId() {
        return blockUserId;
    }

    public void setBlockUserId(String blockUserId) {
        this.blockUserId = blockUserId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}