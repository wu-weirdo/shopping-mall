package com.edaochina.shopping.api.facade.goods.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.goods.GoodsCollectInfoMapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.dao.user.SysMerchantCommunityMapper;
import com.edaochina.shopping.api.facade.goods.SysGoodsFacade;
import com.edaochina.shopping.api.service.goods.*;
import com.edaochina.shopping.api.service.sys.SysPayRefundApplyService;
import com.edaochina.shopping.api.service.user.AgentCountyInfoService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.filter.help.JWTThreadLocalHelper;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.ThreadUtils;
import com.edaochina.shopping.common.utils.date.DateUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.base.jwt.JWTBean;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.*;
import com.edaochina.shopping.domain.dto.goods.*;
import com.edaochina.shopping.domain.entity.goods.*;
import com.edaochina.shopping.domain.entity.user.SysMerchantCommunity;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import com.edaochina.shopping.domain.vo.goods.SysGoodsDetailVO;
import com.edaochina.shopping.domain.vo.goods.SysGoodsTipsVO;
import com.edaochina.shopping.domain.vo.goods.SysGoodsVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service("sysGoodsFacadeImpl")
public class SysGoodsFacadeImpl implements SysGoodsFacade {

    private Logger logger = LoggerFactory.getLogger(SysGoodsFacadeImpl.class);

    private final SysGoodsService sysGoodsService;

    private final GoodsImgsService goodsImgsService;

    private final GoodsTipsService goodsTipsService;

    private final SysUserMerchantService sysUserMerchantService;

    private final SysGoodsTypeService goodsTypeService;

    private final SysGoodsSubclassService sysGoodsSubclassService;


    private final AgentCountyInfoService agentCountyInfoService;

    private final GoodsCollectInfoService goodsCollectInfoService;

    @Resource
    GoodsCollectInfoMapper collectInfoMapper;

    private volatile boolean COLLECT_TASK_RUNNING = false;

    private final SysMerchantCommunityMapper sysMerchantCommunityMapper;

    private final RedisTemplate redisTemplate;

    public SysGoodsFacadeImpl(RedisTemplate redisTemplate, OrderMainMapper orderMainMapper, SysPayRefundApplyService sysPayRefundApplyService, SysGoodsService sysGoodsService, GoodsImgsService goodsImgsService, GoodsTipsService goodsTipsService, SysUserMerchantService sysUserMerchantService, SysGoodsTypeService goodsTypeService, SysGoodsSubclassService sysGoodsSubclassService, AgentCountyInfoService agentCountyInfoService, GoodsCollectInfoService goodsCollectInfoService, SysMerchantCommunityMapper sysMerchantCommunityMapper) {
        this.redisTemplate = redisTemplate;
        this.sysGoodsService = sysGoodsService;
        this.goodsImgsService = goodsImgsService;
        this.goodsTipsService = goodsTipsService;
        this.sysUserMerchantService = sysUserMerchantService;
        this.goodsTypeService = goodsTypeService;
        this.sysGoodsSubclassService = sysGoodsSubclassService;
        this.agentCountyInfoService = agentCountyInfoService;
        this.goodsCollectInfoService = goodsCollectInfoService;
        this.sysMerchantCommunityMapper = sysMerchantCommunityMapper;
    }

