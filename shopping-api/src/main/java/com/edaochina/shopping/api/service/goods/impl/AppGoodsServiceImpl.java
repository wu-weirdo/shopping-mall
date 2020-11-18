package com.edaochina.shopping.api.service.goods.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.goods.GoodsMapper;
import com.edaochina.shopping.api.service.goods.AppGoodsService;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.vo.goods.AppCollectGoodVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodDetailVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Service
public class AppGoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements AppGoodsService {

    @Resource
    GoodsMapper goodsMapper;

    @Override
    public List<AppGoodsVO> getGoodsList(QueryAppGoodsDTO queryAppGoodsDTO) {
        return goodsMapper.getGoodsList(queryAppGoodsDTO);
    }

    @Override
    public List<AppGoodsVO> getMemberGoodsList(QueryAppGoodsDTO queryAppGoodsDTO) {
        return goodsMapper.getMemberGoodsList(queryAppGoodsDTO);
    }

    @Override
    public List<AppGoodsVO> getShopGoodsList(String shopId) {
        return goodsMapper.getShopGoodsList(shopId);
    }


    @Override
    public AppGoodDetailVO queryByGoodsIdAndUserId(String goodsId, String userId, Double longitude, Double latitude) {
        return goodsMapper.queryByGoodsIdAndUserId(goodsId, userId, longitude, latitude);
    }

    /**
     * 更新商品剩余数量和已购数量
     *
     * @param goodsId  商品id
     * @param num      商品剩余数增加数量
     * @param orderNum 已购订单数增加数量
     * @return
     */
    @Override
    public int updateGoodsSurplusNumAndOrderNum(String goodsId, int num, int orderNum) {
        return goodsMapper.updateGoodsSurplusNumAndOrderNum(goodsId, num, orderNum);
    }

    /**
     * 查询拼团商品列表
     *
     * @param queryAppGoodsDTO
     * @return
     */
    @Override
    public List<AppCollectGoodVO> getCollectGoodsList(QueryAppGoodsDTO queryAppGoodsDTO) {
        return goodsMapper.getCollectGoodsList(queryAppGoodsDTO);
    }
}
