package com.edaochina.shopping.api.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.order.QuerySysWriteOffOrderDTO;
import com.edaochina.shopping.domain.dto.order.SysOrderDto;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.vo.order.ExportSysOrderVO;
import com.edaochina.shopping.domain.vo.order.SysOrderVO;
import com.edaochina.shopping.domain.vo.trade.SysShareProfitVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface SysOrderService extends IService<OrderMain> {

    boolean updateOrderUserStatusForTask();

    List<ExportSysOrderVO> getExportOrderList(SysOrderDto exportSysOrderDTO);

    Integer getExportOrderCount(SysOrderDto sysOrderDto);

    List<OrderMain> queryHasNotCalcOrder();

    List<SysOrderVO> sysQueryOrders(SysOrderDto querySysOrderDTO);

    List<OrderMain> getWriteOffList(QuerySysWriteOffOrderDTO querySysOrderDTO);

    List<SysShareProfitVo> getShareOrder(SysShareGoodsProfitDTO dto);
}
