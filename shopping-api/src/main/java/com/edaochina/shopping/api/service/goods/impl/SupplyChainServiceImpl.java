package com.edaochina.shopping.api.service.goods.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.goods.GoodsTypeMapper;
import com.edaochina.shopping.api.dao.goods.SupplyChainDao;
import com.edaochina.shopping.api.service.goods.GoodsCollectInfoService;
import com.edaochina.shopping.api.service.goods.GoodsImgsService;
import com.edaochina.shopping.api.service.goods.SupplyChainService;
import com.edaochina.shopping.api.service.goods.SysGoodsService;
import com.edaochina.shopping.api.service.sys.SysDictionaryParamService;
import com.edaochina.shopping.api.service.trade.ChainRecipientDetailService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.AssertUtils;
import com.edaochina.shopping.common.utils.excel.ExportExcelUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.dto.goods.SupplyChainCopyDTO;
import com.edaochina.shopping.domain.dto.goods.SupplyChainInsertDTO;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.goods.GoodsCollectInfo;
import com.edaochina.shopping.domain.entity.goods.GoodsType;
import com.edaochina.shopping.domain.entity.goods.SupplyChain;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.goods.GoodsImgsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * (SupplyChain)表服务实现类
 *
 * @author wangpenglei
 * @since 2019-11-05 19:00:29
 */
@Service("supplyChainService")
public class SupplyChainServiceImpl extends ServiceImpl<SupplyChainDao, SupplyChain> implements SupplyChainService {

    private final GoodsImgsService goodsImgsService;
    private final SysGoodsService goodsService;
    private final SysUserMerchantService merchantService;
    private final SysDictionaryParamService dictionaryParamService;
    private final GoodsCollectInfoService collectInfoService;
    private final ChainRecipientDetailService chainRecipientDetailService;
    private final GoodsTypeMapper typeMapper;

    public SupplyChainServiceImpl(GoodsImgsService goodsImgsService, SysGoodsService goodsService,
                                  SysUserMerchantService merchantService,
                                  SysDictionaryParamService dictionaryParamService,
                                  GoodsCollectInfoService collectInfoService,
                                  ChainRecipientDetailService chainRecipientDetailService, GoodsTypeMapper typeMapper) {
        this.goodsImgsService = goodsImgsService;
        this.goodsService = goodsService;
        this.merchantService = merchantService;
        this.dictionaryParamService = dictionaryParamService;
        this.collectInfoService = collectInfoService;
        this.chainRecipientDetailService = chainRecipientDetailService;
        this.typeMapper = typeMapper;
    }

    @Override
    public boolean save(SupplyChainInsertDTO dto) {
        String id = IdUtils.nextId();
        dto.setId(id);
        buildGoodsPrice(dto);
        goodsImgsService.saveBatch(dto.getImages(), id);
        return super.save(dto);
    }

    private void buildGoodsPrice(SupplyChain supplyChain) {
        List<SysDictionaryParam> dictionaryParams = dictionaryParamService
                .querySysValueByTypeAndKey("supply_chain", "supply_chain_rate");
        BigDecimal rate = BigDecimal.valueOf(1.1);
        if (dictionaryParams.size() > 0) {
            rate = BigDecimal.valueOf(Double.parseDouble(dictionaryParams.get(0).getSysValue()) / 10000d + 1d);
        }
        AssertUtils.checkArgument(supplyChain.getPurchasePrice().compareTo(supplyChain.getGoodsRetailPrice()) < 0, "采购价需要小于市场价");
        supplyChain.setGoodsPrice(supplyChain.getPurchasePrice().multiply(rate));
    }

    @Override
    public boolean copy(SupplyChainCopyDTO dto) {
        String goodsId = IdUtils.nextId();
        SupplyChain supplyChain = this.getById(dto.getId());
        JWTBean bean = JWTThreadLocalHelper.get();
        SysUserMerchant merchant = merchantService.getById(bean.getId());
        checkCopy(supplyChain, merchant, dto);
        supplyChainIncrease(supplyChain, dto);
        buildCollectInfo(dto, supplyChain, bean, goodsId);
        Goods goods = buildGoods(goodsId, supplyChain, merchant, dto);
        //  添加复用记录
        chainRecipientDetailService.addChainRecipientDetailBySupplyChain(supplyChain, goods);

        // 添加时添加首页图片
        List<GoodsImgsVO> goodsImgsVOS = goodsImgsService.getGoodsImgsList(dto.getId());
        if (goodsImgsVOS.size() > 0) {
            goods.setFirstShowImg(goodsImgsVOS.get(0).getImgUrl());
        }
        return goodsService.save(goods);
    }

    private Goods buildGoods(String goodsId, SupplyChain supplyChain, SysUserMerchant merchant, SupplyChainCopyDTO dto) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(supplyChain, goods);
        goods.setSupplyChain(1);
        goods.setSupplyChainId(supplyChain.getId());
        goods.setGoodsCostPrice(supplyChain.getPurchasePrice());
        goods.setId(goodsId);
        goods.setShopId(merchant.getId());
        goods.setCreateTime(LocalDateTime.now());
        // 复用商品默认限购1件
        /*goods.setLimitBuyFlag(30);
        goods.setLimitBuyNum(1);*/


