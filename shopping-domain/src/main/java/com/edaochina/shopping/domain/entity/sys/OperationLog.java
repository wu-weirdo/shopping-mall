package com.edaochina.shopping.domain.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 操作记录
 * @since : 2019/7/2 10:10
 */
public class OperationLog {

    @TableId(type = IdType.AUTO)
    private int id;

    private String userId;

    private String userType;

    private String userName;

    private String operation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String ip;

    private String method;

    private String params;

    private String url;

    /**
     * 备注，预留字段
     */
    private String remark;

    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userType=" + userType +
                ", userName='" + userName + '\'' +
                ", operation='" + operation + '\'' +
                ", createTime=" + createTime +
                ", ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", url='" + url + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
