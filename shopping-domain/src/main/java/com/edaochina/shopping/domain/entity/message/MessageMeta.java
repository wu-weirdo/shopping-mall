package com.edaochina.shopping.domain.entity.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.edaochina.shopping.domain.constants.MessageConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (MessageMeta)表实体类
 *
 * @author wangpenglei
 * @since 2019-09-17 15:58:43
 */
@SuppressWarnings("serial")
public class MessageMeta extends Model<MessageMeta> {
    //主键
    @TableId(type = IdType.AUTO)
    private Integer id;
    //内容
    private String content;
    //平台
    private Integer stage;
    //类型
    private Integer kind;
    //推送时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pushTime;
    //推送方式
    private Integer mode;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    //推送人群
    private Integer role;
    /**
     * 状态
     */
    private Integer state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String title;

    public String getStageText() {
        return MessageConstants.Stage.getText(this.getStage());
    }

    public String getKindText() {
        return MessageConstants.Kind.getText(this.getKind());
    }

    public String getModeText() {
        return MessageConstants.Mode.getText(this.getMode());
    }

    public String getRoleText() {
        switch (this.getRole()) {
            case 0:
                return "全部";
            case 1:
                return "管理员";
            case 3:
                return "商户";
            case 4:
                return "区县合伙人";
            case 5:
                return "用户";
            case 6:
                return "社区合伙人";
            default:
                throw new IllegalStateException("Unexpected value: " + this.getRole());
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public LocalDateTime getPushTime() {
        return pushTime;
    }

    public void setPushTime(LocalDateTime pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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