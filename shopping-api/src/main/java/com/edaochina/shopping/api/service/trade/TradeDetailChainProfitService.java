package com.edaochina.shopping.api.service.trade;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.trade.SysChainGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailChainProfit;
import com.edaochina.shopping.domain.vo.trade.SysChainProfitVo;

import java.util.List;

/**
 * 供应链交易分润明细(TradeDetailChainProfit)表服务接口
 *
 * @author makejava
 * @since 2019-11-13 13:48:45
 */
public interface TradeDetailChainProfitService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TradeDetailChainProfit queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TradeDetailChainProfit> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tradeDetailChainProfit 实例对象
     * @return 实例对象
     */
    TradeDetailChainProfit insert(TradeDetailChainProfit tradeDetailChainProfit);

    /**
     * 修改数据
     *
     * @param tradeDetailChainProfit 实例对象
     * @return 实例对象
     */
    TradeDetailChainProfit update(TradeDetailChainProfit tradeDetailChainProfit);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PageResult<SysChainProfitVo> getChainProfitList(SysChainGoodsProfitDTO dto);

    PageResult<SysChainProfitVo> getChainProfitListApp(SysChainGoodsProfitDTO dto);
}