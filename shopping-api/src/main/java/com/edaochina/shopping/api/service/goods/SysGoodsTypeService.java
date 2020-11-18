package com.edaochina.shopping.api.service.goods;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.goods.GoodsType;
import com.edaochina.shopping.domain.vo.goods.GoodsTypeVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface SysGoodsTypeService extends IService<GoodsType> {

    List<GoodsTypeVO> getGoodsTypeList();

    List<GoodsTypeVO> getHasGoodsTypeList(String communityId);

    List<GoodsTypeVO> getHasHotGoodsTypeList(String communityId);
}
