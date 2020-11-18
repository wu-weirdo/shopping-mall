package com.edaochina.shopping.api.dao.sys;

import com.edaochina.shopping.domain.entity.sys.SysButton;
import com.edaochina.shopping.domain.vo.sys.SysButtonVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 按钮表 Mapper 接口
 * </p>
 *
 * @since 2019-06-20
 */
public interface SysButtonMapper {

    List<SysButtonVO> buttonList(@Param("roleId") Integer roleId);

    List<SysButtonVO> selectByAccountId(@Param("accountId") Integer accountId);

    int insertButton(SysButton sysButton);

    SysButton selectById(@Param("id") Integer id);

    List<String> buttonAliasList(@Param("roleId") Integer roleId);

    List<String> buttonUserAliasList(@Param("accountId") Integer accountId);

    List<String> allButtonAlias();
}
