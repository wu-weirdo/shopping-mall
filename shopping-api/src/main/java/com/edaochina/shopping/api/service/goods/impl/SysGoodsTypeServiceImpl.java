package com.edaochina.shopping.api.service.goods.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.goods.GoodsTypeMapper;
import com.edaochina.shopping.api.service.goods.SysGoodsTypeService;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.domain.entity.goods.GoodsType;
import com.edaochina.shopping.domain.vo.goods.GoodsTypeVO;
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
public class SysGoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements SysGoodsTypeService {

    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public List<GoodsTypeVO> getGoodsTypeList() {
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        goodsTypeQueryWrapper.orderByAsc("create_time");
        List<GoodsType> goodsTypes = goodsTypeMapper.selectList(goodsTypeQueryWrapper);
        try {
            return BeanUtil.listBeanToList(goodsTypes, GoodsTypeVO.class);
        } catch (Exception e) {
            LoggerUtils.error(BeanUtil.class, e.getMessage());
        }
        return null;
    }

    @Override
    public List<GoodsTypeVO> getHasGoodsTypeList(String communityId) {
        return goodsTypeMapper.getHasGoodsTypeList(communityId);
    }

    @Override
    public List<GoodsTypeVO> getHasHotGoodsTypeList(String communityId) {
        return goodsTypeMapper.getHasHotGoodsTypeList(communityId);
    }
}
