package com.edaochina.shopping.api.facade.law;

import com.edaochina.shopping.domain.dto.law.FadadaReturnInfo;

/**
 * @author jintian
 * @date 2019/5/31 10:36
 */
public interface FadadaLawFacade {

    /**
     * 注册用户
     *
     * @param userId 用户id
     * @param type   用户类型id(3:商家，5:普通用户)
     * @return 签署页面url
     */
    String registerAccount(String userId, String type);

    /**
     * 用户签署
     *
     * @param userId 用户id
     * @return 签署页面url
     */
    String userSign(String userId, String type, String contractId, String returnUrl);

    String uploadContract(String userId, String type);

    /**
     * 接受签署回调内容并处理
     *
     * @param returnInfo
     */
    void acceptSignReturn(FadadaReturnInfo returnInfo);

    String merchantSignContrat(String userId, String returnUrl);
}
