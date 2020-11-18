package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.user.SysUserAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-10
 */
@Repository
public interface SysUserAdminMapper extends BaseMapper<SysUserAdmin> {

    Integer selectRoleId(@Param("account") String account);

    List<String> selectIdByRole(@Param("roleId") Integer roleId);

    int updateRoleId(@Param("id") String id, @Param("roleId") Integer roleId);

}
