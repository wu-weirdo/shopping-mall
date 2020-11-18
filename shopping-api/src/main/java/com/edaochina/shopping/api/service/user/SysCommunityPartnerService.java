package com.edaochina.shopping.api.service.user;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.user.CheckInvitatCodeDTO;
import com.edaochina.shopping.domain.dto.user.CommuntyPartnerDTO;
import com.edaochina.shopping.domain.dto.user.CommuntyPartnerRegisterDTO;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import com.edaochina.shopping.domain.entity.user.SysCommunityPartner;
import com.edaochina.shopping.domain.vo.user.SysCommunityPartnerDetailVO;
import com.edaochina.shopping.domain.vo.user.SysCommunityPartnerVo;
import com.edaochina.shopping.domain.vo.user.SysUserVO;

import java.math.BigDecimal;

/**
 * 群社合伙人服务类
 *
 * @author jintian
 * @date 2019/7/24 14:15
 */
public interface SysCommunityPartnerService {

    /**
     * 群社合伙人注册
     *
     * @param dto
     * @return
     */
    boolean register(CommuntyPartnerRegisterDTO dto);


    PageResult<SysCommunityPartnerVo> querySysCommunityPartners(CommuntyPartnerDTO dto);

    SysCommunityPartnerDetailVO queryDetal(String id);

    boolean update(CommuntyPartnerRegisterDTO dto);

    SysUserVO partenerCheck(LoginDTO dto);

    int addBalance(String partenerId, BigDecimal agentPrice);

    SysCommunityPartner checkInvitatCode(CheckInvitatCodeDTO dto);
}
