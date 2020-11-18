package com.edaochina.shopping.domain.vo.sys;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SysRoleVO {

    private Integer id;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 职能描述
     */
    private String functionInfo;

    /**
     * 是否启用(0未启用，1已启用)
     */
    private Integer useFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 成员人数
     */
    private Integer memberCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getFunctionInfo() {
        return functionInfo;
    }

    public void setFunctionInfo(String functionInfo) {
        this.functionInfo = functionInfo;
    }

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
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

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}
