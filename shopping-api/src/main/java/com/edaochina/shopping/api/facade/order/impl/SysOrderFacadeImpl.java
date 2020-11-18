package com.edaochina.shopping.api.facade.order.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.sys.SysDictionaryParamMapper;
import com.edaochina.shopping.api.facade.order.AppOrderFacade;
import com.edaochina.shopping.api.facade.order.SysOrderFacade;
import com.edaochina.shopping.api.service.goods.AppGoodsService;
import com.edaochina.shopping.api.service.goods.GoodsCollectInfoService;
import com.edaochina.shopping.api.service.goods.SysGoodsService;
import com.edaochina.shopping.api.service.order.SysOrderService;
import com.edaochina.shopping.api.service.sys.SysPayRefundApplyService;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.alioss.AliyunOSSUtil;
import com.edaochina.shopping.common.utils.date.LocalDateUtil;
import com.edaochina.shopping.common.utils.excel.ExcelUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.DeleteFlagConstants;
import com.edaochina.shopping.domain.constants.ExcelConstant;
import com.edaochina.shopping.domain.constants.OrderStatusConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.goods.DeleteOrdersDTO;
import com.edaochina.shopping.domain.dto.order.QuerySysOrderDTO;
import com.edaochina.shopping.domain.dto.order.QuerySysWriteOffOrderDTO;
import com.edaochina.shopping.domain.dto.order.SysOrderDto;
import com.edaochina.shopping.domain.dto.order.WriteOffDTO;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.vo.order.ExportSysOrderVO;
import com.edaochina.shopping.domain.vo.order.ShareOrderDetail;
import com.edaochina.shopping.domain.vo.order.SysOrderDetailVO;
import com.edaochina.shopping.domain.vo.order.SysOrderVO;
import com.edaochina.shopping.domain.vo.trade.SysShareProfitVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.edaochina.shopping.api.facade.order.impl.AppOrderFacadeImpl.getUseLastTime;


@Service("sysOrderFacadeImpl")
public class SysOrderFacadeImpl implements SysOrderFacade {

    private Logger logger = LoggerFactory.getLogger(SysOrderFacadeImpl.class);

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private SysGoodsService sysGoodsService;

    @Autowired
    private AppGoodsService appGoodsService;

    @Autowired
    private AppOrderFacade appOrderFacade;

    @Autowired
    private GoodsCollectInfoService goodsCollectInfoService;

    @Resource
    private SysDictionaryParamMapper sysDictionaryParamMapper;

    private final SysPayRefundApplyService refundApplyService;

    public SysOrderFacadeImpl(SysPayRefundApplyService refundApplyService) {
        this.refundApplyService = refundApplyService;
    }

