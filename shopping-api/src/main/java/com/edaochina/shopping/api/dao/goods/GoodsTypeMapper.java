package com.edaochina.shopping.api.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.goods.GoodsType;
import com.edaochina.shopping.domain.vo.goods.GoodsTypeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Repository
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {

    List<GoodsTypeVO> getHasGoodsTypeList(String communityId);

    List<GoodsTypeVO> getHasHotGoodsTypeList(String communityId);


}
