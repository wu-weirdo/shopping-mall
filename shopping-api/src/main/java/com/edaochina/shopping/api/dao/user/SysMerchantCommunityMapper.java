package com.edaochina.shopping.api.dao.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.user.SysMerchantCommunity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商家小区表 Mapper 接口
 * </p>
 *
 * @since 2019-05-16
 */
@Repository
public interface SysMerchantCommunityMapper extends BaseMapper<SysMerchantCommunity> {

    List<SysMerchantCommunity> queryCommunityByMerchant(String merchantId);

    int insertMerchantCommunity(@Param("merchantId") String merchantId, @Param("community") String community);

    int deleteMerchantCommunity(@Param("merchantId") String merchantId);

    int updateMerchantCommunity(@Param("merchantId") String merchantId);
}
