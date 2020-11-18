package com.edaochina.shopping.api.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.sys.RotationPicMapper;
import com.edaochina.shopping.api.service.sys.RotationPicService;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.dto.sys.RotationPicDTO;
import com.edaochina.shopping.domain.entity.sys.RotationPic;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RotationPicServiceImpl extends ServiceImpl<RotationPicMapper, RotationPic> implements RotationPicService {

    @Resource
    RotationPicMapper mapper;

    /**
     * 查询轮播图列表
     *
     * @return
     */
    @Override
    public List<RotationPic> list() {
        QueryWrapper<RotationPic> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        return mapper.selectList(wrapper);
    }

    /**
     * 删除轮播图
     *
     * @param id
     * @return
     */
    @Override
    public int delete(String id) {
        return mapper.deleteById(id);
    }

    /**
     * 插入轮播图
     *
     * @param dto
     * @return
     */
    @Override
    public int insert(RotationPicDTO dto) {
        RotationPic rotationPic = new RotationPic();
        BeanUtils.copyProperties(dto, rotationPic);
        rotationPic.setId(IdUtils.nextId());
        rotationPic.setInsertTime(LocalDateTime.now());
        rotationPic.setSort(mapper.maxSort() + 1);
        return mapper.insert(rotationPic);
    }

    @Override
    public int update(RotationPicDTO rotationPicDTO) {
        RotationPic rotationPic = new RotationPic();
        BeanUtils.copyProperties(rotationPicDTO, rotationPic);
        return mapper.updateById(rotationPic);
    }

    @Override
    public RotationPic detail(String id) {
        return mapper.selectById(id);
    }

    @Override
    public void swap(String id1, String id2) {
        RotationPic rotationPic = mapper.selectById(id1);
        RotationPic rotationPic1 = mapper.selectById(id2);
        //对换两个轮播图的排序
        Integer sort = rotationPic.getSort();
        Integer sort1 = rotationPic1.getSort();
        rotationPic.setSort(sort1);
        rotationPic1.setSort(sort);
        mapper.updateById(rotationPic);
        mapper.updateById(rotationPic1);
    }


}
