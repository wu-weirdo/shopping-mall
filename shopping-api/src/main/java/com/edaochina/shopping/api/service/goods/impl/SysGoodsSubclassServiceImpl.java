package com.edaochina.shopping.api.service.goods.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.goods.GoodsSubclassMapper;
import com.edaochina.shopping.api.service.goods.SysGoodsSubclassService;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.domain.dto.goods.QueryGoodsSubclassDTO;
import com.edaochina.shopping.domain.entity.goods.GoodsSubclass;
import com.edaochina.shopping.domain.vo.goods.GoodsSubclassVO;
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
public class SysGoodsSubclassServiceImpl extends ServiceImpl<GoodsSubclassMapper, GoodsSubclass> implements SysGoodsSubclassService {

    @Resource
    private GoodsSubclassMapper goodsSubclassMapper;

    /**
     * 通过typeId来获取商品子类
     *
     * @param queryGoodsSubclassDTO 查询参数
     * @return 商品子类
     */
    @Override
    public List<GoodsSubclassVO> getGoodsSubclassList(QueryGoodsSubclassDTO queryGoodsSubclassDTO) {
        QueryWrapper<GoodsSubclass> goodsSubclassQueryWrapper = new QueryWrapper<>();
        goodsSubclassQueryWrapper.eq("type_id", queryGoodsSubclassDTO.getTypeId());
        List<GoodsSubclass> goodsSubclasses = goodsSubclassMapper.selectList(goodsSubclassQueryWrapper);
        try {
            return BeanUtil.listBeanToList(goodsSubclasses, GoodsSubclassVO.class);
        } catch (Exception e) {
            LoggerUtils.error(BeanUtil.class, e.getMessage());
        }
        return null;
    }

    @Override
    public List<GoodsSubclass> getAllGoodsClassList(String communityId) {
        return goodsSubclassMapper.getAllGoodsClassList(communityId);
    }
}
