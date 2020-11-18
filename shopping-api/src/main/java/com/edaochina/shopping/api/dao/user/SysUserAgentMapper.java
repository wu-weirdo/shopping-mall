package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.user.AgentDTO;
import com.edaochina.shopping.domain.entity.user.SysUserAgent;
import com.edaochina.shopping.domain.vo.sys.SysHasAgenAreaVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 代理商表 by 张志侃 Mapper 接口
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Repository
public interface SysUserAgentMapper extends BaseMapper<SysUserAgent> {

    List<SysHasAgenAreaVo> queryDistinctArea();

    SysUserAgent queryAgentByCounty(String countyId);

    int queryCountByAgentDTO(AgentDTO agentDTO);

    List<SysUserAgent> queryByAgentDTO(AgentDTO agentDTO);

    int updateBalanceMoney(@Param("userId") String userId, @Param("money") double v);

    @Select("select * from sys_user_agent where account = #{account} and status = 0")
    SysUserAgent queryByAccount(String account);
}
