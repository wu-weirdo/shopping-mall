package com.edaochina.shopping.api.service.goods;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.goods.GoodsImgs;
import com.edaochina.shopping.domain.vo.goods.GoodsImgsVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface GoodsImgsService extends IService<GoodsImgs> {

    List<GoodsImgsVO> getGoodsImgsList(String goodsId);

    boolean saveBatch(List<String> images, String goodsId);

    boolean removeByGoodsId(String goodsId);
}
