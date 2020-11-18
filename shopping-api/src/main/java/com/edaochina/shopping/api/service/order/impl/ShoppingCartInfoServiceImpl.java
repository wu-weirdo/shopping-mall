package com.edaochina.shopping.api.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.edaochina.shopping.api.dao.trade.ShoppingCartInfoMapper;
import com.edaochina.shopping.api.service.order.ShoppingCartInfoService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.domain.dto.order.OrderPayDTO;
import com.edaochina.shopping.domain.dto.trade.DelCartListDTO;
import com.edaochina.shopping.domain.dto.trade.ShoppingCartDTO;
import com.edaochina.shopping.domain.entity.trade.ShoppingCartInfo;
import com.edaochina.shopping.domain.entity.trade.UpdateShoppingCartInfo;
import com.edaochina.shopping.domain.vo.trade.AppShoppingCartInfo;
import com.edaochina.shopping.domain.vo.trade.ShoppingCartInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartInfoServiceImpl implements ShoppingCartInfoService {

    @Resource
    ShoppingCartInfoMapper shoppingCartInfoMapper;
    private Logger logger = LoggerFactory.getLogger(ShoppingCartInfoServiceImpl.class);

    @Override
    public boolean insertShoppingCart(ShoppingCartInfo shoppingCartInfo) {
        logger.info("insertShoppingCart:" + JSON.toJSONString(shoppingCartInfo));
        if (StringUtils.isBlank(shoppingCartInfo.getUserId())) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        if (StringUtils.isBlank(shoppingCartInfo.getGoodId())) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        if (shoppingCartInfo.getNum() == null || shoppingCartInfo.getNum() == 0) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        //判断是否超过购物车上限
        if (shoppingCartInfoMapper.shoppingCartCount(shoppingCartInfo.getUserId()) >= 24) {
            throw new YidaoException(ReturnData.ADD_SHOPPING_CART_ERROR.getCode(), ReturnData.ADD_SHOPPING_CART_ERROR.getMsg());
        }
        // 判断是否存在
        ShoppingCartInfo has = shoppingCartInfoMapper.queryByUserIdAndGoodId(shoppingCartInfo.getUserId(), shoppingCartInfo.getGoodId());
        logger.info("ShoppingCartInfo has:" + has);
        if (has == null) {
            return shoppingCartInfoMapper.addShoppingCart(shoppingCartInfo) > 0;
        } else {
            has.setNum(has.getNum() + shoppingCartInfo.getNum());
            return updateShoppingCart(has) > 0;
        }

    }

    @Override
    public int delShoppingCart(Integer id) {
        logger.info("delShoppingCart req id:" + id);
        if (id == null || id == 0) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        return shoppingCartInfoMapper.delShoppingCart(id);
    }

    @Override
    public int updateShoppingCartStatus(Integer id, Integer deleteFlag) {
        return shoppingCartInfoMapper.updateCartDelStatus(id, deleteFlag);
    }

    @Override
    public int updateShoppingCart(ShoppingCartInfo shoppingCartInfo) {
        logger.info("updateShoppingCart shoppingCartInfo:" + JSON.toJSONString(shoppingCartInfo));
        if (shoppingCartInfo.getId() == null || shoppingCartInfo.getId() == 0) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        if (shoppingCartInfo.getNum() == null || shoppingCartInfo.getNum() == 0) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        return shoppingCartInfoMapper.updateShoppingCart(shoppingCartInfo);
    }

    @Override
    public Collection<AppShoppingCartInfo> queryUserShoppingCart(String userId, String longitude, String latitude) {
        logger.info("queryUserShoppingCart req:【userId:" + userId + ",longitude:" + longitude + ",latitude:" + latitude + "】");
        if (StringUtils.isBlank(userId)) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        List<ShoppingCartInfoVo> shoppingCartInfoVos = shoppingCartInfoMapper.queryUserShoppingCart(userId, longitude, latitude);
        Map<String, AppShoppingCartInfo> shopMap = new HashMap<>();
        shoppingCartInfoVos.forEach(shoppingCartInfoVo -> {
            AppShoppingCartInfo appShoppingCartInfo;
            if (shopMap.containsKey(shoppingCartInfoVo.getShopId())) {
                appShoppingCartInfo = shopMap.get(shoppingCartInfoVo.getShopId());
            } else {
                appShoppingCartInfo = new AppShoppingCartInfo();
                appShoppingCartInfo.setShopId(shoppingCartInfoVo.getShopId());
                appShoppingCartInfo.setShopName(shoppingCartInfoVo.getShopName());
                appShoppingCartInfo.setGoodsList(new ArrayList<>());
            }
            appShoppingCartInfo.getGoodsList().add(shoppingCartInfoVo);
            shopMap.put(shoppingCartInfoVo.getShopId(), appShoppingCartInfo);
        });
        return shopMap.values();
    }

    @Override
    public List<AppShoppingCartInfo> v2QueryUserShoppingCart(ShoppingCartDTO dto) {
        logger.info("v2QueryUserShoppingCart req:【userId:" + dto.getUserId() + ",longitude:" + dto.getLongitude() + ",latitude:" + dto.getLatitude() + "】");
        if (StringUtils.isBlank(dto.getUserId())) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        List<ShoppingCartInfoVo> shoppingCartInfoVos = shoppingCartInfoMapper.v2QueryUserShoppingCart(dto);
        logger.info("shoppingCartInfoVos size :" + (shoppingCartInfoVos == null ? 0 : shoppingCartInfoVos.size()));

        List<AppShoppingCartInfo> appShoppingCartInfos = shoppingCartInfoVos.parallelStream()
                // 按照商家id与是否过期分组
                .collect(Collectors.groupingBy(shoppingCartInfoVo -> shoppingCartInfoVo.getShopId() + "_" + shoppingCartInfoVo.getExpiredFlag()))
                .values()
                .parallelStream()
                // 将商品id相同的合并
                .map(shoppingCartInfoVosListByShopId -> shoppingCartInfoVosListByShopId.parallelStream()
                        .collect(Collectors.toMap(ShoppingCartInfo::getGoodId, shoppingCartInfoVo -> shoppingCartInfoVo,
                                (shoppingCartInfo, shoppingCartInfo2) -> {
                                    shoppingCartInfo.setNum(shoppingCartInfo.getNum() + shoppingCartInfo2.getNum());
                                    return shoppingCartInfo;
                                }))
                        .values().parallelStream().collect(Collectors.toList()))
                // 商家相同的商品聚合在一个对象中
                .map(shoppingCartInfoVosListByShopId -> {
                    AppShoppingCartInfo appShoppingCartInfo = new AppShoppingCartInfo();
                    appShoppingCartInfo.setShopId(shoppingCartInfoVosListByShopId.get(0).getShopId());
                    appShoppingCartInfo.setShopName(shoppingCartInfoVosListByShopId.get(0).getShopName());
                    appShoppingCartInfo.setExpiredFlag(shoppingCartInfoVosListByShopId.get(0).getExpiredFlag());
                    appShoppingCartInfo.setGoodsList(shoppingCartInfoVosListByShopId);
                    return appShoppingCartInfo;
                })
                .sorted(Comparator.comparingInt(AppShoppingCartInfo::getExpiredFlag))
                .collect(Collectors.toList());

        return appShoppingCartInfos;
    }

    /**
     * 根据主键查询购物车信息
     *
     * @param shoppingCartId
     * @return
     */
    @Override
    public ShoppingCartInfo queryById(Integer shoppingCartId) {
        logger.info("queryById shoppingCartId：" + shoppingCartId);
        if (shoppingCartId == null || shoppingCartId == 0) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getCode(), ReturnData.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        }
        return shoppingCartInfoMapper.queryById(shoppingCartId);
    }

    @Override
    public void cleanOverTimeShoppingCart() {
        shoppingCartInfoMapper.cleanOverTimeShoppingCart();
    }

    @Override
    public int updateShoppingCartList(UpdateShoppingCartInfo updateShoppingCartInfo) {
        logger.info("updateShoppingCartList:" + JSON.toJSONString(updateShoppingCartInfo));
        List<ShoppingCartInfo> shoppingCarts = updateShoppingCartInfo.getShoppingCarts();
        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            return shoppingCartInfoMapper.removeAllShoppingCarts(updateShoppingCartInfo.getUserId());
        }
        return shoppingCartInfoMapper.updateShoppingCartList(shoppingCarts);
    }

    @Override
    public int restoreShoppingCart(OrderPayDTO orderPayDTO) {
        logger.info("restoreShoppingCart req:" + JSON.toJSONString(orderPayDTO));
        return shoppingCartInfoMapper.restoreShoppingCart(orderPayDTO);
    }

    @Override
    public int deleteInvalidCart(String userId) {
        logger.info("deleteInvalidCart req:" + JSON.toJSONString(userId));
        return shoppingCartInfoMapper.deleteInvalidCart(userId);
    }

    @Override
    public int validCartCount(String userId) {
        Integer count = shoppingCartInfoMapper.validCartCount(userId);
        return count == null ? 0 : count;
    }

    @Override
    public int deleteCartList(DelCartListDTO dto) {
        logger.info("deleteCartList req:" + JSON.toJSONString(dto));
        return shoppingCartInfoMapper.deleteCartList(dto);
    }

}
