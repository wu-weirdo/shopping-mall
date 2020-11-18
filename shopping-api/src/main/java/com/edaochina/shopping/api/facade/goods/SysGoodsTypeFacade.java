package com.edaochina.shopping.api.facade.goods;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.goods.DeleteGoodsTypeDTO;
import com.edaochina.shopping.domain.dto.goods.GoodsTypeDTO;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsSubclassDTO;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsTypeDTO;
import com.edaochina.shopping.domain.vo.goods.GoodsTypeDetailVO;
import com.edaochina.shopping.domain.vo.goods.GoodsTypeVO;

import java.util.List;

public interface SysGoodsTypeFacade {

    boolean insertGoodsType(GoodsTypeDTO goodsTypeDTO);

    boolean updateGoodsType(GoodsTypeDTO goodsTypeDTO);

    boolean deleteGoodsTypes(DeleteGoodsTypeDTO deleteGoodsTypeDTO);

    PageResult getGoodsTypeList(QueryGoodsTypeDTO queryGoodsTypeDTO);

    List<GoodsTypeVO> getAllGoodsTypeList(String communityId);

    List<GoodsTypeVO> getAllHotGoodsTypeList(String communityId);

    GoodsTypeDetailVO getGoodsTypeDetail(QueryGoodsSubclassDTO queryGoodsSubclassDTO);
}
