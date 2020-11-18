package com.edaochina.shopping.api.facade.sys.impl;

import com.edaochina.shopping.api.facade.sys.RotationPicFacade;
import com.edaochina.shopping.api.service.sys.RotationPicService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.sys.RotationPicDTO;
import com.edaochina.shopping.domain.entity.sys.RotationPic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RotationPicFacadeImpl implements RotationPicFacade {

    @Resource
    RotationPicService rotationPicService;

    /**
     * 查询轮播图列表
     *
     * @return
     */
    @Override
    public List<RotationPic> list() {
        return rotationPicService.list();
    }

    /**
     * 删除轮播图
     *
     * @param dto
     * @return
     */
    @Override
    public int delete(RotationPicDTO dto) {
        return rotationPicService.delete(dto.getId());
    }

    /**
     * 插入轮播图
     *
     * @param dto
     * @return
     */
    @Override
    public int insert(RotationPicDTO dto) {
        return rotationPicService.insert(dto);
    }

    @Override
    public AjaxResult update(RotationPicDTO rotationPicDTO) {
        return BaseResult.successResult(rotationPicService.update(rotationPicDTO));
    }

    @Override
    public AjaxResult detail(String id) {
        return BaseResult.successResult(rotationPicService.detail(id));
    }

    @Override
    public AjaxResult swap(String id1, String id2) {
        rotationPicService.swap(id1, id2);
        return BaseResult.successResult();
    }

}
