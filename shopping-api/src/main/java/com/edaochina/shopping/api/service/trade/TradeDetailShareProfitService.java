package com.edaochina.shopping.api.service.trade;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import com.edaochina.shopping.domain.vo.trade.SysShareProfitVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TradeDetailShareProfitService {

    PageResult<SysShareProfitVo> getShareProfitList(SysShareGoodsProfitDTO dto);

    BigDecimal getShareByRoleAndUserId(Integer roleId, String userId, LocalDateTime beginTime, LocalDateTime endTime);
}