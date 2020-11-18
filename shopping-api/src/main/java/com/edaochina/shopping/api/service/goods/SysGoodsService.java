package com.edaochina.shopping.api.service.goods;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.goods.ApprovalGoodsDTO;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.goods.GoodsCollectInfo;
import com.edaochina.shopping.domain.vo.goods.SysGoodsVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface SysGoodsService extends IService<Goods> {

    int querySticNumByCommuntyId(String communtyId);

    void updateGoodsToCollectSuccess(GoodsCollectInfo goodsCollectInfo);

    void updateGoodsToCollectFail(GoodsCollectInfo goodsCollectInfo);

    int queryMerchantPutawayNum(String id);

    List<SysGoodsVO> getSysGoodsList(QueryGoodsDTO queryGoodsDTO);

    void approvalGoods(String id, ApprovalGoodsDTO dto);

    void setQrCode(String goodsId);
}
