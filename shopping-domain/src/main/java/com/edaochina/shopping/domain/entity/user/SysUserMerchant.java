package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;

/**
 * <p>
 * 商户表 by 张志侃
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserMerchant extends Model<SysUserMerchant> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 商家名称
     */
    private String title;

    /**
     * 账号
     */
    private String account;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String identityNo;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 二维码地址
     */
    private String qrCode;

    /**
     * 状态 0:使用中 1:禁用 2:删除
     */
    private Integer status;

    /**
     * 余额
     */
    private BigDecimal balanceMoney;

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

    /**
     * 创建时间 默认当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 数据更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    private String countyId;

    private Integer checkStatus;

    /**
     * 用户openid
     */
    private String openid;
    /**
     * 法律效益用户id
     */
    private String lawConsumerId;

    /**
     * 邀请码
     */
    private String invitatCode;

    /**
     * 所属群社合伙人id
     */
    private String communityUserId;

    private String houseNumber;

    @JsonFormat(pattern = "HH:mm:ss")
    private Time startBusiness;

    @JsonFormat(pattern = "HH:mm:ss")
    private Time endBusiness;

    // 2019.10.10 新加字段
    /**
     * 商户总收入
     */
    private BigDecimal totalIncome;

    /**
     * 是否联盟
     */
    private Integer league;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
