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
 * 直播课程(LivingCurriculum)表实体类
 *
 * @author wangpenglei
 * @since 2019-08-26 17:24:50
 */
//@JsonIgnoreProperties(value = {"code"}, allowSetters = true)
@ApiModel(description = "课程")
public class LivingCurriculum extends Model<LivingCurriculum> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("上架时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime putawayTime;

    @ApiModelProperty("下架时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastValidTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("讲师姓名")
    private String teacherName;

    @ApiModelProperty("讲师描述")
    private String teacherDescript;

    @ApiModelProperty("首页图片地址")
    private String img;

    @ApiModelProperty("课程介绍")
    private String livingDescript;

    @ApiModelProperty("验证码,6位数")
    private String code;

    @ApiModelProperty("删除标志(0:未删除，1：已删除)")
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;

    @ApiModelProperty("讲师头像")
    private String teacherImg;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("讲师id")
    @TableField("user_id")
    private String teacherId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getPutawayTime() {
        return putawayTime;
    }

    public void setPutawayTime(LocalDateTime putawayTime) {
        this.putawayTime = putawayTime;
    }

    public LocalDateTime getLastValidTime() {
        return lastValidTime;
    }

    public void setLastValidTime(LocalDateTime lastValidTime) {
        this.lastValidTime = lastValidTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherDescript() {
        return teacherDescript;
    }

    public void setTeacherDescript(String teacherDescript) {
        this.teacherDescript = teacherDescript;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLivingDescript() {
        return livingDescript;
    }

    public void setLivingDescript(String livingDescript) {
        this.livingDescript = livingDescript;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getTeacherImg() {
        return teacherImg;
    }

    public void setTeacherImg(String teacherImg) {
        this.teacherImg = teacherImg;
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