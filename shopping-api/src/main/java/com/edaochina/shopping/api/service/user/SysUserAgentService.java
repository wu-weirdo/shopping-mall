package com.edaochina.shopping.api.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.user.AgentDTO;
import com.edaochina.shopping.domain.dto.user.AgentRegDTO;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import com.edaochina.shopping.domain.dto.user.UpdAgentDTO;
import com.edaochina.shopping.domain.entity.user.SysUserAgent;
import com.edaochina.shopping.domain.vo.user.AgentVO;
import com.edaochina.shopping.domain.vo.user.SysUserVO;

import java.math.BigDecimal;

/**
 * <p>
 * 代理商表 by 张志侃 服务类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
public interface SysUserAgentService extends IService<SysUserAgent> {
    /**
     * 代理商注册
     *
     * @param dto
     * @return
     */
    AjaxResult agentRegister(AgentRegDTO dto);

    /**
     * 代理商密码校验
     *
     * @param dto
     * @return
     */
    SysUserVO agentCheck(LoginDTO dto);

    /**
     * 代理商列表
     *
     * @param agentDTO
     * @return
     */
    PageResult<AgentVO> agentList(AgentDTO agentDTO);

    /**
     * 修改代理商信息
     *
     * @param updAgentDTO
     * @return
     */
    AjaxResult agentUpdate(UpdAgentDTO updAgentDTO);

    /**
     * 代理商详情
     */
    AjaxResult agentDetail(String id);

    SysUserAgent queryByCountyId(String countyId);

    int addProfitMoney(String userId, BigDecimal toAccountMoney);

    /**
     * 查询商家归属代理商
     *
     * @param shareMerchantId
     * @return
     */
    SysUserAgent queryMerchantAffiAgent(String shareMerchantId);
}
