package com.edaochina.shopping.api.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.sys.RotationPic;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xww
 * @since 2019-01-02
 */
public interface RotationPicMapper extends BaseMapper<RotationPic> {
    Integer maxSort();
}
