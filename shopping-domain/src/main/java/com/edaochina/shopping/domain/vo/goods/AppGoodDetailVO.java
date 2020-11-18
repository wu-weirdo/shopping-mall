package com.edaochina.shopping.domain.vo.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品详情
 */
@ApiModel("小程序商品详情")
@Data
public class AppGoodDetailVO {


    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private String id;

    @ApiModelProperty("商家经度")
    private Double longitude = 0.00;

    @ApiModelProperty("商家维度")
    private Double latitude = 0.00;

    private String shopAddress;

    private String shopPhone;

    /**
     * 商家id
     */
    @ApiModelProperty("商家id")
    private String shopId;

    /**
     * 商品名称
     */
    @ApiModelProperty("商家名称")
    private String goodsName;

    /**
     * 商品详情
     */
    @ApiModelProperty("商品详情")
    private String goodsDetail;

    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    /**
     * 商品门市价
     */
    @ApiModelProperty("商品门市价")
    private BigDecimal goodsRetailPrice;


    /**
     * 商品剩余数量
     */
    @ApiModelProperty("商品剩余数量")
    private Integer goodsSurplusNum;

    /**
     * 商品规格
     */
    @ApiModelProperty("商品规格")
    private String goodsSpec;

    /**
     * 是否限购（10不限购，20限购）
     */
    @ApiModelProperty("是否限购标识10不限购，20限购")
    private Integer limitBuyFlag;

    /**
     * 限购数量
     */
    @ApiModelProperty("限购数量")
    private Integer limitBuyNum;

    /**
     * 商品的小视频地址
     */
    @ApiModelProperty("商品小视频地址")
    private String goodsViewUrl;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("商品图片列表")
    private List<String> imgs;

    @ApiModelProperty("商品提示信息列表")
    private List<SysGoodsTipsVO> sysGoodsTipsVOS;

    @ApiModelProperty("商品已购买数量")
    private Integer buyNum;

    private Integer inShopCartNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastValidTime;

    // 以下为3.1 添加内容(金天)
    /**
     * 是否参与拼团标识（0：不拼团，1：拼团）
     */
    private Integer collectFlag;
    /**
     * 拼团成团人数
     */
    private Integer collectNum;
    /**
     * 拼团已拼团人数
     */
    private Integer collectedNum;
    /**
     * 拼团规则信息
     */
    private String collectInfo;
    /**
     * 拼团有效期截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectLastValidTime;
    /**
     * 已拼团人头像
     */
    private List<String> collectUserImgs;


    private Integer orderNum;

    /**
     * 首页展示的退片
     */
    private String firstShowImg;

    private String qrCode;

    @ApiModelProperty("是否联盟商家")
    private Integer league;

    private String supplyChainId;

    private Integer supplyChain;

    /**
     * 上架状态（0：未上架，1：已上架）
     */
    private Integer putawayStatus;
}
