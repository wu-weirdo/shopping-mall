package com.edaochina.shopping.api.service.trade.impl;

import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.date.LocalDateTimeUtil;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.trade.ChainPayInfo;
import com.edaochina.shopping.domain.dto.trade.ChainPayInfoAffirmDto;
import com.edaochina.shopping.domain.dto.trade.ChainRecipientDto;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.goods.SupplyChain;
import com.edaochina.shopping.domain.entity.trade.ChainRecipientDetail;
import com.edaochina.shopping.api.dao.trade.ChainRecipientDetailDao;
import com.edaochina.shopping.api.service.trade.ChainRecipientDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 供应链发货管理(ChainRecipientDetail)表服务实现类
 *
 * @author makejava
 * @since 2019-11-13 15:05:07
 */
@Service("chainRecipientDetailService")
public class ChainRecipientDetailServiceImpl implements ChainRecipientDetailService {
    @Resource
    private ChainRecipientDetailDao chainRecipientDetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ChainRecipientDetail queryById(Integer id) {
        return this.chainRecipientDetailDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ChainRecipientDetail> queryAllByLimit(int offset, int limit) {
        return this.chainRecipientDetailDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param chainRecipientDetail 实例对象
     * @return 实例对象
     */
    @Override
    public ChainRecipientDetail insert(ChainRecipientDetail chainRecipientDetail) {
        this.chainRecipientDetailDao.insert(chainRecipientDetail);
        return chainRecipientDetail;
    }

    /**
     * 修改数据
     *
     * @param chainRecipientDetail 实例对象
     * @return 实例对象
     */
    @Override
    public ChainRecipientDetail update(ChainRecipientDetail chainRecipientDetail) {
        this.chainRecipientDetailDao.update(chainRecipientDetail);
        return this.queryById(chainRecipientDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.chainRecipientDetailDao.deleteById(id) > 0;
    }

    @Override
    public boolean uploadPayInfo(ChainPayInfo chainPayInfo) {
        ChainRecipientDetail chainRecipientDetail = chainRecipientDetailDao.queryById(chainPayInfo.getId());
        BeanUtils.copyProperties(chainPayInfo, chainRecipientDetail);
        // 如果未收款，重置为已上传
        chainRecipientDetail.setPayStatus(1);
        chainRecipientDetail.setShipmentsStatus(1);
        return chainRecipientDetailDao.update(chainRecipientDetail) > 0;
    }

    @Override
    public PageResult getChainRecipientList(ChainRecipientDto dto) {
        JWTBean jwtBean = JWTThreadLocalHelper.get();
        if (!RoleConstants.ADMIN_ROLE_STRING.equals(jwtBean.getRole())) {
            dto.setUserId(jwtBean.getId());
        }
        PageHelperUtils.setPageHelper(dto.getPages());
        return PageHelperUtils.parseToPageResult(chainRecipientDetailDao.getChainRecipientList(dto));
    }

    @Override
    public boolean payInfoAffirm(ChainPayInfoAffirmDto dto) {
        JWTBean jwtBean = JWTThreadLocalHelper.get();
        // 检查权限
        if (!RoleConstants.ADMIN_ROLE_STRING.equals(jwtBean.getRole())) {
            throw new YidaoException(ReturnData.SYSTEM_BIZ_LIMIT);
        }
        ChainRecipientDetail chainRecipientDetail = new ChainRecipientDetail();
        BeanUtils.copyProperties(dto, chainRecipientDetail);
        if (dto.getPayStatus() == 2) {
            chainRecipientDetail.setShipmentsStatus(2);
            chainRecipientDetail.setShipmentsTime(LocalDateTimeUtil.dateToLocalDateTime(new Date()));
        }
        return chainRecipientDetailDao.update(chainRecipientDetail) > 0;
    }

    @Override
    public void addChainRecipientDetailBySupplyChain(SupplyChain supplyChain, Goods goods) {
        ChainRecipientDetail chainRecipientDetail = new ChainRecipientDetail();
        chainRecipientDetail.setChainId(supplyChain.getId());
        chainRecipientDetail.setGoodsId(goods.getId());
        chainRecipientDetail.setMerchantId(goods.getShopId());
        chainRecipientDetailDao.insert(chainRecipientDetail);
    }
}