package com.edaochina.shopping.api.service.goods;

import com.edaochina.shopping.domain.entity.goods.GoodsCollectInfo;

/**
 * @author jintian
 * @date 2019/6/19 17:54
 */
public interface GoodsCollectInfoService {

    GoodsCollectInfo getCollectInfoByGoodsId(String goodsId);

    /**
     * 立即拼团成功
     *
     * @param collectInfo 拼团信息
     * @return 修改数量
     */
    int ripening(GoodsCollectInfo collectInfo);

    int removeCollectByGoodsId(String id);

    int addCollect(GoodsCollectInfo collectInfo);

    int addBuyNum(String goodsId, Integer goodsNum);
}
