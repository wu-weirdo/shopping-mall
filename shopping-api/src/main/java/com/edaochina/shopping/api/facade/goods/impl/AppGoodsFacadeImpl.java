package com.edaochina.shopping.api.facade.goods.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.sys.SysDictionaryParamMapper;
import com.edaochina.shopping.api.dao.user.CommunityMapper;
import com.edaochina.shopping.api.dao.user.UserMapper;
import com.edaochina.shopping.api.facade.goods.AppGoodsFacade;
import com.edaochina.shopping.api.service.goods.AppGoodsService;
import com.edaochina.shopping.api.service.goods.GoodsImgsService;
import com.edaochina.shopping.api.service.goods.GoodsTipsService;
import com.edaochina.shopping.api.service.order.AppOrderService;
import com.edaochina.shopping.api.service.sys.SearchHistoryService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.PageHelperUtils;
import com.edaochina.shopping.common.utils.StringUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.constants.MemberTypeStatus;
import com.edaochina.shopping.domain.constants.RedisConstants;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.domain.entity.goods.GoodsImgs;
import com.edaochina.shopping.domain.entity.goods.GoodsTips;
import com.edaochina.shopping.domain.entity.sys.SearchHistory;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import com.edaochina.shopping.domain.entity.user.Community;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.vo.goods.AppCollectGoodVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodDetailVO;
import com.edaochina.shopping.domain.vo.goods.AppGoodsVO;
import com.edaochina.shopping.domain.vo.goods.SysGoodsTipsVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author User
 */
@Service("appGoodsFacadeImpl")
public class AppGoodsFacadeImpl implements AppGoodsFacade {

    private final AppGoodsService appGoodsService;
    private final GoodsImgsService goodsImgsService;
    private final GoodsTipsService goodsTipsService;
    private final AppOrderService orderService;
    private final RedisTemplate redisTemplate;
    private final UserMapper userMapper;
    private final CommunityMapper communityMapper;
    private final SearchHistoryService searchHistoryService;
    @Resource
    SysDictionaryParamMapper sysDictionaryParamMapper;
    private Logger logger = LoggerFactory.getLogger(AppGoodsFacadeImpl.class);
    private volatile boolean isCache = true;

    public AppGoodsFacadeImpl(UserMapper userMapper, CommunityMapper communityMapper,
                              AppGoodsService appGoodsService, GoodsImgsService goodsImgsService,
                              GoodsTipsService goodsTipsService, AppOrderService orderService,
                              RedisTemplate redisTemplate, SearchHistoryService searchHistoryService) {
        this.userMapper = userMapper;
        this.communityMapper = communityMapper;
        this.appGoodsService = appGoodsService;
        this.goodsImgsService = goodsImgsService;
        this.goodsTipsService = goodsTipsService;
        this.orderService = orderService;
        this.redisTemplate = redisTemplate;
        this.searchHistoryService = searchHistoryService;
    }


    /**
     * 获取商品列表
     *
     * @param queryAppGoodsDTO 查询参数
     * @return 商品列表
     */
    @Override
    public PageResult getGoodsList(QueryAppGoodsDTO queryAppGoodsDTO) {
        logger.info("getGoodsList req:" + JSON.toJSONString(queryAppGoodsDTO));
        buildParams(queryAppGoodsDTO);
        if (!isCache) {
            return doGetGoods(queryAppGoodsDTO);
        }
        String key = queryAppGoodsDTO.toString();
        HashOperations<String, String, PageResult<List<AppGoodsVO>>> hashOperations = redisTemplate.opsForHash();
        if (!hashOperations.hasKey(RedisConstants.APP_GOODS_LIST, key)) {
            synchronized (key.intern()) {
                if (!hashOperations.hasKey(RedisConstants.APP_GOODS_LIST, key)) {
                    hashOperations.put(RedisConstants.APP_GOODS_LIST, key, doGetGoods(queryAppGoodsDTO));
                    redisTemplate.expire(RedisConstants.APP_GOODS_LIST, 3, TimeUnit.MINUTES);
                }
            }
        }
        return hashOperations.get(RedisConstants.APP_GOODS_LIST, key);
    }

    @Override
    public boolean hasCache(boolean cache) {
        this.isCache = cache;
        return isCache;
    }

