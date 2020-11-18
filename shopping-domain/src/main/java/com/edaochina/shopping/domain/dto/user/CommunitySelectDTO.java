package com.edaochina.shopping.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangpl
 * @since 2019/7/15
 */
@ApiModel(value = "CommunitySelectDTO")
public class CommunitySelectDTO {

    @ApiModelProperty(value = "经度,搜索时不使用")
    private Double longitude;
    @ApiModelProperty(value = "纬度,搜索时不使用")
    private Double latitude;
    @ApiModelProperty(value = "小区名称关键字搜索")
    private String name;
    @ApiModelProperty(value = "最大距离,选择附近小区时使用")
    private Long maxDistinct;
    @ApiModelProperty(value = "用户id,获取历史小区时使用")
    private String userId;
    @ApiModelProperty(value = "微信id,获取历史小区时使用")
    private String openId;
    @ApiModelProperty(value = "当前小区id")
    private String currentCommunityId;
    @ApiModelProperty(value = "返回历史小区数量")
    private Integer limit;
    @ApiModelProperty(value = "区县id,优先级大于经纬度")
    private Integer countyId;
    @ApiModelProperty(hidden = true)
    private List<String> historyIds;

    public boolean hasUser() {
        return userId != null && openId != null && currentCommunityId != null && countyId != null;
    }

    public CommunityAppListDTO toCommunityAppListDTO() {
        CommunityAppListDTO communityAppListDTO = new CommunityAppListDTO();
        communityAppListDTO.setCountyId(this.countyId);
        communityAppListDTO.setLatitude(this.latitude);
        communityAppListDTO.setLongitude(this.longitude);
        communityAppListDTO.setMaxDistinct(this.maxDistinct);
        communityAppListDTO.setName(this.name);
        return communityAppListDTO;
    }

    @Override
    public String toString() {
        return "CommunitySelectDTO{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", name='" + name + '\'' +
                ", maxDistinct=" + maxDistinct +
                ", userId='" + userId + '\'' +
                ", openId='" + openId + '\'' +
                ", currentCommunityId='" + currentCommunityId + '\'' +
                ", limit=" + limit +
                ", countyId=" + countyId +
                '}';
    }

    public List<String> getHistoryIds() {
        return historyIds;
    }

    public void setHistoryIds(List<String> historyIds) {
        this.historyIds = historyIds;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMaxDistinct() {
        return maxDistinct;
    }

    public void setMaxDistinct(Long maxDistinct) {
        this.maxDistinct = maxDistinct;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCurrentCommunityId() {
        return currentCommunityId;
    }

    public void setCurrentCommunityId(String currentCommunityId) {
        this.currentCommunityId = currentCommunityId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
