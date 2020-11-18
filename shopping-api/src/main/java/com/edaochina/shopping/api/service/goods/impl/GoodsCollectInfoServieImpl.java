package com.edaochina.shopping.api.service.goods.impl;

import com.edaochina.shopping.api.dao.goods.GoodsCollectInfoMapper;
import com.edaochina.shopping.api.service.goods.GoodsCollectInfoService;
import com.edaochina.shopping.domain.entity.goods.GoodsCollectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jintian
 * @date 2019/6/19 17:54
 */
@Service
public class GoodsCollectInfoServieImpl implements GoodsCollectInfoService {

    @Autowired
    private GoodsCollectInfoMapper goodsCollectInfoMapper;


    @Override
    public GoodsCollectInfo getCollectInfoByGoodsId(String goodsId) {
        return goodsCollectInfoMapper.getCollectInfoByGoodsId(goodsId);
    }

    @Override
    public int ripening(GoodsCollectInfo collectInfo) {
        return goodsCollectInfoMapper.ripening(collectInfo);
    }

    @Override
    public int removeCollectByGoodsId(String goodsId) {
        return goodsCollectInfoMapper.removeCollectByGoodsId(goodsId);
    }

    @Override
    public int addCollect(GoodsCollectInfo collectInfo) {
        collectInfo.setBuyNum(0);
        return goodsCollectInfoMapper.addCollect(collectInfo);
    }

    @Override
    public int addBuyNum(String goodsId, Integer goodsNum) {
        return goodsCollectInfoMapper.addBuyNum(goodsId, goodsNum);
    }
}
