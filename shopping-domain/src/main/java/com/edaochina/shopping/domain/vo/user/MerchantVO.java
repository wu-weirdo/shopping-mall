package com.edaochina.shopping.domain.vo.user;

import com.edaochina.shopping.domain.entity.user.Community;
import com.edaochina.shopping.domain.entity.user.SysMerchantImage;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商户列表VO by 张志侃
 * </p>
 *
 * @author zzk
 * @since 2019-1-7
 */
public class MerchantVO implements Serializable {

    private String id;

    private String qrCode;

    private int status;

    private String account;
    /**
     * 手机号码
     */
    private String phone;

    /**
     * 代理商姓名
     */
    private String name;

    private String address;

    private String title;

    private Double longitude;

    private Double latitude;
    /**
     * 身份证号码
     */
    private String identityNo;

    /**
     * 创建时间 默认当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 商家订单数
     */
    private Integer orderCount;

    /**
     * 总利润
     */
    private BigDecimal totalProfit;

    /**
     * 商品数
     */
    private Integer goodsCount;
    /**
     * 绑定的小区id,格式1,3,6
     */
    private String community;

    /**
     * 0天数，1时间
     */
    private Integer memberType;

    /**
     * 天数
     */
    private Integer memberNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime memberTime;

    private String countyId;

    /**
     * 小区列表
     */
    private List<Community> communities;

    private List<SysMerchantImage> images;

    private String businssImage;

    private Integer checkStatus;

    private String invitatCode;

    private String houseNumber;

    private String foodImage;

    @JsonFormat(pattern = "HH:mm:ss")
    private Time startBusiness;

    @JsonFormat(pattern = "HH:mm:ss")
    private Time endBusiness;

    /**
     * 是否联盟
     */
    private Integer league;

    @Override
    public String toString() {
        return "MerchantVO{" +
                "id='" + id + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", status=" + status +
                ", account='" + account + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", title='" + title + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", identityNo='" + identityNo + '\'' +
                ", createTime=" + createTime +
                ", orderCount=" + orderCount +
                ", totalProfit=" + totalProfit +
                ", goodsCount=" + goodsCount +
                ", community='" + community + '\'' +
                ", memberType=" + memberType +
                ", memberNum=" + memberNum +
                ", memberTime=" + memberTime +
                ", countyId='" + countyId + '\'' +
                ", communities=" + communities +
                ", images=" + images +
                ", businssImage='" + businssImage + '\'' +
                ", checkStatus=" + checkStatus +
                ", invitatCode='" + invitatCode + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", foodImage='" + foodImage + '\'' +
                ", startBusiness=" + startBusiness +
                ", endBusiness=" + endBusiness +
                ", league=" + league +
                '}';
    }

    public Integer getLeague() {
        return league;
    }

    public void setLeague(Integer league) {
        this.league = league;
    }

    public Time getStartBusiness() {
        return startBusiness;
    }

    public void setStartBusiness(Time startBusiness) {
        this.startBusiness = startBusiness;
    }

    public Time getEndBusiness() {
        return endBusiness;
    }

    public void setEndBusiness(Time endBusiness) {
        this.endBusiness = endBusiness;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public LocalDateTime getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(LocalDateTime memberTime) {
        this.memberTime = memberTime;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

    public List<SysMerchantImage> getImages() {
        return images;
    }

    public void setImages(List<SysMerchantImage> images) {
        this.images = images;
    }

    public String getBusinssImage() {
        return businssImage;
    }

    public void setBusinssImage(String businssImage) {
        this.businssImage = businssImage;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getInvitatCode() {
        return invitatCode;
    }

    public void setInvitatCode(String invitatCode) {
        this.invitatCode = invitatCode;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }
}
