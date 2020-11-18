package com.edaochina.shopping.api.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.goods.GoodsSubclass;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface GoodsSubclassMapper extends BaseMapper<GoodsSubclass> {

    List<GoodsSubclass> getAllGoodsClassList(String communityId);
}
