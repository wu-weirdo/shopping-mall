package com.edaochina.shopping.domain.entity.goods;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private String id;

    /**
     * 商家id
     */
    private String shopId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品详情
     */
    private String goodsDetail;

    /**
     * 商品种类
     */
    private String goodsTypeId;

    /**
     * 商品种类名称
     */
    private String goodsTypeName;

    /**
     * 商品子类
     */
    private String goodsSubclassId;

    /**
     * 商品子类名称
     */
    private String goodsSubclassName;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品门市价
     */
    private BigDecimal goodsRetailPrice;

    /**
     * 商品成本价
     */
    private BigDecimal goodsCostPrice;


    /**
     * 商品剩余数量
     */
    private Integer goodsSurplusNum;

    /**
     * 商品规格
     */
    private String goodsSpec;

    /**
     * 是否审批通过（10未审批，20不通过，30通过）
     */
    private Integer approvalFlag;

    /**
     * 拒绝理由
     */
    private String reason;

    /**
     * 是否限购（10不限购，20限购）
     */
    private Integer limitBuyFlag;

    /**
     * 限购数量
     */
    private Integer limitBuyNum;

    /**
     * 使用倒计时（10自定义，20永久，30三个月，40一个月，50：7天）
     */
    private Integer useCountDown;

    /**
     * 使用自定义时间（单位：小时）
     */
    private BigDecimal useCountDownTime;

    /**
     * 拼单次数
     */
    private Integer orderNum;

    /**
     * 是否删除（10否，20是）
     */
    private Integer deleteFlag;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 更新人id
     */
    private String updateId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 乐观锁版本号
     */
    private Integer version;

    /**
     * 商品最后有效期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastValidTime;

    /**
     * 置顶状态
     */
    private Integer stickStatus;

    /**
     * 置顶权重
     */
    private Integer stickWeight;

    /**
     * 商品的小视频地址
     */
    private String goodsViewUrl;

    /**
     * 首页展示的图片地址
     */
    private String firstShowImg;

    /**
     * 是否参与拼团(0:不参与，1：参与)
     */
    private Integer collectFlag;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime putawayTime;

    /**
     * 上架状态
     */
    private Integer putawayStatus;


    private Integer goodsSales;

    /**
     * 核销时间段开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffStartTime;

    /**
     * 核销时间段结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffEndTime;

    /**
     * 是否爆品
     */
    private Boolean hot;

    /**
     * 是否推广(默认否)
     */
    private Integer promotion;

    /**
     * 推广费率(默认0.05)
     */
    private BigDecimal promotionCosts;

    /**
     * 是否是供应链商品
     */
    private Integer supplyChain;

    /**
     * 供应链商品Id
     */
    private String supplyChainId;


    private String qrCode;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
