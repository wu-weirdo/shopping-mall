package com.edaochina.shopping.api.facade.user.impl;

import com.edaochina.shopping.api.facade.user.UserAddressFacade;
import com.edaochina.shopping.api.service.user.UserAddressService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.user.UserAddressDTO;
import com.edaochina.shopping.domain.entity.user.UserAddress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAddressFacadeImpl implements UserAddressFacade {

    @Resource
    UserAddressService userAddressService;

    /**
     * 查询用户的收货地址
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult selectUserAddressList(UserAddressDTO dto) {
        List<UserAddress> list = userAddressService.selectUserAddressList(dto);
        Map<String, List<UserAddress>> map = new HashMap<>();
        map.put("list", list);
        return BaseResult.successResult(map);
    }

    /**
     * 添加用户收货地址
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult addUserAddress(UserAddressDTO dto) {
        int result = userAddressService.addUserAddress(dto);
        if (result > 0) {
            return BaseResult.successResult(ReturnData.SUCCESS.getCode(), ReturnData.SUCCESS.getMsg());
        } else {
            return BaseResult.failedResult(ReturnData.FAIL.getCode(), ReturnData.FAIL.getMsg());
        }
    }

    /**
     * 修改用户收货地址
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult updateUserAddress(UserAddressDTO dto) {
        int result = userAddressService.updateUserAddress(dto);
        if (result > 0) {
            return BaseResult.successResult(ReturnData.SUCCESS.getCode(), ReturnData.SUCCESS.getMsg());
        } else {
            return BaseResult.failedResult(ReturnData.FAIL.getCode(), ReturnData.FAIL.getMsg());
        }
    }

    /**
     * 删除用户收货地址
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult deleteUserAddress(UserAddressDTO dto) {
        int result = userAddressService.deleteUserAddress(dto);
        if (result > 0) {
            return BaseResult.successResult(ReturnData.SUCCESS.getCode(), ReturnData.SUCCESS.getMsg());
        } else {
            return BaseResult.failedResult(ReturnData.FAIL.getCode(), ReturnData.FAIL.getMsg());
        }
    }
}
