package com.edaochina.shopping.api.service.trade.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.trade.TradeDetailCalcMapper;
import com.edaochina.shopping.api.service.trade.TradeDetailCalcService;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.trade.TradeDetailCalcListDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailCalc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分润计算表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
@Service
public class TradeDetailCalcServiceImpl extends ServiceImpl<TradeDetailCalcMapper, TradeDetailCalc> implements TradeDetailCalcService {

    @Override
    public PageResult<TradeDetailCalc> list(TradeDetailCalcListDTO dto) {
        QueryWrapper<TradeDetailCalc> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getId())) {
            wrapper.eq("id", dto.getId());
        }
        if (!StringUtils.isEmpty(dto.getOutTradeNo())) {
            wrapper.eq("out_trade_no", dto.getOutTradeNo());
        }
        if (!StringUtils.isEmpty(dto.getMerchantId())) {
            wrapper.eq("merchant_id", dto.getMerchantId());
        }
        if (!StringUtils.isEmpty(dto.getAgentId())) {
            wrapper.eq("agent_id", dto.getAgentId());
        }
        if (dto.getStartTime() != null) {
            wrapper.ge("create_time", dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            wrapper.le("create_time", dto.getEndTime());
        }
        wrapper.orderByDesc("create_time");
        Pages pages = dto.getPages();
        IPage<TradeDetailCalc> page = this.page(new Page<>(pages.getPageIndex(), pages.getPageSize()), wrapper);
        List<TradeDetailCalc> list = null;
        try {
            list = BeanUtil.listBeanToList(page.getRecords(), TradeDetailCalc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pages.setTotal((int) page.getTotal());
        return PageUtil.getPageResult(list, pages);
    }
}
