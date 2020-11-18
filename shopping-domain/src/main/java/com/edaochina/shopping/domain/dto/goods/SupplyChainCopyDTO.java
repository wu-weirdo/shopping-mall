package com.edaochina.shopping.domain.dto.goods;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * SupplyChainCopyDTO
 *
 * @author wangpenglei
 * @since 2019/11/6 10:11
 */
public class SupplyChainCopyDTO {

    private String id;

    @NotNull
    private Boolean hot;

    @NotNull
    private Integer collectFlag;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastValidTime;

    @NotNull
    private Integer collectNum;

    @NotNull
    private String collectInfo;

    @Override
    public String toString() {
        return "SupplyChainCopyDTO{" +
                "id='" + id + '\'' +
                ", hot=" + hot +
                ", collectFlag=" + collectFlag +
                ", lastValidTime=" + lastValidTime +
                ", collectNum=" + collectNum +
                ", collectInfo='" + collectInfo + '\'' +
                '}';
    }

    public LocalDateTime getLastValidTime() {
        return lastValidTime;
    }

    public void setLastValidTime(LocalDateTime lastValidTime) {
        this.lastValidTime = lastValidTime;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public String getCollectInfo() {
        return collectInfo;
    }

    public void setCollectInfo(String collectInfo) {
        this.collectInfo = collectInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

}
