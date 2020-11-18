package com.edaochina.shopping.api.service.goods;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.goods.SupplyChainCopyDTO;
import com.edaochina.shopping.domain.dto.goods.SupplyChainInsertDTO;
import com.edaochina.shopping.domain.entity.goods.SupplyChain;

/**
 * (SupplyChain)表服务接口
 *
 * @author wangpenglei
 * @since 2019-11-05 19:00:29
 */
public interface SupplyChainService extends IService<SupplyChain> {

    boolean save(SupplyChainInsertDTO dto);

    boolean copy(SupplyChainCopyDTO ids);

    boolean update(SupplyChainInsertDTO dto);

    String export(SupplyChain supplyChain);
}