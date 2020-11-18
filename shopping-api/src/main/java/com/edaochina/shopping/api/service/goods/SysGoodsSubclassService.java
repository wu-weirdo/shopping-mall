package com.edaochina.shopping.api.service.goods;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsSubclassDTO;
import com.edaochina.shopping.domain.entity.goods.GoodsSubclass;
import com.edaochina.shopping.domain.vo.goods.GoodsSubclassVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public interface SysGoodsSubclassService extends IService<GoodsSubclass> {

    List<GoodsSubclassVO> getGoodsSubclassList(QueryGoodsSubclassDTO queryGoodsSubclassDTO);

    List<GoodsSubclass> getAllGoodsClassList(String communityId);
}
