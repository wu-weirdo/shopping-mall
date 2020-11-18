package com.edaochina.shopping.api.facade.goods;

import com.edaochina.shopping.api.facade.order.QueryShopIdsByCommunity;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.ApprovalFlagConstants;
import com.edaochina.shopping.domain.dto.goods.*;
import com.edaochina.shopping.domain.vo.goods.SysGoodsDetailVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

public interface SysGoodsFacade extends QueryShopIdsByCommunity {

    boolean updateGoods(GoodsDTO goodsDTO);

    boolean insertGoods(GoodsDTO goodsDTO);

    boolean deleteGoods(DeleteGoodsDTO deleteGoodsDTO);

    PageResult getGoodsList(QueryGoodsDTO queryGoodsDTO) throws InstantiationException, IllegalAccessException;

    SysGoodsDetailVO getGoodsDetail(QuerySysGoodsDTO querySysGoodsDTO);

    boolean approvalGoods(ApprovalGoodsDTO approvalGoodsDTO);

    void collectOverTimeTask();

    @Transactional(rollbackFor = Exception.class)
    boolean ripening(String goodsId);

    boolean batchUpdateGoods(BatchGoodsDTO batchGoodsDTO);

    default void checkGoodsDTO(GoodsDTO goodsDTO) {
        if (goodsDTO == null) {
            throw new YidaoException(ReturnData.GOODS_PARAM_EMPTY.getCode(), ReturnData.GOODS_PARAM_EMPTY.getMsg());
        }
        // 普通商品不需要审核
        if (goodsDTO.getHot() != null && !goodsDTO.getHot() && goodsDTO.getCollectFlag() != null && goodsDTO.getCollectFlag() != 1) {
            goodsDTO.setApprovalFlag(ApprovalFlagConstants.ACCEPT);
        } else if (goodsDTO.getHot() != null && goodsDTO.getHot()) {
            goodsDTO.setApprovalFlag(ApprovalFlagConstants.NO_APPROVAL);
        }

        if (goodsDTO.getGoodsDetail() != null && goodsDTO.getGoodsDetail().length() > 500) {
            throw new YidaoException("商品详情超出字数限制！");
        }
        if (this.checkGoodsName(goodsDTO.getGoodsName())) {
            throw new YidaoException("商品名存在违规词！");
        }
    }

    default boolean checkGoodsName(String goodsName) {
        return Arrays.asList("秒杀", "最", "底价", "第一", "顶级", "冠军", "首家", "抄底", "之冠", "之王", "极致",
                "顶尖", "领导品牌", "史无前例", "极品", "世界级", "金牌", "广告法不让说", "绝对", "唯一", "肿瘤", "肝炎",
                "囊肿", "关节炎", "类风湿", "假一").parallelStream().anyMatch(goodsName::contains);
    }

    boolean batchApprovalGoods(SysBatchApprovalGoodsDTO dto);
}
