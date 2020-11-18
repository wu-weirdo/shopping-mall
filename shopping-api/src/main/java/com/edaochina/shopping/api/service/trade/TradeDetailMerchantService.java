package com.edaochina.shopping.api.service.trade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.trade.TradeDetailMerchantDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailMerchant;
import com.edaochina.shopping.domain.vo.trade.TradeDetailMerchantVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
public interface TradeDetailMerchantService extends IService<TradeDetailMerchant> {

    TradeDetailMerchantVO dataStatis(TradeDetailMerchantDTO dto);
}