    /**
     * 更新商品
     *
     * @param goodsDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGoods(GoodsDTO goodsDTO) {
        logger.info("update goods req:" + JSON.toJSONString(goodsDTO));
        // 对添加商品检查
        checkGoods(goodsDTO);
        // 更新商品
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDTO, goods);
        goods.setUpdateId(goodsDTO.getCreateId());

        if (goodsDTO.getLastValidTime() == null) {
            goods.setLastValidTime(DateUtil.date2LocalDateTime(DateUtil.getForeverTime()));
        } else {
            goods.setLastValidTime(goodsDTO.getLastValidTime());
        }
        if (goods.getPutawayTime() == null) {
            goods.setPutawayTime(LocalDateTime.now());
        } else {
            goods.setPutawayTime(goodsDTO.getPutawayTime());
        }

        goods.setUpdateTime(LocalDateTime.now());
        // 设置商品种类名称
        setGoodsTypeName(goodsDTO, goods);
        // 设置商品子类名称
        setGoodsSubclassName(goodsDTO, goods);
        if (StringUtils.isEmpty(goodsDTO.getRoleId()) || !CommonConstants.THREE_STR.equals(goodsDTO.getRoleId())) {
            goods.setShopId(null);
        }
        // TODO 抽取出来
        // 设置拼团信息
        /*if (StringUtils.isNotBlank(goodsDTO.getRoleId()) && !CommonConstants.THREE_STR.equals(goodsDTO.getRoleId()) &&
                goodsDTO.getCollectFlag() != null && 1 == goodsDTO.getCollectFlag()) {*/
        if (goodsDTO.getCollectFlag() != null && 1 == goodsDTO.getCollectFlag()) {
            GoodsCollectInfo goodsCollectInfo = goodsCollectInfoService.getCollectInfoByGoodsId(goodsDTO.getId());
            if (goodsCollectInfo != null && goodsCollectInfo.getCollectStatus() != null && goodsCollectInfo.getCollectStatus() == 1) {
                throw new YidaoException(ReturnData.COLLECT_GOODS_HAS_END);
            }
            // 清除老的拼团信息
            goodsCollectInfoService.removeCollectByGoodsId(goodsDTO.getId());
            GoodsCollectInfo collectInfo = new GoodsCollectInfo();
            collectInfo.setCollectInfo(goodsDTO.getCollectInfo());
            collectInfo.setCollectNum(goodsDTO.getCollectNum());
            collectInfo.setCreateUserId(goodsDTO.getShopId());
            collectInfo.setLastValidTime(goodsDTO.getCollectLastValidTime());
            collectInfo.setGoodsId(goodsDTO.getId());
            collectInfo.setBuyNum(0);
            // 拼团价格添加
            collectInfo.setCollectPrice(goodsDTO.getCollectPrice());
            // 添加新的拼团信息
            goodsCollectInfoService.addCollect(collectInfo);
        } else {
            goods.setCollectFlag(null);
        }
        goods.setFirstShowImg(goodsDTO.getImgs().get(0));
        boolean update = sysGoodsService.updateById(goods);
        if (update) {
            // 删除商品图片
            removeGoodsImgs(goodsDTO);
            // 批量添加商品图片
            insertGoodsImgs(goodsDTO.getImgs(), goodsDTO.getId());
            // 删除商品内容
            removeGoodsTips(goodsDTO);
            // 批量添加商品内容
            insertGoodsTips(goodsDTO.getGoodsTipsDTOs(), goodsDTO.getId());
            redisTemplate.delete(RedisConstants.APP_GOODS_LIST);
        }
        return true;
    }

    /**
     * 添加商品
     *
     * @param goodsDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertGoods(GoodsDTO goodsDTO) {
        // 对添加商品检查
        checkGoods(goodsDTO);
        // 添加商品
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDTO, goods);
        String goodsId = IdUtils.nextId();
        goods.setId(goodsId);
        goods.setCreateId(goodsDTO.getCreateId());
        goods.setCreateTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        // 设置商品种类名称
        setGoodsTypeName(goodsDTO, goods);
        // 设置商品子类名称
        setGoodsSubclassName(goodsDTO, goods);

        if (goodsDTO.getLastValidTime() == null) {
            goods.setLastValidTime(DateUtil.date2LocalDateTime(DateUtil.getForeverTime()));
        } else {
            goods.setLastValidTime(goodsDTO.getLastValidTime());
        }
        if (goods.getPutawayTime() == null) {
            goods.setPutawayTime(LocalDateTime.now());
        } else {
            goods.setPutawayTime(goodsDTO.getPutawayTime());
        }
        if (goodsDTO.getStickWeight() == null) {
            goods.setStickWeight(3);
        }
        // TODO 抽取出来
        // 设置拼团信息
        if (StringUtils.isNotBlank(goodsDTO.getRoleId()) && !CommonConstants.THREE_STR.equals(goodsDTO.getRoleId()) &&
                goodsDTO.getCollectFlag() != null && 1 == goodsDTO.getCollectFlag()) {
            // 清除老的拼团信息
            goodsCollectInfoService.removeCollectByGoodsId(goodsDTO.getId());
            GoodsCollectInfo collectInfo = new GoodsCollectInfo();
            collectInfo.setCollectInfo(goodsDTO.getCollectInfo());
            collectInfo.setCollectNum(goodsDTO.getCollectNum());
            collectInfo.setCreateUserId(goodsDTO.getShopId());
            collectInfo.setLastValidTime(goodsDTO.getCollectLastValidTime());
            collectInfo.setGoodsId(goodsId);
            collectInfo.setBuyNum(0);
            // 拼团价格添加
            collectInfo.setCollectPrice(goodsDTO.getCollectPrice());
            // 添加新的拼团信息
            goodsCollectInfoService.addCollect(collectInfo);
        } else {
            goods.setCollectFlag(null);
        }

        boolean save = sysGoodsService.save(goods);
        sysGoodsService.setQrCode(goodsId);

        if (save) {
            // 批量添加商品图片
            insertGoodsImgs(goodsDTO.getImgs(), goodsId);
            // 批量添加商品内容
            insertGoodsTips(goodsDTO.getGoodsTipsDTOs(), goodsId);
        }
        return true;
    }

    private void checkGoods(GoodsDTO goodsDTO) {
        Goods goods = null;
        if (StringUtils.isNotBlank(goodsDTO.getId())) {
            goods = sysGoodsService.getById(goodsDTO.getId());
            if (goods.getApprovalFlag().equals(ApprovalFlagConstants.ACCEPT) && goods.getCollectFlag() == 1) {
                throw new YidaoException(ReturnData.COLLECT_GOODS_CANNOT_EDIT);
            }
            // 如果已审核通过则改为待审核
            if (goods.getApprovalFlag().equals(ApprovalFlagConstants.ACCEPT) && goods.getHot()) {
                goodsDTO.setApprovalFlag(ApprovalFlagConstants.NO_APPROVAL);
            }
        } else {
            goodsDTO.setStickStatus(0);
        }

        SysUserMerchant merchant = sysUserMerchantService.getById(goodsDTO.getShopId());
        if (merchant == null) {
            throw new YidaoException(ReturnData.GOODS_MERCHANT_NOT_EXIT);
        }

        if (RoleConstants.AGENT_ROLE_STRING.equals(goodsDTO.getRoleId())) {
            List<String> county = agentCountyInfoService.agentCountyStrList(goodsDTO.getCreateId());
            if (!county.contains(merchant.getCountyId())) {
                throw new YidaoException(ReturnData.GOODS_MERCHANT_NOT_BELONG_TO);
            }
        }

        List<SysMerchantCommunity> communities = sysMerchantCommunityMapper.queryCommunityByMerchant(merchant.getId());

        if (communities.size() > 0) {
            for (SysMerchantCommunity community : communities) {
                int n = sysGoodsService.querySticNumByCommuntyId(community.getCommunity());
                // 检查置顶商品数
                if ((goods == null && n >= 3 && goodsDTO.getStickStatus() == 1) ||
                        (goods != null && goods.getStickStatus() == 0 && n >= 3 && goodsDTO.getStickStatus() == 1)) {
                    throw new YidaoException(ReturnData.OVER_GOODS_STICK_NUM.getCode(), ReturnData.OVER_GOODS_STICK_NUM.getMsg());
                }
            }
        }
    }

    /**
     * 批量删除商品
     *
     * @param deleteGoodsDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteGoods(DeleteGoodsDTO deleteGoodsDTO) {
        String ids = deleteGoodsDTO.getIds();
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArr = ids.split(",");
            List<Goods> goods = new ArrayList<>();
            for (String id : idArr) {
                checkPutaway(id);
                Goods goods1 = new Goods();
                goods1.setId(id);
                goods1.setDeleteFlag(DeleteFlagConstants.TRUE);
                goods.add(goods1);
            }

            return sysGoodsService.updateBatchById(goods);
        }
        return false;
    }

    private void checkPutaway(String id) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Goods::getPutawayStatus, 10).eq(Goods::getId, id);
        if (sysGoodsService.count(queryWrapper) > 0) {
            throw new YidaoException("上架中的商品不允许删除!");
        }
    }

    /**
     * 获取商品列表
     *
     * @param queryGoodsDTO
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public PageResult getGoodsList(QueryGoodsDTO queryGoodsDTO) {
        PageHelperUtils.setPageHelper(queryGoodsDTO.getPages());
        JWTBean bean = JWTThreadLocalHelper.get();
        if (bean != null) {
            if (bean.isAgent()) {
                queryGoodsDTO.setAgentId(bean.getId());
            }
            if (bean.isMerchant()) {
                queryGoodsDTO.setShopId(bean.getId());
            }
        }
        List<SysGoodsVO> goodsVOS = sysGoodsService.getSysGoodsList(queryGoodsDTO);
        return PageHelperUtils.parseToPageResult(goodsVOS);
    }

    /**
     * 获取商品详情
     *
     * @param querySysGoodsDTO
     * @return
     */
    @Override
    public SysGoodsDetailVO getGoodsDetail(QuerySysGoodsDTO querySysGoodsDTO) {

        SysGoodsDetailVO sysGoodsDetailVO = new SysGoodsDetailVO();
        Goods goods = sysGoodsService.getById(querySysGoodsDTO.getId());
        BeanUtils.copyProperties(goods, sysGoodsDetailVO);

        if (goods.getUseCountDown() != 10) {
            sysGoodsDetailVO.setUseCountDownTime(null);
        }
        if (goods.getLimitBuyFlag() == 10) {
            sysGoodsDetailVO.setLimitBuyNum(null);
        }

        // 判断是否拼团，如果拼团则设置商品拼团信息
        if (goods.getCollectFlag() == 1) {
            GoodsCollectInfo goodsCollectInfo = goodsCollectInfoService.getCollectInfoByGoodsId(goods.getId());
            if (goodsCollectInfo != null) {
                // 设置商品的拼团信息
                sysGoodsDetailVO.setCollectInfo(goodsCollectInfo.getCollectInfo());
                sysGoodsDetailVO.setCollectLastValidTime(goodsCollectInfo.getLastValidTime());
                sysGoodsDetailVO.setCollectNum(goodsCollectInfo.getCollectNum());
                sysGoodsDetailVO.setCollectPrice(goodsCollectInfo.getCollectPrice());
            }
        }

        // 获取商品图片
        QueryWrapper<GoodsImgs> goodsImgsQueryWrapper = new QueryWrapper<>();
        String goodsId = querySysGoodsDTO.getId();
        if (goods.getSupplyChain() == 1) {
            goodsId = goods.getSupplyChainId();
        }
        goodsImgsQueryWrapper.eq("goods_id", goodsId);
        List<GoodsImgs> goodsImgs = goodsImgsService.list(goodsImgsQueryWrapper);
        List<String> imgs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(goodsImgs)) {
            for (GoodsImgs goodsImg : goodsImgs) {
                imgs.add(goodsImg.getImgUrl());
            }
        }
        sysGoodsDetailVO.setGoodsImgs(imgs);

        // 获取商品内容
        QueryWrapper<GoodsTips> goodsTipsQueryWrapper = new QueryWrapper<>();
        goodsTipsQueryWrapper.eq("goods_id", querySysGoodsDTO.getId());
        List<GoodsTips> list1 = goodsTipsService.list(goodsTipsQueryWrapper);
        try {
            List<SysGoodsTipsVO> sysGoodsTipsVOs = BeanUtil.listBeanToList(list1, SysGoodsTipsVO.class);
            sysGoodsDetailVO.setSysGoodsTipsVOs(sysGoodsTipsVOs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取商户信息
        SysUserMerchant sysUserMerchant = sysUserMerchantService.getById(goods.getShopId());
        if (sysUserMerchant != null) {
            sysGoodsDetailVO.setShopName(sysUserMerchant.getTitle());
        }

        // 获取商品种类
        GoodsType goodsType = goodsTypeService.getById(goods.getGoodsTypeId());
        if (goodsType != null) {
            sysGoodsDetailVO.setGoodsTypeName(goodsType.getTypeName());
        }
        // 获取商品子类
        GoodsSubclass goodsSubclass = sysGoodsSubclassService.getById(goods.getGoodsSubclassId());
        if (goodsSubclass != null) {
            sysGoodsDetailVO.setGoodsSubclassName(goodsSubclass.getSubclassName());
        }
        logger.info(sysGoodsDetailVO.toString());
        return sysGoodsDetailVO;
    }

    /**
     * 审批商品
     *
     * @param approvalGoodsDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approvalGoods(ApprovalGoodsDTO approvalGoodsDTO) {
        redisTemplate.delete(RedisConstants.APP_GOODS_LIST);
        checkApprovalGoods(approvalGoodsDTO.getId(), approvalGoodsDTO);
        sysGoodsService.approvalGoods(approvalGoodsDTO.getId(), approvalGoodsDTO);
        return true;
    }


    @Override
    public void collectOverTimeTask() {
        if (COLLECT_TASK_RUNNING) {
            return;
        }
        COLLECT_TASK_RUNNING = true;
        try {
            int num;
            do {
                List<GoodsCollectInfo> goodsCollectInfos = collectInfoMapper.getOverTimeCollects();
                num = goodsCollectInfos.size();
                for (GoodsCollectInfo goodsCollectInfo : goodsCollectInfos) {
                    if (goodsCollectInfo.getBuyNum() >= goodsCollectInfo.getCollectNum()) {
                        sysGoodsService.updateGoodsToCollectSuccess(goodsCollectInfo);
                    } else {
                        sysGoodsService.updateGoodsToCollectFail(goodsCollectInfo);
                    }
                }
            } while (num == 100);
        } finally {
            COLLECT_TASK_RUNNING = false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean ripening(String goodsId) {
        Goods goods = sysGoodsService.getById(goodsId);
        logger.info(goods.toString());
        checkRipening(goods);
        GoodsCollectInfo collectInfo = goodsCollectInfoService.getCollectInfoByGoodsId(goodsId);
        if (collectInfo == null) {
            throw new YidaoException("该商品无拼团信息!");
        }
        logger.info(collectInfo.toString());
        if (collectInfo.getBuyNum() < collectInfo.getCollectNum()) {
            collectInfo.setCollectNum(collectInfo.getBuyNum());
        }
        collectInfo.setLastValidTime(LocalDateTime.now().plusMinutes(10));
        return goodsCollectInfoService.ripening(collectInfo) == 1;
    }

    private void checkApprovalGoods(String goodsId, ApprovalGoodsDTO dto) {
        if (dto.getApprovalFlag() != 30) {
            return;
        }
        Goods goods = sysGoodsService.getById(goodsId);
        if (goods.getCollectFlag() == 1) {
            GoodsCollectInfo collectInfo = goodsCollectInfoService.getCollectInfoByGoodsId(goodsId);
            if (collectInfo == null) {
                throw new YidaoException(ReturnData.MISS_COLLECT_INFO);
            }
        }
    }

    private void checkRipening(Goods goods) {
        if (goods.getCollectFlag() != 1) {
            throw new YidaoException("该商品不在拼团状态中!");
        }
        if (goods.getApprovalFlag() != 30) {
            throw new YidaoException("该商品未通过审批!");
        }
        if (goods.getPutawayStatus() != 10) {
            throw new YidaoException("该商品未上架!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateGoods(BatchGoodsDTO batchGoodsDTO) {
        // 参数检查
        if (CollectionUtils.isEmpty(batchGoodsDTO.getIds())) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER);
        }
        Collection<Goods> goodsList = sysGoodsService.listByIds(batchGoodsDTO.getIds());
        for (Goods g : goodsList) {
            if (batchGoodsDTO.getPutawayStatus() != null &&
                    (g.getPutawayTime().isAfter(LocalDateTime.now()) || g.getLastValidTime().isBefore(LocalDateTime.now()))) {
                throw new YidaoException("不在上下架时间范围内");
            }
        }
        List<Goods> goods = goodsList.parallelStream().peek(goods1 -> {
            if (batchGoodsDTO.getDeleteFlag() != null) {
                goods1.setDeleteFlag(batchGoodsDTO.getDeleteFlag());
            }
            if (batchGoodsDTO.getPutawayStatus() != null) {
                goods1.setPutawayStatus(batchGoodsDTO.getPutawayStatus());
                if (batchGoodsDTO.getPutawayStatus() == 20) {
                    // 拼团商品下架需要自动退款和取消拼团
                    if (goods1.getCollectFlag() == 1) {
                        GoodsCollectInfo goodsCollectInfo = collectInfoMapper.getCollectInfoByGoodsId(goods1.getId());
                        // 上下架异常修改
                        if (goodsCollectInfo != null) {
                            sysGoodsService.updateGoodsToCollectFail(goodsCollectInfo);
                        }
                        //goods1.setCollectFlag(0);
                    }
                    /*else {
                        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
                        queryWrapper.lambda()
                                .eq(OrderMain::getGoodsId, goods1.getId())
                                .eq(OrderMain::getDeleteFlag, 10)
                                .eq(OrderMain::getStatus, OrderStatusConstants.WAIT_USE);
                        List<OrderMain> orderMains = orderMainMapper.selectList(queryWrapper);
                        orderMains.parallelStream().forEach(orderMain -> {
                            PayRefundApply payRefundApply = new PayRefundApply();
                            payRefundApply.setRefundMethod("商品下架自动退款");
                            payRefundApply.setRefundReason("商品下架");
                            sysPayRefundApplyService.autoRefund(orderMain.getId(), payRefundApply);
                        });
                    }*/
                } else {
                    if (goods1.getCollectFlag() == 1) {
                        GoodsCollectInfo goodsCollectInfo = collectInfoMapper.getCollectInfoByGoodsId(goods1.getId());
                        // 上下架异常修改
                        if (goodsCollectInfo != null && goodsCollectInfo.getCollectStatus() != null && goodsCollectInfo.getCollectStatus() == 1) {
                            throw new YidaoException(ReturnData.COLLECT_GOODS_HAS_END);
                        }
                    }
                }
            }
        }).collect(Collectors.toList());
        return sysGoodsService.updateBatchById(goods);
    }

    @Override
    public boolean batchApprovalGoods(SysBatchApprovalGoodsDTO dto) {
        JWTBean bean = JWTThreadLocalHelper.get();
        ThreadUtils.execute(() -> {
            JWTThreadLocalHelper.put(bean);
            for (String id : dto.getIds()) {
                try {
                    sysGoodsService.approvalGoods(id, dto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            JWTThreadLocalHelper.clear();
        });
        return true;
    }

    /**
     * 删除商品内容
     *
     * @param goodsDTO
     */
    private void removeGoodsTips(GoodsDTO goodsDTO) {
        if (goodsDTO != null && StringUtils.isNotEmpty(goodsDTO.getId())) {
            QueryWrapper<GoodsTips> goodsTipsQueryWrapper = new QueryWrapper<>();
            goodsTipsQueryWrapper.eq("goods_id", goodsDTO.getId());
            goodsTipsService.remove(goodsTipsQueryWrapper);
        }
    }

    /**
     * 删除商品图片
     *
     * @param goodsDTO
     */
    private void removeGoodsImgs(GoodsDTO goodsDTO) {
        if (goodsDTO != null && StringUtils.isNotEmpty(goodsDTO.getId())) {
            goodsImgsService.removeByGoodsId(goodsDTO.getId());
        }
    }

    /**
     * 批量添加商品内容
     *
     * @param goodsTipsDTOs
     */
    private void insertGoodsTips(List<GoodsTipsDTO> goodsTipsDTOs, String goodsId) {
        if (CollectionUtils.isNotEmpty(goodsTipsDTOs)) {
            List<GoodsTips> goodsTipsList = new ArrayList<>();
            for (GoodsTipsDTO goodsTipsDTO : goodsTipsDTOs) {
                GoodsTips goodsTips = new GoodsTips();
                String goodsTipId = IdUtils.nextId();
                goodsTips.setId(goodsTipId);
                goodsTips.setGoodsId(goodsId);
                goodsTips.setContent(goodsTipsDTO.getContent());
                goodsTips.setSort(goodsTipsDTO.getSort());
                goodsTipsList.add(goodsTips);
            }
            goodsTipsService.saveBatch(goodsTipsList);
        }
    }

    /**
     * 批量添加商品图片
     *
     * @param imgs
     */
    private void insertGoodsImgs(List<String> imgs, String goodsId) {
        goodsImgsService.saveBatch(imgs, goodsId);
    }

    private void setGoodsSubclassName(GoodsDTO goodsDTO, Goods goods) {
        if (StringUtils.isNotEmpty(goodsDTO.getGoodsSubclassId())) {
            GoodsSubclass goodsSubclass = sysGoodsSubclassService.getById(goodsDTO.getGoodsSubclassId());
            if (goodsSubclass != null && StringUtils.isNotEmpty(goodsSubclass.getSubclassName())) {
                goods.setGoodsSubclassName(goodsSubclass.getSubclassName());
            }
        }
    }

    private void setGoodsTypeName(GoodsDTO goodsDTO, Goods goods) {
        if (StringUtils.isNotEmpty(goodsDTO.getGoodsTypeId())) {
            GoodsType goodsType = goodsTypeService.getById(goodsDTO.getGoodsTypeId());
            if (goodsType != null && StringUtils.isNotEmpty(goodsType.getTypeName())) {
                goods.setGoodsTypeName(goodsType.getTypeName());
            }
        }
    }

}