        goods.setGoodsSurplusNum(dto.getCollectNum());
        goods.setCreateId(merchant.getId());
        goods.setPutawayStatus(20);
        goods.setHot(dto.getHot());
        goods.setCollectFlag(dto.getCollectFlag());
        goods.setGoodsSurplusNum(dto.getCollectNum());
        goodsService.setQrCode(goodsId);
        return goods;
    }

    private void buildCollectInfo(SupplyChainCopyDTO dto, SupplyChain supplyChain, JWTBean bean, String goodsId) {
        GoodsCollectInfo info = new GoodsCollectInfo();
        info.setCollectPrice(supplyChain.getGoodsPrice());
        info.setCreateUserId(bean.getId());
        info.setGoodsId(goodsId);
        BeanUtils.copyProperties(dto, info);
        collectInfoService.addCollect(info);
    }

    private void checkCopy(SupplyChain supplyChain, SysUserMerchant merchant, SupplyChainCopyDTO dto) {
        AssertUtils.checkArgument(dto.getLastValidTime().isAfter(LocalDateTime.now().plusHours(48)),
                "拼团结束时间需要大于当前48小时!");
        AssertUtils.checkNotNull(supplyChain, "没有此供应链商品!");
        AssertUtils.checkArgument(merchant.getLeague() == 1, "非联盟商家不能复用!");
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Goods::getShopId, merchant.getId())
                .eq(Goods::getSupplyChain, 1)
                .eq(Goods::getDeleteFlag, 10)
                .eq(Goods::getSupplyChainId, supplyChain.getId());
        AssertUtils.checkArgument(goodsService.count(queryWrapper) == 0, "你已复用此供应链商品!");
        AssertUtils.checkArgument(dto.getCollectNum() >= supplyChain.getMinimumShipment(), "采购量不得小于最低发货量!");
    }

    /**
     * 同步更新复用次数和总销量
     *
     * @param supplyChain 供应链商品
     * @param dto         复用信息
     */
    private void supplyChainIncrease(SupplyChain supplyChain, SupplyChainCopyDTO dto) {
        String key = supplyChain.getId() + "copyCountIncrease";
        AssertUtils.checkArgument(supplyChain.getGoodsCount() - dto.getCollectNum() >= 0, "库存不足!");
        synchronized (key.intern()) {
            supplyChain.setCopyCount(supplyChain.getCopyCount() + 1);
            supplyChain.setTotalSales(supplyChain.getTotalSales() + dto.getCollectNum());
            supplyChain.setGoodsCount(supplyChain.getGoodsCount() - dto.getCollectNum());
            this.updateById(supplyChain);
        }
    }


    @Override
    public boolean update(SupplyChainInsertDTO dto) {
        if (this.getById(dto.getId()).getCopyCount() > 0 && dto.getState() != 0) {
            throw new YidaoException("已被复用的供应链商品不允许修改!");
        }
        if (dto.getState() != 0) {
            if (dto.getImages() != null) {
                goodsImgsService.removeByGoodsId(dto.getId());
                goodsImgsService.saveBatch(dto.getImages(), dto.getId());
            }
            dto.setUpdateTime(LocalDateTime.now());
            buildGoodsPrice(dto);
        }
        return this.updateById(dto);
    }

    @Override
    public String export(SupplyChain supplyChain) {
        QueryWrapper<SupplyChain> queryWrapper = new QueryWrapper<>(supplyChain);
        queryWrapper.lambda().eq(SupplyChain::getState, 1);
        ExportExcelUtil.Builder builder = ExportExcelUtil.Builder.newInstance()
                .createSheet("供应链导出")
                .createHeader(Arrays.asList("Id", "联系人姓名", "联系人电话", "联系人地址", "采购价格", "库存",
                        "创建时间", "复选次数", "商品名称", "商品详情", "商品种类", "商品规格", "商品门市价",
                        "品牌", "最低发货量", "总销量", "收款人", "收款银行", "卡号", "负责人地址",
                        "负责人名字", "负责人电话"))
                .setFields(Arrays.asList("getId", "getName", "getPhone", "getAddress", "getPurchasePrice",
                        "getGoodsCount", "getCreateTime", "getCopyCount", "getGoodsName", "getGoodsDetail", "getGoodsTypeId",
                        "getGoodsSpec", "getGoodsRetailPrice", "getBrand", "getMinimumShipment",
                        "getTotalSales", "getChamberlain", "getBank", "getCardNumber",
                        "getContactAddress", "getContactName", "getContactPhone"))
                .putTranslateFunction("getGoodsTypeId", id -> Optional.ofNullable(typeMapper.selectById((Serializable) id)).orElse(new GoodsType()).getTypeName());
        List<SupplyChain> supplyChains = this.list(queryWrapper);
        builder.put(supplyChains);
        return builder.export();
    }

}