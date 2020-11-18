package com.edaochina.shopping.api.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.sys.OperationLogMapper;
import com.edaochina.shopping.api.service.sys.OperationLogService;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.sys.OperationLogDTO;
import com.edaochina.shopping.domain.entity.sys.OperationLog;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 操作记录服务实现类
 * @since : 17:27
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public IPage<OperationLog> list(OperationLogDTO dto) {
        Pages pages = dto.getPages();
        QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getUserId())) {
            queryWrapper.eq("user_id", dto.getUserId());
        }
        if (dto.getBeginTime() != null) {
            queryWrapper.le("create_time", dto.getEndTime());
            queryWrapper.ge("create_time", dto.getBeginTime());
        }
        queryWrapper.and(operationLogQueryWrapper -> operationLogQueryWrapper.like("operation", "部门")
                .or().like("operation", "管理员"));
        return this.page(new Page<>(pages.getPageIndex(), pages.getPageSize()), queryWrapper);
    }

    @Override
    public List<OperationLog> users() {
        return baseMapper.users();
    }

}
