package com.edaochina.shopping.api.service.trade.impl;

import com.edaochina.shopping.api.dao.trade.TradeDetailShareProfitMapper;
import com.edaochina.shopping.api.service.trade.TradeDetailShareProfitService;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import com.edaochina.shopping.domain.vo.trade.SysShareProfitVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jintian
 * @date 2019/11/5 15:48
 */
@Service
public class TradeDetailShareProfitServiceImpl implements TradeDetailShareProfitService {

    @Resource
    private TradeDetailShareProfitMapper tradeDetailShareProfitMapper;

    @Override
    public PageResult<SysShareProfitVo> getShareProfitList(SysShareGoodsProfitDTO dto) {
        // 获取角色类型
        JWTBean jwtBean = JWTThreadLocalHelper.get();
        String role = jwtBean.getRole();
        String id = jwtBean.getId();
        // 设置查询的角色以及id
        dto.setRoleId(role);
        if (!RoleConstants.ADMIN_ROLE_STRING.equals(role)) {
            dto.setUserId(id);
        }
        PageHelperUtils.setPageHelper(dto.getPages());
        return PageHelperUtils.parseToPageResult(tradeDetailShareProfitMapper.getShareProfitList(dto));
    }

    @Override
    public BigDecimal getShareByRoleAndUserId(Integer roleId, String userId, LocalDateTime beginTime, LocalDateTime endTime) {
        BigDecimal shareProfig = tradeDetailShareProfitMapper.getShareByRoleAndUserId(roleId, userId, beginTime, endTime);
        if (shareProfig == null) {
            return new BigDecimal(0.00);
        }
        return shareProfig;
    }
}
