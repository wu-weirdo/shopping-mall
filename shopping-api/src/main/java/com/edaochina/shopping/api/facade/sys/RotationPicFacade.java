package com.edaochina.shopping.api.facade.sys;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.sys.RotationPicDTO;
import com.edaochina.shopping.domain.entity.sys.RotationPic;

import java.util.List;

public interface RotationPicFacade {

    /**
     * 轮播图列表
     *
     * @return
     */
    List<RotationPic> list();

    /**
     * 删除轮播图
     *
     * @param dto
     * @return
     */
    int delete(RotationPicDTO dto);

    /**
     * 插入轮播图
     *
     * @param dto
     * @return
     */
    int insert(RotationPicDTO dto);

    AjaxResult update(RotationPicDTO rotationPicDTO);

    AjaxResult detail(String id);

    AjaxResult swap(String id1, String id2);
}
