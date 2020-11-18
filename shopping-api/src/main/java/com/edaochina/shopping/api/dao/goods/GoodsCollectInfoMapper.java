package com.edaochina.shopping.api.dao.goods;

import com.edaochina.shopping.domain.entity.goods.GoodsCollectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jintian
 * @date 2019/6/19 17:51
 */
public interface GoodsCollectInfoMapper {

    GoodsCollectInfo getCollectInfoByGoodsId(String goodsId);

    int removeCollectByGoodsId(String goodsId);

    int addCollect(GoodsCollectInfo collectInfo);

    int addBuyNum(@Param("goodsId") String goodsId, @Param("goodsNum") Integer goodsNum);

    List<GoodsCollectInfo> getOverTimeCollects();

    int updateTodel(Integer id);

    int ripening(GoodsCollectInfo collectInfo);

    int updateToEnd(Integer id);
}
