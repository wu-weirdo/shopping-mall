package com.edaochina.shopping.api.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.vo.goods.AppCollectGoodVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodDetailVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodsVO;
import com.edaochina.shopping.domain.vo.goods.SysGoodsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获取对应小区的商品
     *
     * @param dto
     * @return
     */
    List<AppGoodsVO> getGoodsList(QueryAppGoodsDTO dto);

    List<AppGoodsVO> getMemberGoodsList(QueryAppGoodsDTO dto);

    /**
     * 获取商店的商品
     *
     * @param shopId 商店id
     * @return 商店的商品
     */
    List<AppGoodsVO> getShopGoodsList(@Param("shopId") String shopId);

    Integer querySticNumByCommuntyId(@Param("communtyId") String communtyId);

    AppGoodDetailVO queryByGoodsIdAndUserId(@Param("goodsId") String goodsId, @Param("userId") String userId,
                                            @Param("longitude") Double longitude, @Param("latitude") Double latitude);

    int updateGoodsSurplusNumAndOrderNum(@Param("goodsId") String goodsId, @Param("num") int num, @Param("orderNum") int orderNum);

    List<AppCollectGoodVO> getCollectGoodsList(QueryAppGoodsDTO queryAppGoodsDTO);

    int updateCollectFlag(@Param("goodsId") String goodsId, @Param("collectFlag") int collectFlag);

    int queryMerchantPutawayNum(String merchantId);

    List<SysGoodsVO> getSysGoodsList(QueryGoodsDTO queryGoodsDTO);

    @Update("update goods set putaway_status = #{putawayStatus} where id = #{goodsId}")
    int updatePutawayStatus(@Param("goodsId") String goodsId, @Param("putawayStatus") int i);
}
