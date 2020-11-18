package com.edaochina.shopping.api.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.user.*;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.sys.SysHasAgenAreaVo;
import com.edaochina.shopping.domain.vo.user.MerchantVO;
import com.edaochina.shopping.domain.vo.user.SysUserVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商户表 by 张志侃 服务类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
public interface SysUserMerchantService extends IService<SysUserMerchant> {
    /**
     * 商家注册
     *
     * @param dto
     * @return
     */
    AjaxResult merchantRegister(MerchantRegDTO dto);

    /**
     * 商家密码校验
     *
     * @param dto
     * @return
     */
    SysUserVO merchantCheck(LoginDTO dto);

    /**
     * 商家列表
     *
     * @param merchantDTO
     * @return
     */
    PageResult<MerchantVO> merchantList(MerchantDTO merchantDTO);

    AjaxResultGeneric<Boolean> quitLeague(String id);

    AjaxResultGeneric<Boolean> league(String id);

    /**
     * 商户详情
     *
     * @param id
     * @return
     */
    AjaxResult merchantDetail(String id);

    /**
     * 编辑商户
     */
    AjaxResult merchantUpdate(UpdMerchantDTO dto);

    /**
     * 删除商户
     */
    AjaxResult deleteMerchant(UpdMerchantDTO dto);

    AjaxResult appMerchantRegister(AppMerchantRegDTO dto);

    Integer queryMemberNumByCountId(String countyId);

    List<SysUserMerchant> querySysUserMerchantByCommunityId(String countyId);

    void getQrCodeByMerchantId(String merchantId);

    List<SysUserMerchant> queryNoQrCode();

    /**
     * @param community 小区id集合
     * @return 商家集合
     */
    List<SysUserMerchant> selectByCommunity(List<String> community);

    /**
     * @param countyId 区县id
     * @return 3级区县信息
     */
    SysHasAgenAreaVo selectAddress(String countyId);

    void updateUserLawId(String userId, String data);

    /**
     * 添加商户商品分润到账金额
     *
     * @param shoperId
     * @param totalIncome
     * @return
     */
    int addProfitMoney(String shoperId, BigDecimal totalIncome);
}