    /**
     * 参数组装
     *
     * @param queryAppGoodsDTO 查询参数
     */
    private void buildParams(QueryAppGoodsDTO queryAppGoodsDTO) {
        checkSearch(queryAppGoodsDTO);
        createSearchHistory(queryAppGoodsDTO);
        initCounty(queryAppGoodsDTO);
        SysDictionaryParam distinctDictionary = sysDictionaryParamMapper.querySysValue("community_range", "community_range");
        Long distinctValue = distinctDictionary == null ? -1L : Long.parseLong(distinctDictionary.getSysValue());
        queryAppGoodsDTO.setMaxDistance(distinctValue);
    }

    /**
     * 查询商品列表
     *
     * @param dto 查询参数
     * @return 商品列表
     */
    private PageResult doGetGoods(QueryAppGoodsDTO dto) {
        PageHelperUtils.setPageHelper(dto.getPages());
        List<AppGoodsVO> goodsList = appGoodsService.getGoodsList(dto);
        return PageHelperUtils.parseToPageResult(goodsList);
    }

    private void checkSearch(QueryAppGoodsDTO queryAppGoodsDTO) {
        if (StringUtil.containsEmoji(queryAppGoodsDTO.getGoodsName())) {
            throw new YidaoException("商品名搜索不得包括Emoji表情!");
        }
    }

    @Override
    public PageResult getMemberGoodsList(QueryAppGoodsDTO queryAppGoodsDTO) {
        logger.info("getGoodsList req:" + JSON.toJSONString(queryAppGoodsDTO));
        createSearchHistory(queryAppGoodsDTO);
        initCounty(queryAppGoodsDTO);
        SysDictionaryParam distinctDictionary = sysDictionaryParamMapper.querySysValue("community_range", "community_range");
        Long distinctValue = distinctDictionary == null ? -1L : Long.parseLong(distinctDictionary.getSysValue());
        queryAppGoodsDTO.setMaxDistance(distinctValue);
        PageHelperUtils.setPageHelper(queryAppGoodsDTO.getPages());
        List<AppGoodsVO> goodsList = appGoodsService.getMemberGoodsList(queryAppGoodsDTO);
        return PageHelperUtils.parseToPageResult(goodsList);
    }

    @Async
    public void createSearchHistory(QueryAppGoodsDTO queryAppGoodsDTO) {
        if (org.springframework.util.StringUtils.isEmpty(queryAppGoodsDTO.getGoodsName()) || queryAppGoodsDTO.getUserId() == null) {
            return;
        }
        SearchHistory history = new SearchHistory();
        history.setCommunityid(queryAppGoodsDTO.getCommunity());
        history.setContent(queryAppGoodsDTO.getGoodsName().trim());
        history.setUid(queryAppGoodsDTO.getUserId());
        searchHistoryService.save(history);
    }

    @Override
    public List<AppGoodsVO> getShopGoodsList(String shopId) {
        logger.info("getShopGoodsList req:" + JSON.toJSONString(shopId));
        return appGoodsService.getShopGoodsList(shopId);
    }

