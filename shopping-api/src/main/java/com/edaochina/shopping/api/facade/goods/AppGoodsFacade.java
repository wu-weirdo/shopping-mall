package com.edaochina.shopping.api.facade.goods;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.domain.vo.goods.AppCollectGoodVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodDetailVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodsVO;

import java.util.List;

public interface AppGoodsFacade {

    PageResult<AppGoodsVO> getGoodsList(QueryAppGoodsDTO queryAppGoodsDTO);

    boolean hasCache(boolean cache);

    PageResult getMemberGoodsList(QueryAppGoodsDTO queryAppGoodsDTO);

    List<AppGoodsVO> getShopGoodsList(String shopId);

    AppGoodDetailVO getGoodsDetail(QueryAppGoodsDTO queryAppGoodsDTO);

    PageResult<AppCollectGoodVO> getCollectGoodList(QueryAppGoodsDTO queryAppGoodsDTO);
}
