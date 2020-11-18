package com.edaochina.shopping.api.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.sys.OperationLog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 操作记录dao
 * @since : 17:24
 */
@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    @Select("SELECT DISTINCT user_id, user_name FROM operation_log;")
    List<OperationLog> users();

}
