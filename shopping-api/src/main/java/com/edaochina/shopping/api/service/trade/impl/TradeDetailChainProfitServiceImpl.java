package com.edaochina.shopping.api.service.trade.impl;

import com.edaochina.shopping.api.dao.trade.TradeDetailChainProfitMapper;
import com.edaochina.shopping.api.service.trade.TradeDetailChainProfitService;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.trade.SysChainGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailChainProfit;
import com.edaochina.shopping.domain.vo.trade.SysChainProfitVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 供应链交易分润明细(TradeDetailChainProfit)表服务实现类
 *
 * @author makejava
 * @since 2019-11-13 13:48:47
 */
@Service("tradeDetailChainProfitService")
public class TradeDetailChainProfitServiceImpl implements TradeDetailChainProfitService {
    @Resource
    private TradeDetailChainProfitMapper tradeDetailChainProfitDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TradeDetailChainProfit queryById(Integer id) {
        return this.tradeDetailChainProfitDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<TradeDetailChainProfit> queryAllByLimit(int offset, int limit) {
        return this.tradeDetailChainProfitDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tradeDetailChainProfit 实例对象
     * @return 实例对象
     */
    @Override
    public TradeDetailChainProfit insert(TradeDetailChainProfit tradeDetailChainProfit) {
        this.tradeDetailChainProfitDao.insert(tradeDetailChainProfit);
        return tradeDetailChainProfit;
    }

    /**
     * 修改数据
     *
     * @param tradeDetailChainProfit 实例对象
     * @return 实例对象
     */
    @Override
    public TradeDetailChainProfit update(TradeDetailChainProfit tradeDetailChainProfit) {
        this.tradeDetailChainProfitDao.update(tradeDetailChainProfit);
        return this.queryById(tradeDetailChainProfit.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tradeDetailChainProfitDao.deleteById(id) > 0;
    }

    @Override
    public PageResult<SysChainProfitVo> getChainProfitList(SysChainGoodsProfitDTO dto) {
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
        return PageHelperUtils.parseToPageResult(tradeDetailChainProfitDao.getChainProfitList(dto));
    }

    @Override
    public PageResult<SysChainProfitVo> getChainProfitListApp(SysChainGoodsProfitDTO dto) {
        String role = dto.getRoleId();
        String id = dto.getUserId();
        // 设置查询的角色以及id
        dto.setRoleId(role);
        if (!RoleConstants.ADMIN_ROLE_STRING.equals(role)) {
            dto.setUserId(id);
        }
        PageHelperUtils.setPageHelper(dto.getPages());
        return PageHelperUtils.parseToPageResult(tradeDetailChainProfitDao.getChainProfitList(dto));
    }
}