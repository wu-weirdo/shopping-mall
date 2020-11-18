package com.edaochina.shopping.api.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.dto.user.UserAddressDTO;
import com.edaochina.shopping.domain.entity.user.UserAddress;

import java.util.List;

/**
 * <p>
 * 收货地址表 by 张志侃 服务类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 查询用户收货地址
     *
     * @param dto
     * @return
     */
    List<UserAddress> selectUserAddressList(UserAddressDTO dto);

    /**
     * 添加用户收货地址
     *
     * @param dto
     * @return
     */
    int addUserAddress(UserAddressDTO dto);

    /**
     * 修改用户收货地址
     *
     * @param dto
     * @return
     */
    int updateUserAddress(UserAddressDTO dto);

    /**
     * 删除用户收货地址
     *
     * @param dto
     * @return
     */
    int deleteUserAddress(UserAddressDTO dto);
}
