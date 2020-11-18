package com.edaochina.shopping.api.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.order.AppMerchantRefundOrderDTO;
import com.edaochina.shopping.domain.dto.sys.SysOrderRefundDTO;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.vo.order.AppMerchantRefundOrderVO;
import com.edaochina.shopping.domain.vo.order.AppRefundApplyVO;
import com.edaochina.shopping.domain.vo.sys.ExportSysOrderRefundVo;
import com.edaochina.shopping.domain.vo.sys.SysOrderRefundVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 退款申请表 Mapper 接口
 * </p>
 *
 * @since 2019-06-17
 */
@Repository
public interface PayRefundApplyMapper extends BaseMapper<PayRefundApply> {

    PayRefundApply selectById(@Param("id") Integer id);

    int insertRefundApply(PayRefundApply payRefundApply);

    AppRefundApplyVO refundApplyInfo(@Param("orderId") String orderId);

    /**
     * 查询退款订单
     *
     * @param dto 查询参数
     * @return 退款订单列表
     */
    List<SysOrderRefundVo> list(SysOrderRefundDTO dto);

    /**
     * 查询导出退款订单
     *
     * @param dto 查询参数
     * @return 退款订单列表
     */
    List<ExportSysOrderRefundVo> exportList(SysOrderRefundDTO dto);

    /**
     * 查询总数
     *
     * @param dto 查询参数
     * @return 总数
     */
    int orderRefundCount(SysOrderRefundDTO dto);

    AppRefundApplyVO refundApplyInfo(@Param("id") Integer id);

    int updateApplyStatus(@Param("id") Integer id, @Param("applyStatus") Integer applyStatus);

    List<AppMerchantRefundOrderVO> merchantRefundOrders(AppMerchantRefundOrderDTO dto);

    int merchantRefundOrderCount(AppMerchantRefundOrderDTO dto);

    int refuseRefund(@Param("id") Integer id, @Param("refuseReason") String refuseReason);

    int agreeRefund(@Param("id") Integer id);

    /**
     * 修改退款订单
     *
     * @param dto 修改参数
     * @return 影响记录数
     */
    int sysUpdate(SysOrderRefundDTO dto);

    /**
     * 查询等待打款的退款
     *
     * @return
     */
    List<PayRefundApply> queryWaitRemit();

    int updateFefundStatus(@Param("id") Integer id, @Param("status") int status);

    void updateHanlderStatus(@Param("id") Integer id, @Param("status") int status);

    List<PayRefundApply> queryByMerchantAndHandlerStatus(@Param("merchantHandlerStatus") Integer merchantHandlerStatus, @Param("refundStatus") Integer refundStatus, @Param("pageSize") Integer pageSize);

    void updateMerchantUnHandlerToUnConnect(@Param("time") Date time);

    PayRefundApply selectByOrderIdAndInRefundStatus(@Param("orderId") String orderId, @Param("status") List<Integer> status);

    AppRefundApplyVO queryRefundApplyInfoByOrderId(String orderId);

    int updateRemitStatus(@Param("refundApplyId") Integer refundApplyId, @Param("remitStatus") int remitStatus);

    int updateCollectUserStatus(int id);

    int updateCollectMerchantStatus(int id);
}