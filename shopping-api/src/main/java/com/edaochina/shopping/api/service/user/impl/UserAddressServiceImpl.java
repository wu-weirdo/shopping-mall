package com.edaochina.shopping.api.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.UserAddressMapper;
import com.edaochina.shopping.api.service.user.UserAddressService;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.dto.user.UserAddressDTO;
import com.edaochina.shopping.domain.entity.user.UserAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 收货地址表 by 张志侃 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Resource
    UserAddressMapper mapper;

    /**
     * 查询用户收货地址
     *
     * @param dto
     * @return
     */
    @Override
    public List<UserAddress> selectUserAddressList(UserAddressDTO dto) {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", dto.getUserId());
        wrapper.eq("delete_flag", 0);
        return mapper.selectList(wrapper);
    }

    /**
     * 新增用户收货地址
     *
     * @param dto
     * @return
     */
    @Override
    public int addUserAddress(UserAddressDTO dto) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(dto, userAddress);
        userAddress.setId(IdUtils.nextId());
        userAddress.setDeleteFlag(0);
        userAddress.setCreateTime(LocalDateTime.now());
        return mapper.insert(userAddress);
    }

    /**
     * 修改用户收货地址
     *
     * @param dto
     * @return
     */
    @Override
    public int updateUserAddress(UserAddressDTO dto) {
        UserAddress userAddress = this.getById(dto.getId());
        BeanUtils.copyProperties(dto, userAddress);
        return mapper.updateById(userAddress);
    }

    /**
     * 删除用户收货地址（逻辑删）
     *
     * @param dto
     * @return
     */
    @Override
    public int deleteUserAddress(UserAddressDTO dto) {
        UserAddress userAddress = this.getById(dto.getId());
        userAddress.setDeleteFlag(1);
        return mapper.updateById(userAddress);
    }
}
