package com.edaochina.shopping.api.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.edaochina.shopping.api.dao.order.OrderErrorBackMapper;
import com.edaochina.shopping.api.dao.order.OrderErrorHandleMapper;
import com.edaochina.shopping.api.dao.order.OrderErrorImgsMapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.dao.user.SysUserMerchantMapper;
import com.edaochina.shopping.api.dao.user.UserMapper;
import com.edaochina.shopping.api.service.order.OrderErrorBackService;
import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.StringUtil;
import com.edaochina.shopping.common.utils.ThreadUtils;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.OrderStatusConstants;
import com.edaochina.shopping.domain.dto.order.OrderErrorBackDTO;
import com.edaochina.shopping.domain.dto.order.QueryOrderErrorBackDTO;
import com.edaochina.shopping.domain.dto.order.UpdOrderErrorDTO;
import com.edaochina.shopping.domain.entity.order.OrderErrorBack;
import com.edaochina.shopping.domain.entity.order.OrderErrorHandle;
import com.edaochina.shopping.domain.entity.order.OrderErrorImgs;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.order.OrderErrorBackVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 异常订单反馈表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
@Service
@Transactional
public class OrderErrorBackServiceImpl implements OrderErrorBackService {

    private static Logger logger = LoggerFactory.getLogger(OrderErrorBackServiceImpl.class);

    @Resource
    private OrderMainMapper orderMainMapper;

    @Resource
    private SysUserMerchantMapper merchantMapper;

    @Resource
    private OrderErrorBackMapper orderErrorBackMapper;

    @Resource
    private OrderErrorHandleMapper orderErrorHandleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderErrorImgsMapper orderErrorImgsMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public int insertOrderError(OrderErrorBackDTO dto) {
        logger.info("order error add req:" + JSON.toJSONString(dto));
        if (StringUtil.containsEmoji(dto.getExceptionReason())) {
            throw new YidaoException(ReturnData.ERROR_ORDER_REASON_ERROR);
        }
        if (orderErrorBackMapper.selectByOrderId(dto.getOrderId()) != null) {
            throw new YidaoException(ReturnData.ERROR_ORDER_EXIST.getCode(), ReturnData.ERROR_ORDER_EXIST.getMsg());
        } else {
            OrderErrorBack orderErrorBack = new OrderErrorBack();
            BeanUtils.copyProperties(dto, orderErrorBack);
            OrderMain order = orderMainMapper.selectById(dto.getOrderId());
            orderErrorBack.setMerchantId(order.getShoperId());
            orderErrorBack.setOrderStatus(order.getStatus());
            orderErrorBack.setMerchantTitle(order.getShoperName());
            orderErrorBack.setGoodsName(order.getGoodsName());
            orderErrorBack.setGoodsNum(order.getGoodsNum());
            orderErrorBack.setGoodsPrice(order.getTotalPrice());
            orderErrorBack.setUserId(order.getUserId());
            SysUser user = userMapper.getUserById(order.getUserId());
            orderErrorBack.setUserPhone(user.getPhone());
            orderErrorBack.setOrderTime(order.getCreateTime());
            orderErrorBack.setWriteOffTime(order.getWriteOffTime());
            orderErrorBack.setWriteOffStatus(order.getWriteOffStatus());
            SysUserMerchant merchant = merchantMapper.selectById(order.getShoperId());
            orderErrorBack.setMerchantPhone(merchant.getPhone());
            orderErrorBack.setCreateTime(LocalDateTime.now());
            orderErrorBackMapper.saveToDb(orderErrorBack);
            if (CollectionUtils.isNotEmpty(dto.getImgs())) {
                for (int i = 0; i < dto.getImgs().size(); i++) {
                    saveErrorImg(dto.getImgs().get(i), orderErrorBack.getId());
                }
            }
            return 1;
        }
    }

    private void saveErrorImg(String imgUrl, Integer id) {
        ThreadUtils.execute(() -> {
            OrderErrorImgs errorImgs = new OrderErrorImgs();
            errorImgs.setOrderErrorId(id);
            // 针对app进行改造
            if (imgUrl.startsWith("http://cd-shopping.oss-cn-hangzhou.aliyuncs.com") || imgUrl.startsWith("https://cd-shopping.oss-cn-hangzhou.aliyuncs.com")) {
                errorImgs.setImgUrl(imgUrl);
            } else {
                errorImgs.setImgUrl(commonService.appUploadImage(imgUrl));
            }
            orderErrorImgsMapper.insertImg(errorImgs);
        });
    }

    @Override
    public PageResult<OrderErrorBackVO> orderErrorBackList(QueryOrderErrorBackDTO dto) {
        List<OrderErrorBackVO> orderErrorBacks = orderErrorBackMapper.orderErrorList(dto);
        int i = orderErrorBackMapper.orderErrorCount(dto);
        Pages pages = dto.getPages();
        pages.setTotal(i);
        return PageUtil.getPageResult(orderErrorBacks, pages);
    }

    @Override
    public int updateOrderErrorBack(UpdOrderErrorDTO dto) {
        //处理异常订单
        OrderErrorHandle orderErrorHandle = new OrderErrorHandle();
        if (dto.getWriteOffStatus() == CommonConstants.ZERO_INT) {
            orderErrorHandle.setHandleAfterStatus(OrderStatusConstants.WAIT_USE);
        } else {
            orderErrorHandle.setHandleAfterStatus(OrderStatusConstants.WAIT_EVALUATE);
        }
        OrderErrorBack orderErrorBack = orderErrorBackMapper.selectById(dto.getId());
        orderErrorHandle.setHandleBeforeStatus(orderErrorBack.getOrderStatus());
        orderErrorHandle.setExceptionOrderId(dto.getId());
        orderErrorHandle.setHandleUserId(dto.getHandleUserId());
        orderErrorHandle.setHandleUserRole(dto.getHandleUserRole());
        orderErrorHandle.setRemark(dto.getRemark());
        orderErrorHandleMapper.insertOrderErrorHandle(orderErrorHandle);
        if (dto.getHandleResult() == 2) {
            dto.setHandleTime(LocalDateTime.now());
        }
        orderErrorBackMapper.updateOrderError(dto);
        return 1;
    }

    @Override
    public OrderErrorBackVO selectOrderErrorById(Integer id) {
        OrderErrorBackVO orderError = orderErrorBackMapper.selectById(id);
        List<String> imgList = orderErrorImgsMapper.selectByOrderErrorId(id);
        orderError.setOrderErrorImgs(imgList);
        return orderError;
    }

    @Override
    public OrderErrorBack queryByOrderIdAndOrigin(String orderId, String origin) {
        return orderErrorBackMapper.queryByOrderIdAndOrigin(orderId, origin);
    }

}
