package com.edaochina.shopping.api.service.goods.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.goods.GoodsImgsMapper;
import com.edaochina.shopping.api.service.goods.GoodsImgsService;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.entity.goods.GoodsImgs;
import com.edaochina.shopping.domain.vo.goods.GoodsImgsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Service
public class GoodsImgsServiceImpl extends ServiceImpl<GoodsImgsMapper, GoodsImgs> implements GoodsImgsService {

    @Resource
    private GoodsImgsMapper goodsImgsMapper;

    /**
     * 通过商品id来获取该商品的所有图片
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsImgsVO> getGoodsImgsList(String goodsId) {

        QueryWrapper<GoodsImgs> goodsImgsQueryWrapper = new QueryWrapper<>();
        goodsImgsQueryWrapper.eq("goods_id", goodsId);
        goodsImgsQueryWrapper.orderByAsc("sort");
        List<GoodsImgs> goodsImgs = goodsImgsMapper.selectList(goodsImgsQueryWrapper);
        try {
            return BeanUtil.listBeanToList(goodsImgs, GoodsImgsVO.class);
        } catch (Exception e) {
            LoggerUtils.error(BeanUtil.class, e.getMessage());
        }

        return null;
    }

    @Override
    public boolean saveBatch(List<String> images, String goodsId) {
        if (images == null) {
            return false;
        }
        List<GoodsImgs> goodsImgsList = IntStream.range(0, images.size()).mapToObj(i -> {
            String imgId = IdUtils.nextId();
            GoodsImgs goodsImg = new GoodsImgs();
            goodsImg.setId(imgId);
            goodsImg.setGoodsId(goodsId);
            goodsImg.setImgUrl(images.get(i));
            goodsImg.setSort(i);
            return goodsImg;
        }).collect(Collectors.toList());
        return this.saveBatch(goodsImgsList);
    }

    @Override
    public boolean removeByGoodsId(String goodsId) {
        QueryWrapper<GoodsImgs> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsImgs::getGoodsId, goodsId);
        return this.remove(queryWrapper);
    }


}
