package com.edaochina.shopping.api.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.sys.SysUserPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 账户权限dao
 * @since : 2019/7/26
 */
@Repository
@Mapper
public interface SysUserPermissionMapper extends BaseMapper<SysUserPermission> {
}
