package com.edaochina.shopping.domain.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * (SupplyChain)表实体类
 *
 * @author wangpenglei
 * @since 2019-11-05 19:00:26
 */
@SuppressWarnings("serial")
public class SupplyChain extends Model<SupplyChain> {
    //主键
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    //联系人姓名
    private String contactName;
    //联系人电话
    private String contactPhone;
    //联系人地址
    private String contactAddress;
    //注意事项
    private String attention;
    //采购价格
    private BigDecimal purchasePrice;
    //商品数量
    private Integer goodsCount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
    //复选次数
    private Integer copyCount;
    //状态
    private Integer state;
    //商品名称
    private String goodsName;
    //商品详情
    private String goodsDetail;
    //商品种类id
    private String goodsTypeId;
    //商品子类id
    private String goodsSubclassId;
    //商品规格
    private String goodsSpec;
    //商品小视屏地址
    private String goodsViewUrl;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品市场价格
     */
    private BigDecimal goodsRetailPrice;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 最低发货量
     */
    private Integer minimumShipment;

    /**
     * 总销量
     */
    private Integer totalSales;

    /**
     * 收款人
     */
    private String chamberlain;

    /**
     * 收款银行
     */
    private String bank;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 负责人地址
     */
    private String address;

    /**
     * 负责人名字
     */
    private String name;

    /**
     * 负责人电话
     */
    private String phone;

    @Override
    public String toString() {
        return "SupplyChain{" +
                "id='" + id + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", attention='" + attention + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", goodsCount=" + goodsCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", copyCount=" + copyCount +
                ", state=" + state +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", goodsTypeId='" + goodsTypeId + '\'' +
                ", goodsSubclassId='" + goodsSubclassId + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", goodsViewUrl='" + goodsViewUrl + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsRetailPrice=" + goodsRetailPrice +
                ", brand='" + brand + '\'' +
                ", minimumShipment=" + minimumShipment +
                ", totalSales=" + totalSales +
                ", chamberlain='" + chamberlain + '\'' +
                ", bank='" + bank + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                "} " + super.toString();
    }

    public String getChamberlain() {
        return chamberlain;
    }

    public void setChamberlain(String chamberlain) {
        this.chamberlain = chamberlain;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsRetailPrice() {
        return goodsRetailPrice;
    }

    public void setGoodsRetailPrice(BigDecimal goodsRetailPrice) {
        this.goodsRetailPrice = goodsRetailPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getMinimumShipment() {
        return minimumShipment;
    }

    public void setMinimumShipment(Integer minimumShipment) {
        this.minimumShipment = minimumShipment;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
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

    public Integer getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(Integer copyCount) {
        this.copyCount = copyCount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsSubclassId() {
        return goodsSubclassId;
    }

    public void setGoodsSubclassId(String goodsSubclassId) {
        this.goodsSubclassId = goodsSubclassId;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsViewUrl() {
        return goodsViewUrl;
    }

    public void setGoodsViewUrl(String goodsViewUrl) {
        this.goodsViewUrl = goodsViewUrl;
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