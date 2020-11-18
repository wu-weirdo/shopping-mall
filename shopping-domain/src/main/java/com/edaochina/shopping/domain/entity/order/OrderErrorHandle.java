package com.edaochina.shopping.domain.entity.order;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderErrorHandle extends Model<OrderErrorHandle> {

    private Integer id;

    /**
     * 异常订单id
     */
    private Integer exceptionOrderId;

    /**
     * 处理人id
     */
    private String handleUserId;

    /**
     * 处理人类型(1代理商，2管理员)
     */
    private Integer handleUserRole;

    /**
     * 处理前状态（10待分享，20待使用，30待评价，40已退款，50已评价，60已过期，70待支付，80付款失败,90异常)
     */
    private Integer handleBeforeStatus;

    /**
     * 处理后（10待分享，20待使用，30待评价，40已退款，50已评价，60已过期，70待支付，80付款失败,90异常)
     */
    private Integer handleAfterStatus;

    /**
     * 备注
     */
    private String remark;


}