    @Async
    public void initCounty(QueryAppGoodsDTO dto) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", dto.getUserId());
        queryWrapper.eq("member_type", MemberTypeStatus.IS_NOT_MEMBER);
        queryWrapper.isNull("county_code");
        queryWrapper.isNull("city_code");
        queryWrapper.isNull("province_code");
        if (userMapper.selectCount(queryWrapper) > 0) {
            SysUser sysUser = userMapper.getUserById(dto.getUserId());
            Community community = communityMapper.selectById(dto.getCommunity());
            sysUser.bindCommunity(community);
            userMapper.updateById(sysUser);
        }
    }

    /**
     * 获取商品详情
     *
     * @param queryAppGoodsDTO 查询参数
     * @return 商品详情
     */
    @Override
    public AppGoodDetailVO getGoodsDetail(QueryAppGoodsDTO queryAppGoodsDTO) {
        logger.info("getGoodsDetail req:" + JSON.toJSONString(queryAppGoodsDTO));
        logger.info("Longitude ：" + (queryAppGoodsDTO.getLongitude() == null));
        if (queryAppGoodsDTO.getLongitude() == null) {
            queryAppGoodsDTO.setLongitude(0.00);
            queryAppGoodsDTO.setLatitude(0.00);
        }
        AppGoodDetailVO appGoodDetailVO = appGoodsService.queryByGoodsIdAndUserId(queryAppGoodsDTO.getGoodsId(),
                queryAppGoodsDTO.getUserId(), queryAppGoodsDTO.getLongitude(), queryAppGoodsDTO.getLatitude());
        checkAppGoodDetailVO(appGoodDetailVO);
        if (appGoodDetailVO.getCollectedNum() == null) {
            appGoodDetailVO.setCollectedNum(0);
        }
        if (appGoodDetailVO.getBuyNum() == null) {
            appGoodDetailVO.setBuyNum(0);
        }
        // 获取商品图片
        setImages(appGoodDetailVO, queryAppGoodsDTO);
        // 获取商品内容
        setSysGoodsTips(appGoodDetailVO, queryAppGoodsDTO);
        // 3.1 新加内容
        // 判断是否有拼团信息，参与拼团则查询拼团用户头像
        setCollectUserImages(appGoodDetailVO, queryAppGoodsDTO);
        return appGoodDetailVO;
    }

    /**
     * 查询拼团商品
     *
     * @param queryAppGoodsDTO 查询条件
     * @return 拼团商品列表
     */
    @Override
    public PageResult<AppCollectGoodVO> getCollectGoodList(QueryAppGoodsDTO queryAppGoodsDTO) {
        logger.info("getCollectGoodList req:" + JSON.toJSONString(queryAppGoodsDTO));
        if (StringUtils.isBlank(queryAppGoodsDTO.getCommunity())) {
            throw new YidaoException(ReturnData.UNSELECTED_COMMUNITY);
        }
        PageHelperUtils.setPageHelper(queryAppGoodsDTO.getPages());
        return PageHelperUtils.parseToPageResult(appGoodsService.getCollectGoodsList(queryAppGoodsDTO));
    }

    private List<SysGoodsTipsVO> getGoodsTipsList(QueryAppGoodsDTO queryAppGoodsDTO) {
        logger.info("getGoodsTipsList:" + JSON.toJSONString(queryAppGoodsDTO));
        QueryWrapper<GoodsTips> goodsTipsQueryWrapper = new QueryWrapper<>();
        goodsTipsQueryWrapper.eq("goods_id", queryAppGoodsDTO.getGoodsId());
        List<GoodsTips> list = goodsTipsService.list(goodsTipsQueryWrapper);
        try {
            return BeanUtil.listBeanToList(list, SysGoodsTipsVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void checkAppGoodDetailVO(AppGoodDetailVO appGoodDetailVO) {
        if (appGoodDetailVO == null) {
            throw new YidaoException(ReturnData.GOODS_IS_NOT_EXIST);
        }
        logger.info("appGoodDetailVO buyNum:" + appGoodDetailVO.getBuyNum());
        if (appGoodDetailVO.getBuyNum() == null) {
            appGoodDetailVO.setBuyNum(0);
        }
    }


    private void setImages(AppGoodDetailVO appGoodDetailVO, QueryAppGoodsDTO queryAppGoodsDTO) {

        String goodsId = queryAppGoodsDTO.getGoodsId();
        if (appGoodDetailVO.getSupplyChain() == 1) {
            goodsId = appGoodDetailVO.getSupplyChainId();
        }

        QueryWrapper<GoodsImgs> goodsImagesQueryWrapper = new QueryWrapper<>();
        goodsImagesQueryWrapper.eq("goods_id", goodsId);
        List<String> imgList = goodsImgsService.list(goodsImagesQueryWrapper).parallelStream()
                .map(GoodsImgs::getImgUrl)
                .collect(Collectors.toList());
        logger.info("GoodsImages list:" + JSON.toJSONString(imgList));
        appGoodDetailVO.setImgs(imgList);
    }

    private void setSysGoodsTips(AppGoodDetailVO appGoodDetailVO, QueryAppGoodsDTO queryAppGoodsDTO) {
        List<SysGoodsTipsVO> tipsList = getGoodsTipsList(queryAppGoodsDTO);
        appGoodDetailVO.setSysGoodsTipsVOS(tipsList);
    }

    private void setCollectUserImages(AppGoodDetailVO appGoodDetailVO, QueryAppGoodsDTO queryAppGoodsDTO) {
        if (appGoodDetailVO.getCollectFlag() == 1) {
            appGoodDetailVO.setCollectUserImgs(orderService.getUserCollectUserImg(queryAppGoodsDTO.getGoodsId()));
        }
    }

}
