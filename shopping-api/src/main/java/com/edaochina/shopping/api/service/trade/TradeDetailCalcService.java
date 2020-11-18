package com.edaochina.shopping.api.service.trade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailCalcListDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailCalc;

/**
 * <p>
 * 分润计算表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
public interface TradeDetailCalcService extends IService<TradeDetailCalc> {

    /**
     * 查询分润计算明细列表（订单维度）
     *
     * @param dto
     * @return
     */
    PageResult<TradeDetailCalc> list(TradeDetailCalcListDTO dto);
}
