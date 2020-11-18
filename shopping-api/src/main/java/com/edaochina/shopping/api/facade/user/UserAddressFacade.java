package com.edaochina.shopping.api.facade.user;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.user.UserAddressDTO;

public interface UserAddressFacade {

    /**
     * 查询用户收货地址
     *
     * @param dto
     * @return
     */
    AjaxResult selectUserAddressList(UserAddressDTO dto);

    /**
     * 添加用户收货地址
     *
     * @param dto
     * @return
     */
    AjaxResult addUserAddress(UserAddressDTO dto);

    /**
     * 修改用户收货地址
     *
     * @param dto
     * @return
     */
    AjaxResult updateUserAddress(UserAddressDTO dto);

    /**
     * 删除用户收货地址
     *
     * @param dto
     * @return
     */
    AjaxResult deleteUserAddress(UserAddressDTO dto);

}
