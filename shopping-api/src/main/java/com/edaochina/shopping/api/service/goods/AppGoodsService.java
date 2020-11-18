package com.edaochina.shopping.api.service.goods;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.vo.goods.AppCollectGoodVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodDetailVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodsVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface AppGoodsService extends IService<Goods> {

    List<AppGoodsVO> getGoodsList(QueryAppGoodsDTO queryAppGoodsDTO);

    List<AppGoodsVO> getMemberGoodsList(QueryAppGoodsDTO queryAppGoodsDTO);

    List<AppGoodsVO> getShopGoodsList(String shopId);

    AppGoodDetailVO queryByGoodsIdAndUserId(String goodsId, String userId, Double longitude, Double latitude);

    int updateGoodsSurplusNumAndOrderNum(String goodsId, int num, int orderNum);

    List<AppCollectGoodVO> getCollectGoodsList(QueryAppGoodsDTO queryAppGoodsDTO);
}
