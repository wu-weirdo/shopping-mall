package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.sys.SysOrderRefundDTO;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.vo.sys.SysOrderRefundVo;

import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @since : 2019/6/18 14:32
 */
public interface SysPayRefundApplyService {

    /**
     * 查询退款订单
     *
     * @param dto   查询参数
     * @param pages 分页参数
     * @return 退款订单列表
     */
    PageResult<SysOrderRefundVo> list(SysOrderRefundDTO dto, Pages pages);

    /**
     * 根据id查询退款订单
     *
     * @param id 退款订单id
     * @return 退款订单vo
     */
    SysOrderRefundVo selectById(int id);

    /**
     * 修改退款订单
     *
     * @param dto 修改参数
     * @return 影响记录数
     */
    int update(SysOrderRefundDTO dto);

    /**
     * excel导出
     *
     * @param dto 导出参数
     * @return excel下载链接
     */
    String export(SysOrderRefundDTO dto);

    /**
     * 根据订单id和退款订单处理状态集合查询
     *
     * @param orderId 订单id
     * @param status  退款订单处理状态集合
     * @return 退款订单
     */
    PayRefundApply selectByOrderIdAndInRefundStatus(String orderId, List<Integer> status);

    int cancelApply(Integer id);

    int collectUser(int id);

    int collectMerchant(int id);

    /**
     * 订单自动退款
     *
     * @param orderId        要退款的订单id
     * @param payRefundApply 覆盖默认参数
     * @return 影响数据库记录数
     */
    int autoRefund(String orderId, PayRefundApply payRefundApply);
}
