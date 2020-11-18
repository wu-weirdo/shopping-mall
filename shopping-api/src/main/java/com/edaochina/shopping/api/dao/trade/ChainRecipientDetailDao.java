package com.edaochina.shopping.api.dao.trade;

import com.edaochina.shopping.domain.dto.trade.ChainRecipientDto;
import com.edaochina.shopping.domain.entity.trade.ChainRecipientDetail;
import com.edaochina.shopping.domain.vo.trade.ChainRecipientVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 供应链发货管理(ChainRecipientDetail)表数据库访问层
 *
 * @author makejava
 * @since 2019-11-13 15:05:07
 */
public interface ChainRecipientDetailDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChainRecipientDetail queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChainRecipientDetail> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param chainRecipientDetail 实例对象
     * @return 对象列表
     */
    List<ChainRecipientDetail> queryAll(ChainRecipientDetail chainRecipientDetail);

    /**
     * 新增数据
     *
     * @param chainRecipientDetail 实例对象
     * @return 影响行数
     */
    int insert(ChainRecipientDetail chainRecipientDetail);

    /**
     * 修改数据
     *
     * @param chainRecipientDetail 实例对象
     * @return 影响行数
     */
    int update(ChainRecipientDetail chainRecipientDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<ChainRecipientVo> getChainRecipientList(ChainRecipientDto dto);

    /**
     * 更新可申请发货状态
     *
     * @param goodsId
     * @param i
     * @return
     */
    @Update("update chain_recipient_detail set can_apply = #{canApply} where goods_id = #{goodsId}")
    int updateCanApplyStatus(@Param("goodsId") String goodsId, @Param("canApply") int i);
}