package com.edaochina.shopping.api.facade.order;

import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.domain.entity.user.Community;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 通过小区查询商家信息
 * @since : 2019/6/27 15:24
 */
public interface QueryShopIdsByCommunity {

    /**
     * 通过小区查询商家信息
     *
     * @param communities            小区集合
     * @param sysUserMerchantService 商家服务
     * @return 商家id集合
     */
    default Set<String> queryShopIdsByCommunity(List<Community> communities, SysUserMerchantService sysUserMerchantService) {
        return sysUserMerchantService
                .selectByCommunity(communities.parallelStream().map(Community::getId).collect(Collectors.toList()))
                .parallelStream().map(SysUserMerchant::getId).collect(Collectors.toSet());
    }

}
