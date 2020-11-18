package com.edaochina.shopping.api.dao.trade;

import com.edaochina.shopping.domain.dto.trade.SysChainGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailChainProfit;
import com.edaochina.shopping.domain.vo.trade.SysChainProfitVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应链交易分润明细(TradeDetailChainProfit)表数据库访问层
 *
 * @author makejava
 * @since 2019-11-13 13:48:44
 */
public interface TradeDetailChainProfitMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TradeDetailChainProfit queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TradeDetailChainProfit> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tradeDetailChainProfit 实例对象
     * @return 对象列表
     */
    List<TradeDetailChainProfit> queryAll(TradeDetailChainProfit tradeDetailChainProfit);

    /**
     * 新增数据
     *
     * @param tradeDetailChainProfit 实例对象
     * @return 影响行数
     */
    int insert(TradeDetailChainProfit tradeDetailChainProfit);

    /**
     * 修改数据
     *
     * @param tradeDetailChainProfit 实例对象
     * @return 影响行数
     */
    int update(TradeDetailChainProfit tradeDetailChainProfit);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<SysChainProfitVo> getChainProfitList(SysChainGoodsProfitDTO dto);
}