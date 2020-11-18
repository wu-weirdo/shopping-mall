package com.edaochina.shopping.api.facade.goods.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.facade.goods.SysGoodsTypeFacade;
import com.edaochina.shopping.api.service.goods.SysGoodsSubclassService;
import com.edaochina.shopping.api.service.goods.SysGoodsTypeService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.goods.*;
import com.edaochina.shopping.domain.entity.goods.GoodsSubclass;
import com.edaochina.shopping.domain.entity.goods.GoodsType;
import com.edaochina.shopping.domain.vo.goods.GoodsSubclassVO;
import com.edaochina.shopping.domain.vo.goods.GoodsTypeDetailVO;
import com.edaochina.shopping.domain.vo.goods.GoodsTypeVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author User
 */
@Service("sysGoodsTypeFacadeImpl")
public class SysGoodsTypeFacadeImpl implements SysGoodsTypeFacade {

    private final SysGoodsTypeService goodsTypeService;

    private final SysGoodsSubclassService sysGoodsSubclassService;

    public SysGoodsTypeFacadeImpl(SysGoodsTypeService goodsTypeService, SysGoodsSubclassService sysGoodsSubclassService) {
        this.goodsTypeService = goodsTypeService;
        this.sysGoodsSubclassService = sysGoodsSubclassService;
    }

    /**
     * 添加商品分类
     *
     * @param goodsTypeDTO 查询参数
     * @return 商品分类
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertGoodsType(GoodsTypeDTO goodsTypeDTO) {
        LocalDateTime dateTime = LocalDateTime.now();

        GoodsType goodsType = new GoodsType();
        String goodsTypeId = IdUtils.nextId();
        BeanUtils.copyProperties(goodsTypeDTO, goodsType);
        goodsType.setId(goodsTypeId);
        goodsType.setCreateTime(dateTime);
        boolean save = goodsTypeService.save(goodsType);

        if (save) {
            saveBatchGoodsSubclass(goodsTypeDTO, goodsTypeId);
        }

        return true;
    }

    /**
     * 修改商品分类
     *
     * @param goodsTypeDTO 查询参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGoodsType(GoodsTypeDTO goodsTypeDTO) {
        //checkGoodsTypeHasUse(goodsTypeDTO.getId());
        GoodsType goodsType = new GoodsType();
        BeanUtils.copyProperties(goodsTypeDTO, goodsType);
        boolean update = goodsTypeService.updateById(goodsType);
        if (update) {
            deleteGoodsSubclassByTypeId(goodsTypeDTO);
            saveBatchGoodsSubclass(goodsTypeDTO, goodsTypeDTO.getId());
        }
        return true;
    }

    /**
     * 检查商品类型是否被使用
     *
     * @param id 查询参数
     */
    private void checkGoodsTypeHasUse(String id) {

    }

    /**
     * 删除商品类型
     *
     * @param deleteGoodsTypeDTO 查询参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteGoodsTypes(DeleteGoodsTypeDTO deleteGoodsTypeDTO) {
        if (StringUtils.isEmpty(deleteGoodsTypeDTO.getIds())) {
            throw new YidaoException(ReturnData.GOODS_TYPE_PARAM_EMPTY.getCode(), ReturnData.GOODS_TYPE_PARAM_EMPTY.getMsg());
        }
        String[] idArr = deleteGoodsTypeDTO.getIds().split(",");
        for (String id : idArr) {
            if (StringUtils.isNotEmpty(id)) {
                checkGoodsTypeHasUse(id);
                // 删除商品种类
                GoodsType goodsType = new GoodsType();
                goodsType.setId(id);
                goodsTypeService.removeById(goodsType);
                // 删除商品子类
                GoodsTypeDTO goodsTypeDTO = new GoodsTypeDTO();
                goodsTypeDTO.setId(id);
                deleteGoodsSubclassByTypeId(goodsTypeDTO);
            }
        }

        return true;
    }

    /**
     * 获取商品分类
     *
     * @param queryGoodsTypeDTO 查询参数
     * @return 商品分类
     */
    @Override
    public PageResult getGoodsTypeList(QueryGoodsTypeDTO queryGoodsTypeDTO) {
        QueryWrapper<GoodsType> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(queryGoodsTypeDTO.getTypeName())) {
            queryWrapper.like("type_name", queryGoodsTypeDTO.getTypeName());
        }

        if (queryGoodsTypeDTO.getEndTime() != null) {
            queryWrapper.le("create_time", queryGoodsTypeDTO.getEndTime());
        }

        if (queryGoodsTypeDTO.getStartTime() != null) {
            queryWrapper.ge("create_time", queryGoodsTypeDTO.getStartTime());
        }

