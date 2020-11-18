package com.edaochina.shopping.api.service.trade;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.trade.ChainPayInfo;
import com.edaochina.shopping.domain.dto.trade.ChainPayInfoAffirmDto;
import com.edaochina.shopping.domain.dto.trade.ChainRecipientDto;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.goods.SupplyChain;
import com.edaochina.shopping.domain.entity.trade.ChainRecipientDetail;

import java.util.List;

/**
 * 供应链发货管理(ChainRecipientDetail)表服务接口
 *
 * @author makejava
 * @since 2019-11-13 15:05:07
 */
public interface ChainRecipientDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ChainRecipientDetail queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ChainRecipientDetail> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param chainRecipientDetail 实例对象
     * @return 实例对象
     */
    ChainRecipientDetail insert(ChainRecipientDetail chainRecipientDetail);

    /**
     * 修改数据
     *
     * @param chainRecipientDetail 实例对象
     * @return 实例对象
     */
    ChainRecipientDetail update(ChainRecipientDetail chainRecipientDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    boolean uploadPayInfo(ChainPayInfo chainPayInfo);

    PageResult getChainRecipientList(ChainRecipientDto dto);

    boolean payInfoAffirm(ChainPayInfoAffirmDto dto);

    void addChainRecipientDetailBySupplyChain(SupplyChain supplyChain, Goods goods);
}