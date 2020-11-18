package com.edaochina.shopping.api.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.SysMenuConfMapper;
import com.edaochina.shopping.api.service.user.SysMenuConfService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.domain.entity.user.SysMenuConf;
import com.edaochina.shopping.domain.vo.user.SysMenuConfVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统菜单分配表 by 张志侃 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Service
public class SysMenuConfServiceImpl extends ServiceImpl<SysMenuConfMapper, SysMenuConf> implements SysMenuConfService {

    @Override
    public List<SysMenuConfVO> selectList(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER);
        }
        QueryWrapper<SysMenuConf> sysMenuConfQueryWrapper = new QueryWrapper<>();
        sysMenuConfQueryWrapper.eq("role_id", roleId);

        List<SysMenuConf> sysMenuAssignmens = this.list(sysMenuConfQueryWrapper);
        if (sysMenuAssignmens == null || sysMenuAssignmens.isEmpty()) {
            throw new YidaoException(ReturnData.USER_WITHOUT_MENU);
        }
        try {
            return BeanUtil.listBeanToList(sysMenuAssignmens, SysMenuConfVO.class);
        } catch (Exception e) {
            LoggerUtils.error(SysMenuConfServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.REFLECT_ERROR);
        }
    }
}
