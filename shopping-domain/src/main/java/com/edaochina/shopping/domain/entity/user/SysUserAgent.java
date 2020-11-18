package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 代理商表 by 张志侃
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserAgent extends Model<SysUserAgent> {

    private static final long serialVersionUID = 1L;

    private String id;

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
    @TableField(strategy = FieldStrategy.NOT_EMPTY)
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 代理商姓名
     */
    private String name;

    /**
     * 照片
     */
    private String photo;

    /**
     * 身份证号码
     */
    private String identityNo;


    /**
     * 状态 0:使用中 1:禁用 2:删除
     */
    private Integer status;

    /**
     * 余额
     */
    private BigDecimal balanceMoney;

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

    /**
     * 用户openid
     */
    private String openid;

    /**
     * 是否付款（10：未付款，20：已付款）
     */
    private Integer payment;

    /**
     * 直播间code
     */
    private String liveCode;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
