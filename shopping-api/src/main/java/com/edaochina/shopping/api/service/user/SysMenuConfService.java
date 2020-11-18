package com.edaochina.shopping.api.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.user.SysMenuConf;
import com.edaochina.shopping.domain.vo.user.SysMenuConfVO;

import java.util.List;

/**
 * <p>
 * 系统菜单分配表 by 张志侃 服务类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
public interface SysMenuConfService extends IService<SysMenuConf> {
    List<SysMenuConfVO> selectList(String roleId);
}
