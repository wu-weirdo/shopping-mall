package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.dto.sys.RotationPicDTO;
import com.edaochina.shopping.domain.entity.sys.RotationPic;

import java.util.List;

public interface RotationPicService {

    /**
     * 查询轮播图列表
     *
     * @return
     */
    List<RotationPic> list();

    /**
     * 删除轮播图
     *
     * @return
     */
    int delete(String id);

    /**
     * 插入轮播图
     *
     * @return
     */
    int insert(RotationPicDTO dto);

    int update(RotationPicDTO rotationPicDTO);

    RotationPic detail(String id);

    void swap(String id1, String id2);
}
