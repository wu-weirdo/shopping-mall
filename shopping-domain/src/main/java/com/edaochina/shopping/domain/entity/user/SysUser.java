package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.edaochina.shopping.domain.constants.MemberTypeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 普通用户表  by zzk
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 0 未知 1 男 2 女
     */
    private Integer gender;

    /**
     * 绑定小区
     */
    private String bindCommunity;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 来源商家
     */
    private String origin;

    /**
     * 1 正常 2禁用
     */
    private Integer status;

    /**
     * 会员类型(0非会员，1会员,2会员费已支付未收到支付结果）
     */
    private Integer memberType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime memberTime;

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

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String countyCode;

    private String countyName;

    private String userCityAddress;

    /**
     * 是否删除(1.未删除 2.已删除)
     */
    private Integer delFlg;

    /**
     * 用户微信session(有效期30天)
     */
    private String sessionKey;

    /**
     * 手机是否授权更新(0.未更新，1已更新)
     */
    private Integer phoneUpdateFlag;

    /**
     * 微信端的uid
     */
    private String wxUid;

    private String tencentOpenId;

    /**
     * 已优惠金额
     */
    private BigDecimal discountMoney;


    private Integer buyNum;

    public void bindCommunity(Community community) {
        this.setBindCommunity(community.getId());
        this.setCityCode(community.getCityId());
        this.setCityName(community.getCityName());
        this.setCountyCode(community.getCountyId());
        this.setCountyName(community.getCountyName());
        this.setProvinceCode(community.getProvinceId());
        this.setProvinceName(community.getProvinceName());
        this.setUserCityAddress(community.getProvinceName() + community.getCityName() + community.getCountyName());
    }

    public boolean isNotMember() {
        return this.memberType == null || this.memberType.equals(MemberTypeStatus.IS_NOT_MEMBER);
    }

    /**
     * 法律效益用户id
     */
    private String lawConsumerId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
