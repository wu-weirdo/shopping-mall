package base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.goods.GoodsMapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.dao.order.PayRefundApplyMapper;
import com.edaochina.shopping.api.dao.user.CommunityMapper;
import com.edaochina.shopping.api.dao.user.SysUserMerchantMapper;
import com.edaochina.shopping.api.dao.user.UserMapper;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;

import java.math.BigDecimal;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 生产假数据
 * @since : 2019/7/1 16:15
 */
public class Fake {

    private final PayRefundApplyMapper payRefundApplyMapper;

    private final OrderMainMapper orderMainMapper;

    private final SysUserMerchantMapper merchantMapper;

    private final UserMapper userMapper;

    private final GoodsMapper goodsMapper;

    private final CommunityMapper communityMapper;

    private SysUserMerchant sysUserMerchant;
    private Goods goods;
    private SysUser user;
    private String orderMainId;
    private String praPhone;


    public Fake(PayRefundApplyMapper payRefundApplyMapper, OrderMainMapper orderMainMapper,
                SysUserMerchantMapper merchantMapper, UserMapper userMapper, GoodsMapper goodsMapper, CommunityMapper communityMapper) {
        this.payRefundApplyMapper = payRefundApplyMapper;
        this.orderMainMapper = orderMainMapper;
        this.merchantMapper = merchantMapper;
        this.userMapper = userMapper;
        this.goodsMapper = goodsMapper;
        this.communityMapper = communityMapper;
        sysUserMerchant = fakeShoper();
        goods = fakeGoods();
        user = fakeUser();
    }

    public void fakeOrderMain() {
        OrderMain orderMain = new OrderMain();
        orderMainId = IdUtils.nextId();
        orderMain.setId(orderMainId);
        orderMain.setOrderNo(orderMainId);
        orderMain.setShoperId(sysUserMerchant.getId());
        orderMain.setShoperName(sysUserMerchant.getName());
        orderMain.setShoperAddress(sysUserMerchant.getAddress());
        orderMain.setGoodsId(goods.getId());
        orderMain.setGoodsName(goods.getGoodsName());
        orderMain.setGoodsNum(1);
        orderMain.setUserId(user.getId());
        orderMain.setUserName(user.getName());
        orderMain.setUserAddress(user.getUserCityAddress());
        orderMain.setActualPrice(new BigDecimal(6));
        orderMain.setCostPrice(new BigDecimal(0));
        orderMain.setTotalPrice(new BigDecimal(6));
        orderMain.setPhone(user.getPhone());
        orderMain.setStatus(20);
        orderMainMapper.insert(orderMain);
    }

    public SysUserMerchant fakeShoper() {
        return merchantMapper.selectList(new QueryWrapper<>()).stream().findAny().orElse(new SysUserMerchant());
    }

    public Goods fakeGoods() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", sysUserMerchant.getId());
        return goodsMapper.selectList(queryWrapper).stream().findAny().orElse(new Goods());
    }

    public SysUser fakeUser() {
        return userMapper.selectList(new QueryWrapper<>()).stream().findAny().orElse(new SysUser());
    }

    public void fakePayRefundApply() {
        fakeOrderMain();
        PayRefundApply payRefundApply = new PayRefundApply();
        praPhone = IdUtils.nextId(11);
        payRefundApply.setOrderId(orderMainId);
        payRefundApply.setUserId(user.getId());
        payRefundApply.setUserPhone(praPhone);
        payRefundApply.setShoperId(sysUserMerchant.getId());
        payRefundApplyMapper.insert(payRefundApply);
    }

    public PayRefundApply getFakePayRefundApply() {
        QueryWrapper<PayRefundApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", praPhone);
        return payRefundApplyMapper.selectOne(queryWrapper);
    }

    public OrderMain getFakeOrderMain() {
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderMainId);
        return orderMainMapper.selectOne(queryWrapper);
    }

    public SysUserMerchant getSysUserMerchant() {
        return sysUserMerchant;
    }

    public Goods getGoods() {
        return goods;
    }

    public SysUser getUser() {
        return user;
    }

    public PayRefundApplyMapper getPayRefundApplyMapper() {
        return payRefundApplyMapper;
    }

    public OrderMainMapper getOrderMainMapper() {
        return orderMainMapper;
    }

    public SysUserMerchantMapper getMerchantMapper() {
        return merchantMapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public GoodsMapper getGoodsMapper() {
        return goodsMapper;
    }

    public CommunityMapper getCommunityMapper() {
        return communityMapper;
    }
}
