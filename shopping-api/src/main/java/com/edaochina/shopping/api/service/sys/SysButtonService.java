package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.sys.SysButton;
import com.edaochina.shopping.domain.vo.sys.SysPermissionInfo;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 按钮表 服务类
 * </p>
 *
 * @since 2019-06-20
 */
public interface SysButtonService {

    Collection<SysPermissionInfo> buttonList(Integer roleId);

    int insertButton(SysButton sysButton);

    SysButton selectById(Integer id);

    List<String> buttonAliasList(Integer roleId);

    List<String> buttonUserAliasList(Integer accountId);

    List<String> allButtonAlias();

    Collection<SysPermissionInfo> selectByAccountId(String userId, String memberType);
}
