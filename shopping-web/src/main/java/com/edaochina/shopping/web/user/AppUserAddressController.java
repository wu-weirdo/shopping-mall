package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.user.UserAddressFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.user.UserAddressDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/useraddress")
@Api(tags = "收货地址")
public class AppUserAddressController {

    @Resource
    UserAddressFacade userAddressFacade;

    /**
     * 查询用户收货地址
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxResult list(UserAddressDTO dto) {
        return userAddressFacade.selectUserAddressList(dto);
    }

    /**
     * 新增用户收货地址
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult add(@RequestBody UserAddressDTO dto) {
        return userAddressFacade.addUserAddress(dto);
    }

    /**
     * 修改用户收货地址
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResult update(@RequestBody UserAddressDTO dto) {
        return userAddressFacade.updateUserAddress(dto);
    }

    /**
     * 删除用户收货地址
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public AjaxResult delete(@RequestBody UserAddressDTO dto) {
        return userAddressFacade.deleteUserAddress(dto);
    }

}
