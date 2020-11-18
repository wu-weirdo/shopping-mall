package com.edaochina.shopping.api.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.sys.SysContractTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 合同模板表 Mapper 接口
 * </p>
 *
 * @since 2019-05-24
 */
@Repository
public interface SysContractTemplateMapper extends BaseMapper<SysContractTemplate> {

    SysContractTemplate queryById(@Param("id") int id);

    SysContractTemplate queryByRemark(@Param("remark") String remark);
}
