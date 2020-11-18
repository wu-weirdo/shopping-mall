package com.edaochina.shopping.api.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.sys.OperationLogDTO;
import com.edaochina.shopping.domain.entity.sys.OperationLog;

import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 操作记录服务
 * @since : 17:25
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查询记录列表
     *
     * @param dto 参数
     * @return 记录列表
     */
    IPage<OperationLog> list(OperationLogDTO dto);

    List<OperationLog> users();

}
