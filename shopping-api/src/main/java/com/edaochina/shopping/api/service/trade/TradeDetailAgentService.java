package com.edaochina.shopping.api.service.trade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailAgent;
import com.edaochina.shopping.domain.vo.trade.TradeDetailAgentVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
public interface TradeDetailAgentService extends IService<TradeDetailAgent> {

    TradeDetailAgentVO dataStatis(TradeDetailAgentDTO dto);
}