    /**
     * 批量删除订单
     *
     * @param deleteOrdersDTO
     * @return
     */
    @Override
    public boolean deleteOrders(DeleteOrdersDTO deleteOrdersDTO) {
        String ids = deleteOrdersDTO.getIds();
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArr = ids.split(",");
            List<OrderMain> orderMains = new ArrayList<>();
            for (String id : idArr) {
                OrderMain orderMain = new OrderMain();
                orderMain.setId(id);
                orderMain.setDeleteFlag(DeleteFlagConstants.TRUE);
                orderMains.add(orderMain);
            }
            return sysOrderService.updateBatchById(orderMains);
        }
        return false;
    }

    @Override
    public int refund(PayRefundApply payRefundApply) {

        return refundApplyService.autoRefund(payRefundApply.getOrderId(), payRefundApply);
    }


    /**
     * 核销
     *
     * @param writeOffDTO
     * @return
     */
    @Override
    public boolean writeOff(WriteOffDTO writeOffDTO) {
        // 公共代码提取
        return appOrderFacade.writeOff(writeOffDTO);
    }

    /**
     * 查询订单列表
     *
     * @param querySysOrderDTO
     * @return
     */
    @Override
    public PageResult getOrderList(SysOrderDto querySysOrderDTO) {
        PageHelperUtils.setPageHelper(querySysOrderDTO.getPages());
        JWTBean bean = JWTThreadLocalHelper.get();
        if (bean.isAgent()) {
            querySysOrderDTO.setAgentId(bean.getId());
        }
        if (bean.isMerchant()) {
            querySysOrderDTO.setShopId(bean.getId());
        }
        List<SysOrderVO> sysOrderVOS = sysOrderService.sysQueryOrders(querySysOrderDTO);
        return PageHelperUtils.parseToPageResult(sysOrderVOS);

    }

    /**
     * 查询获取核销列表   状态为待使用、待评价、已评价
     *
     * @param querySysOrderDTO
     * @return
     */
    @Override
    public PageResult getWriteOffList(QuerySysWriteOffOrderDTO querySysOrderDTO) {
        //  sql 优化改造
        PageHelperUtils.setPageHelper(querySysOrderDTO.getPages());
        List<OrderMain> orderMains = sysOrderService.getWriteOffList(querySysOrderDTO);
        return PageHelperUtils.parseToPageResult(orderMains);
    }


    /**
     * 获取订单详情
     *
     * @param querySysOrderDTO
     * @return
     */
    @Override
    public SysOrderDetailVO getOrderDetail(QuerySysOrderDTO querySysOrderDTO) {
        SysOrderDetailVO sysOrderDetailVO = new SysOrderDetailVO();
        OrderMain order = sysOrderService.getById(querySysOrderDTO.getId());
        BeanUtils.copyProperties(order, sysOrderDetailVO);
        Goods goods = sysGoodsService.getById(order.getGoodsId());
        if (goods != null) {
            sysOrderDetailVO.setGoodsTypeName(goods.getGoodsTypeName());
            sysOrderDetailVO.setGoodsDetail(goods.getGoodsDetail());
            sysOrderDetailVO.setLimitBuyNum(goods.getLimitBuyNum());
            sysOrderDetailVO.setUseLastValidTime(getUseLastTime(goods, order.getCreateTime()));
        }
        return sysOrderDetailVO;
    }

    /**
     * 处理未支付的订单，并修改状态
     *
     * @return
     */
    @Override
    public boolean updateCollectOrderStatusForTask() {
        LoggerUtils.info(SysOrderFacadeImpl.class, "开始处理未支付的订单，并修改状态");
        // 获取15分钟未支付的单子
        Date date1 = DateUtils.addMinutes(new Date(), -30);
        QueryWrapper<OrderMain> orderMainQueryWrapper = new QueryWrapper<>();
        orderMainQueryWrapper.in("status", OrderStatusConstants.WAIT_PAY);
        orderMainQueryWrapper.eq("delete_flag", DeleteFlagConstants.FALSE);
        orderMainQueryWrapper.le("create_time", date1);
        List<OrderMain> list = sysOrderService.list(orderMainQueryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OrderMain orderMain : list) {
                OrderMain order = new OrderMain();
                order.setId(orderMain.getId());
                order.setStatus(OrderStatusConstants.PAY_FAIL);
                order.setDeleteFlag(DeleteFlagConstants.TRUE);
                sysOrderService.updateById(order);
                // 修改商品剩余数量
                // 修改商品的已购数量
                appGoodsService.updateGoodsSurplusNumAndOrderNum(orderMain.getGoodsId(), -orderMain.getGoodsNum(), -1);
                //  判断是否是拼团订单，如果是则减少拼团数量
                goodsCollectInfoService.addBuyNum(orderMain.getGoodsId(), -orderMain.getGoodsNum());
            }
        }
        return false;
    }

    /**
     * 导出订单
     *
     * @param sysOrderDto
     * @return
     */
    @Override
    public String exportOrder(SysOrderDto sysOrderDto) {
        List<ExportSysOrderVO> list = getExportSysOrderList(sysOrderDto);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ExcelUtil.exportDataToExcel(list, ExcelConstant.ORDER_HEADERS, ExcelConstant.TITLE, outputStream);

            String fileName = IdUtils.nextId() + ".xls";
            byte[] buff = outputStream.toByteArray();
            for (int i = 0; i < buff.length; i++) {
                ByteArrayInputStream bin = new ByteArrayInputStream(buff);
                if (!org.apache.commons.lang3.StringUtils.isEmpty(fileName)) {
                    String[] index = fileName.split("\\.");
                    if (index.length > 0) {
                        String key = LocalDateUtil.localDateTime2String(LocalDateTime.now()) + "/" + IdUtils.nextId() + "." + index[index.length - 1];
                        AliyunOSSUtil.uploadFile(bin, key, bin.available());
                        return AliyunOSSUtil.imgUrl + "/" + key;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public PageResult<SysShareProfitVo> getShareOrder(SysShareGoodsProfitDTO dto) {
        JWTBean jwtBean = JWTThreadLocalHelper.get();
        String role = jwtBean.getRole();
        String id = jwtBean.getId();
        // 设置查询的角色以及id
        if (!RoleConstants.ADMIN_ROLE_STRING.equals(role)) {
            dto.setUserId(id);
            dto.setRoleId(role);
        }
        PageHelperUtils.setPageHelper(dto.getPages());
        PageHelperUtils.setPageHelper(dto.getPages());
        return PageHelperUtils.parseToPageResult(sysOrderService.getShareOrder(dto));
    }

    @Override
    public ShareOrderDetail getShareOrderDetail(String id) {
        OrderMain orderMain = sysOrderService.getById(id);
        if (orderMain == null) {
            return null;
        }

        SysDictionaryParam merchantRate = sysDictionaryParamMapper.querySysValue("share_goods", "merchant_profit_rate");
        BigDecimal merchantProfitRate = new BigDecimal(merchantRate.getSysValue()).divide(new BigDecimal(10000));
        // 代理商分润比例
        SysDictionaryParam agentRate = sysDictionaryParamMapper.querySysValue("share_goods", "agent_profit_rate");
        BigDecimal agentProfitRate = new BigDecimal(agentRate.getSysValue()).divide(new BigDecimal(10000));

        ShareOrderDetail shareOrderDetail = new ShareOrderDetail();
        shareOrderDetail.setBuyNum(orderMain.getGoodsNum());
        shareOrderDetail.setCreateTime(orderMain.getCreateTime());
        shareOrderDetail.setGoodsId(orderMain.getGoodsId());
        shareOrderDetail.setGoodsName(orderMain.getGoodsName());
        shareOrderDetail.setMerchantId(orderMain.getShoperId());
        shareOrderDetail.setMerchantName(orderMain.getShoperName());
        shareOrderDetail.setShareMerchantId(orderMain.getShareMerchantId());
        shareOrderDetail.setShareRate(orderMain.getShareRate());
        shareOrderDetail.setOrderPrice(orderMain.getActualPrice());
        shareOrderDetail.setStatus(orderMain.getStatus());
        shareOrderDetail.setOrderId(orderMain.getId());

        BigDecimal shareFee = orderMain.getTotalPrice().multiply(orderMain.getShareRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
        StringBuilder freeDetail = new StringBuilder("推广人：");
        BigDecimal merchantProfit = shareFee.multiply(merchantProfitRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal agentProfit = shareFee.multiply(agentProfitRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        freeDetail.append(merchantProfit.doubleValue()).append("   合伙人：");
        freeDetail.append(agentProfit.doubleValue()).append("  总部：");
        freeDetail.append(shareFee
                .subtract(merchantProfit)
                .subtract(agentProfit)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        shareOrderDetail.setShareFeeDetail(freeDetail.toString());
        return shareOrderDetail;
    }

    private List<ExportSysOrderVO> getExportSysOrderList(SysOrderDto sysOrderDto) {
        List<ExportSysOrderVO> exportOrderList = new ArrayList<>();
        if (sysOrderDto.getPages() == null) {
            sysOrderDto.setPages(new Pages());
        }
        sysOrderDto.getPages().setPageIndex(1);
        sysOrderDto.getPages().setPageSize(300);
        PageHelperUtils.setPageHelper(sysOrderDto.getPages());
        List<ExportSysOrderVO> exportOrderListSub = sysOrderService.getExportOrderList(sysOrderDto);
        while (exportOrderListSub.size() > 0) {
            exportOrderList.addAll(exportOrderListSub);
            sysOrderDto.getPages().incrementPageIndex();
            PageHelperUtils.setPageHelper(sysOrderDto.getPages());
            exportOrderListSub = sysOrderService.getExportOrderList(sysOrderDto);
            if (exportOrderList.size() > 10000) {
                break;
            }
        }
        return exportOrderList;
    }

}
