package com.edaochina.shopping.domain.vo.user;


import com.edaochina.shopping.domain.entity.user.CountyInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 代理商表 by 张志侃
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Data
public class AgentVO implements Serializable {


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
     * 创建时间 默认当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 所在市订单数
     */
    private Integer orderCount;

    /**
     * 总分润金额
     */
    private BigDecimal totalProfit;

    private List<CountyInfo> agentAreaInfos;

    /**
     * 是否付款（10：未付款，20：已付款）
     */
    private int payment;


    private Integer merchantMemberNum;

    private Integer userMemberNum;
}