        queryWrapper.orderByAsc("create_time");
        Pages pages = queryGoodsTypeDTO.getPages();
        IPage<GoodsType> page = goodsTypeService.page(new Page<>(pages.getPageIndex(), pages.getPageSize()), queryWrapper);
        List<GoodsTypeVO> result = new ArrayList<>();
        try {
            result = BeanUtil.listBeanToList(page.getRecords(), GoodsTypeVO.class);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        pages.setTotal((int) page.getTotal());
        return PageUtil.getPageResult(result, pages);
    }

    /**
     * 获取所有的商品分类
     *
     * @return 商品分类
     */
    @Override
    public List<GoodsTypeVO> getAllGoodsTypeList(String communityId) {
        if (StringUtils.isEmpty(communityId)) {
            return goodsTypeService.getGoodsTypeList();
        } else {
            return goodsTypeService.getHasGoodsTypeList(communityId);
        }
    }

    @Override
    public List<GoodsTypeVO> getAllHotGoodsTypeList(String communityId) {
        return goodsTypeService.getHasHotGoodsTypeList(communityId);
    }

    /**
     * 获取商品种类的详情
     *
     * @param queryGoodsSubclassDTO 查询参数
     * @return 商品种类的详情
     */
    @Override
    public GoodsTypeDetailVO getGoodsTypeDetail(QueryGoodsSubclassDTO queryGoodsSubclassDTO) {
        GoodsTypeDetailVO goodsTypeDetailVO = new GoodsTypeDetailVO();
        String typeId = queryGoodsSubclassDTO.getTypeId();
        GoodsType goodsType = goodsTypeService.getById(typeId);
        BeanUtils.copyProperties(goodsType, goodsTypeDetailVO);
        GoodsTypeVO goodsTypeVO = new GoodsTypeVO();
        goodsTypeVO.setId(typeId);
        List<GoodsSubclassVO> goodsSubclassVOS = getGoodsSubclassVOS(goodsTypeVO);
        goodsTypeDetailVO.setGoodsSubclassVOS(goodsSubclassVOS);
        return goodsTypeDetailVO;
    }

    /**
     * 获取商品子类列表
     *
     * @param goodsTypeVO 查询参数
     * @return 商品子类列表
     */
    private List<GoodsSubclassVO> getGoodsSubclassVOS(GoodsTypeVO goodsTypeVO) {
        List<GoodsSubclassVO> goodsSubclassVOList = new ArrayList<>();
        if (goodsTypeVO != null && StringUtils.isNotEmpty(goodsTypeVO.getId())) {
            QueryGoodsSubclassDTO queryGoodsSubclassDTO = new QueryGoodsSubclassDTO();
            queryGoodsSubclassDTO.setTypeId(goodsTypeVO.getId());
            goodsSubclassVOList = sysGoodsSubclassService.getGoodsSubclassList(queryGoodsSubclassDTO);
        }
        return goodsSubclassVOList;
    }

    /**
     * 通过商品种类id删除商品子类
     *
     * @param goodsTypeDTO 查询参数
     */
    private void deleteGoodsSubclassByTypeId(GoodsTypeDTO goodsTypeDTO) {

        if (StringUtils.isNotEmpty(goodsTypeDTO.getId())) {
            QueryWrapper<GoodsSubclass> goodsSubclassQueryWrapper = new QueryWrapper<>();
            goodsSubclassQueryWrapper.eq("type_id", goodsTypeDTO.getId());
            sysGoodsSubclassService.remove(goodsSubclassQueryWrapper);
        }
    }

    /**
     * 批量添加商品子类
     *
     * @param goodsTypeDTO 查询参数
     * @param goodsId      商品id
     */
    private void saveBatchGoodsSubclass(GoodsTypeDTO goodsTypeDTO, String goodsId) {
        List<GoodsSubclass> goodsSubclasses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(goodsTypeDTO.getGoodsSubclassDTOS())) {
            for (int i = 0; i < goodsTypeDTO.getGoodsSubclassDTOS().size(); i++) {
                GoodsSubclass goodsSubclass = new GoodsSubclass();
                GoodsSubclassDTO goodsSubclassDTO = goodsTypeDTO.getGoodsSubclassDTOS().get(i);
                BeanUtils.copyProperties(goodsSubclassDTO, goodsSubclass);
                goodsSubclass.setId(IdUtils.nextId());
                goodsSubclass.setTypeId(goodsId);
                goodsSubclass.setSort(i);
                goodsSubclasses.add(goodsSubclass);
            }
        }
        sysGoodsSubclassService.saveBatch(goodsSubclasses);
    }

}
