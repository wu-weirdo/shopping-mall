package com.edaochina.shopping.api.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.user.*;
import com.edaochina.shopping.api.service.goods.SysGoodsService;
import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.api.service.user.AccountService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.*;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.constants.CommonConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.user.*;
import com.edaochina.shopping.domain.entity.goods.Goods;
import com.edaochina.shopping.domain.entity.user.*;
import com.edaochina.shopping.domain.vo.sys.SysHasAgenAreaVo;
import com.edaochina.shopping.domain.vo.user.MerchantVO;
import com.edaochina.shopping.domain.vo.user.SysUserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商户表 by 张志侃 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Service
public class SysUserMerchantServiceImpl extends ServiceImpl<SysUserMerchantMapper, SysUserMerchant> implements SysUserMerchantService {

    private static Logger logger = LoggerFactory.getLogger(SysUserMerchantServiceImpl.class);
    @Resource
    private SysUserMerchantMapper merchantMapper;
    @Resource
    private CommunityMapper communityMapper;
    @Resource
    private SysMerchantImageMapper merchantImageMapper;

    @Resource
    private SysMerchantCommunityMapper merchantCommunityMapper;

    @Resource
    private SysGoodsService sysGoodsService;

    private final AccountService accountService;

    @Autowired
    private SysCommunityPartnerMapper sysCommunityPartnerMapper;

    private final SysAddressMapper addressMapper;
    @Resource
    private CommonService commonService;

    public SysUserMerchantServiceImpl(SysAddressMapper addressMapper, AccountService accountService) {
        this.addressMapper = addressMapper;
        this.accountService = accountService;
    }

    @Override
    public AjaxResult merchantRegister(MerchantRegDTO dto) {
        // 检查商户
        checkMerchant(dto);
        SysUserMerchant merchant = new SysUserMerchant();
        BeanUtils.copyProperties(dto, merchant);
        String id = IdUtils.nextId();
        merchant.setId(id);
        //处理特殊字符
        merchant.setName(CharUtils.decodeby10(dto.getName()));
        merchant.setStatus(CommonConstants.ZERO_INT);
        merchant.setCreateTime(LocalDateTime.now());
        merchant.setUpdateTime(LocalDateTime.now());
        String salt = CodeUtil.makeAuthCodeSix();
        if (StringUtils.isNotBlank(dto.getCommunityUserId())) {
            SysCommunityPartner sysCommunityPartner = sysCommunityPartnerMapper.selectByPrimaryKey(dto.getCommunityUserId());
            merchant.setInvitatCode(sysCommunityPartner.getInvitatCode());
        }
        String pwd = "";
        try {
            pwd = SignUtils.generateSign(dto.getPassword() + salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        merchant.setSalt(salt);
        merchant.setPassword(pwd);
        int i = merchantMapper.insert(merchant);
        this.addMerchantImage(id, dto.getImage(), dto.getBusinssImage(), dto.getFoodImage());
        for (String community : dto.getCommunity().split(",")) {
            merchantCommunityMapper.insertMerchantCommunity(id, community);
        }
        if (i == 1) {
            accountService.saveWithType(Account.copy2Account(merchant), RoleConstants.MERCHANT_ROLE, id);
            // 添加商户的二维码
            getQrCodeByMerchantId(id);
            return BaseResult.successResult(id);
        }
        return BaseResult.failedResult();
    }

    private void checkMerchant(MerchantRegDTO dto) {
        // 检查商户有无注册过
        if (accountService.exist(dto.getAccount())) {
            throw new YidaoException(ReturnData.USERNAME_EXIST);
        }
        if (!VerifyUtils.isIdentityNo(dto.getIdentityNo())) {
            throw new YidaoException(ReturnData.IDENTITY_NO_ERROR.getCode(), ReturnData.IDENTITY_NO_ERROR.getMsg());
        }
        if (!VerifyUtils.isPhone(dto.getPhone())) {
            throw new YidaoException(ReturnData.PHONE_ERROR.getCode(), ReturnData.PHONE_ERROR.getMsg());
        }
    }


    @Override
    public SysUserVO merchantCheck(LoginDTO dto) {
        QueryWrapper<SysUserMerchant> wrapper = new QueryWrapper<>();
        wrapper.eq("account", dto.getAccount());
        wrapper.ne("status", 2);
        SysUserMerchant merchant = this.getOne(wrapper);
        if (merchant == null || StringUtils.isBlank(merchant.getId())) {
            throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }
        /* 密码加密 */
        String mdPwd;
        try {
            mdPwd = SignUtils.generateSign(dto.getPassword() + merchant.getSalt());
        } catch (NoSuchAlgorithmException e) {
            LoggerUtils.error(SysUserAgentServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.MD5_ERROR);
        }
        if (!merchant.getPassword().equals(mdPwd)) {
            throw new YidaoException(ReturnData.ACCOUNT_OR_PASSWORD_WRONG);
        }
      /*  if (!dto.getOpenid().isEmpty()){
            merchantMapper.updateOpenid(dto.getAccount(),dto.getOpenid());
        }*/
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(merchant, sysUserVO);
        return sysUserVO;
    }

    @Override
    public PageResult<MerchantVO> merchantList(MerchantDTO merchantDTO) {
        int n = merchantMapper.merchantCount(merchantDTO);
        Pages pages = merchantDTO.getPages();
        pages.setTotal(n);
        List<MerchantVO> vos = merchantMapper.merchantList(merchantDTO);
        Set<String> communityIds = new HashSet<>();
        vos.forEach(merchantVO -> {
            if (StringUtils.isNotBlank(merchantVO.getCommunity())) {
                communityIds.addAll(Arrays.asList(merchantVO.getCommunity().split(",")));
            }
            // 商品数查询
            int num = sysGoodsService.queryMerchantPutawayNum(merchantVO.getId());
            merchantVO.setGoodsCount(num);
        });
        if (communityIds.size() > 0) {
            Map<String, Community> communityMap = communityMapper.selectBatchIds(communityIds).parallelStream().collect(Collectors.toMap(Community::getId, community -> community));
            vos.forEach(merchantVO -> {
                if (StringUtils.isNotBlank(merchantVO.getCommunity())) {
                    List<Community> communities = Arrays.asList(merchantVO.getCommunity().split(",")).parallelStream().map(communityMap::get).collect(Collectors.toList());
                    merchantVO.setCommunities(communities);
                }
            });
        }
        return PageUtil.getPageResult(vos, pages);
    }

    private void putCommunities(MerchantVO merchant) {
        Set<String> communityIds = merchantCommunityMapper.queryCommunityByMerchant(merchant.getId()).parallelStream()
                .map(SysMerchantCommunity::getCommunity)
                .collect(Collectors.toSet());
        List<Community> communityVOS = new ArrayList<>();
        if (communityIds.size() > 0) {
            communityVOS = communityMapper.selectBatchIds(communityIds);
        }
        merchant.setCommunities(communityVOS);
    }

    @Override
    public AjaxResultGeneric<Boolean> quitLeague(String id) {
        SysUserMerchant merchant = this.getById(id);
        checkQuitLeague(merchant);
        merchant.setLeague(0);
        return BaseResult.successGenericResult(this.updateById(merchant));
    }

    private void checkQuitLeague(SysUserMerchant merchant) {
        if (merchant == null) {
            throw new YidaoException("没有此商家!");
        }
        if (merchant.getLeague() == 0) {
            throw new YidaoException("该商家不是联盟商家!");
        }
    }

    @Override
    public AjaxResultGeneric<Boolean> league(String id) {
        SysUserMerchant merchant = this.getById(id);
        checkLeague(merchant);
        merchant.setLeague(1);
        return BaseResult.successGenericResult(this.updateById(merchant));
    }

    private void checkLeague(SysUserMerchant merchant) {
        if (merchant == null) {
            throw new YidaoException("没有此商家!");
        }
        if (merchant.getLeague() == 1) {
            throw new YidaoException("该商家已加入联盟商家!");
        }
    }

    @Override
    public AjaxResult merchantDetail(String id) {
        SysUserMerchant merchant = merchantMapper.selectById(id);
        if (merchant != null) {
            MerchantVO merchantVO = new MerchantVO();
            BeanUtils.copyProperties(merchant, merchantVO);
            //获取门店图片
            List<SysMerchantImage> images = getMerchantImage(id);
            merchantVO.setImages(images);
            // 商品数查询
            int num = sysGoodsService.queryMerchantPutawayNum(id);
            merchantVO.setGoodsCount(num);

            if (images.size() > 0) {
                merchantVO.setBusinssImage(images.get(0).getBusinssImage());
                merchantVO.setFoodImage(images.get(0).getFoodImage());
            }
            putCommunities(merchantVO);
            return BaseResult.successResult(merchantVO);
        }
        return BaseResult.failedResult(ReturnData.USER_NOT_EXIST);
    }

    /**
     * 获取门店图片
     */
    private List<SysMerchantImage> getMerchantImage(String merchantId) {
        QueryWrapper<SysMerchantImage> imageWrapper = new QueryWrapper<>();
        imageWrapper.eq("merchant_id", merchantId);
        return merchantImageMapper.selectList(imageWrapper);
    }

    /**
     * 插入门头照片
     */
    private void addMerchantImage(String merchantId, String url, String bussinsUrl, String foodUrl) {
        String[] result = url.split(",");
        for (String s : result) {
            SysMerchantImage image = new SysMerchantImage();
            image.setId(IdUtils.nextId());
            image.setMerchantId(merchantId);
            image.setImage(s);
            image.setCreateTime(LocalDateTime.now());
            image.setUpdateTime(LocalDateTime.now());
            image.setBusinssImage(bussinsUrl);
            image.setFoodImage(foodUrl);
            merchantImageMapper.insert(image);
        }
    }

    @Override
    public AjaxResult merchantUpdate(UpdMerchantDTO dto) {
        //修改轮播图
        if (StringUtils.isNotBlank(dto.getImage())) {
            QueryWrapper<SysMerchantImage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("merchant_id", dto.getId());
            merchantImageMapper.delete(queryWrapper);
            this.addMerchantImage(dto.getId(), dto.getImage(), dto.getBusinssImage(), dto.getFoodImage());
        }
        if (!VerifyUtils.isIdentityNo(dto.getIdentityNo())) {
            throw new YidaoException(ReturnData.IDENTITY_NO_ERROR.getCode(), ReturnData.IDENTITY_NO_ERROR.getMsg());
        }
        if (!VerifyUtils.isPhone(dto.getPhone())) {
            throw new YidaoException(ReturnData.PHONE_ERROR.getCode(), ReturnData.PHONE_ERROR.getMsg());
        }
        SysUserMerchant merchant = new SysUserMerchant();
        BeanUtils.copyProperties(dto, merchant);
        // 设置密码和加盐
        if (StringUtils.isNotBlank(dto.getPassword())) {
            String salt = CodeUtil.makeAuthCodeSix();
            String pwd = dto.getPassword();
            try {
                pwd = SignUtils.generateSign(dto.getPassword() + salt);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            merchant.setSalt(salt);
            merchant.setPassword(pwd);
        } else {
            merchant.setPassword(null);
        }
        // 更新商家小区信息
        merchantCommunityMapper.deleteMerchantCommunity(dto.getId());
        for (String community : dto.getCommunitys()) {
            merchantCommunityMapper.insertMerchantCommunity(dto.getId(), community);
        }
        merchant.setUpdateTime(LocalDateTime.now());
        if (merchantMapper.updateById(merchant) == 1) {
            return BaseResult.successResult();
        }
        return BaseResult.failedResult(ReturnData.UPD_USER_ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteMerchant(UpdMerchantDTO dto) {
        if (merchantMapper.updateMerchant(dto) > 0) {
            accountService.selectOne(dto.getId(), RoleConstants.MERCHANT_ROLE_STRING).ifPresent(accountVO -> {
                accountVO.setStatus(dto.getStatus());
                accountService.updateById(accountVO);
            });
            // 删除商家对应的商品
            Goods goods = new Goods();
            goods.setDeleteFlag(20);
            UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("shop_id", dto.getId());
            sysGoodsService.update(goods, updateWrapper);
            merchantCommunityMapper.updateMerchantCommunity(dto.getId());
        }
        return BaseResult.successResult();
    }

    @Override
    public AjaxResult appMerchantRegister(AppMerchantRegDTO dto) {
        // 去除空格
        dto.setAccount(dto.getAccount().trim());
        dto.setPassword(dto.getPassword().trim());
        Community community = communityMapper.selectById(dto.getCommunity());
        if (community == null) {
            return BaseResult.failedResult(ReturnData.COMM_NOT_EXIT_ERROR.getCode(), ReturnData.COMM_NOT_EXIT_ERROR.getMsg());
        }

        MerchantRegDTO merchantRegDTO = new MerchantRegDTO();
        // 校验 invitatCode
        if (StringUtils.isNotBlank(dto.getInvitatCode())) {
            int n = sysCommunityPartnerMapper.checkInvitatCodeAndCounty(dto.getInvitatCode(), dto.getCountyId());
            if (n == 0) {
                throw new YidaoException(ReturnData.IDENTITY_CODE_ERROR);
            }
            SysCommunityPartner sysCommunityPartner = sysCommunityPartnerMapper.queryByInvitatCode(dto.getInvitatCode());
            merchantRegDTO.setCommunityUserId(sysCommunityPartner.getId());
        }


        merchantRegDTO.setMemberType(0);
        BeanUtils.copyProperties(dto, merchantRegDTO);
        // 一小区的经纬度为准
        merchantRegDTO.setLongitude(community.getLongitude());
        merchantRegDTO.setLatitude(community.getLatitude());
        merchantRegDTO.setStatus(2);
        return merchantRegister(merchantRegDTO);
    }

    @Override
    public Integer queryMemberNumByCountId(String countyId) {
        return merchantMapper.queryMemberNumByCountId(countyId);
    }

    @Override
    public List<SysUserMerchant> querySysUserMerchantByCommunityId(String community) {
        QueryWrapper<SysUserMerchant> sysUserMerchantQueryWrapper = new QueryWrapper<>();
        sysUserMerchantQueryWrapper.like("community", community);
        sysUserMerchantQueryWrapper.ne("status", 2);
        return this.list(sysUserMerchantQueryWrapper);
    }

    /**
     * 通过商家id获取商家提货相应的二维码地址
     * (由于涉及到生成图片下载到上传过程比较长，采用异步方式)
     *
     * @param merchantId 商家id
     */
    @Override
    public void getQrCodeByMerchantId(String merchantId) {
        ThreadUtils.execute(() -> {
            String imageUrl = commonService.getMerchantQrImage(merchantId);
            if (imageUrl != null) {
                // 更新到商户的qr属性中
                SysUserMerchant userMerchant = new SysUserMerchant();
                userMerchant.setId(merchantId);
                imageUrl = "https" + imageUrl.substring(4);
                userMerchant.setQrCode(imageUrl);
                updateById(userMerchant);
            }
        });
    }

    @Override
    public List<SysUserMerchant> queryNoQrCode() {
        return merchantMapper.queryNoQrCode();
    }

    @Override
    public List<SysUserMerchant> selectByCommunity(List<String> community) {
        QueryWrapper<SysMerchantCommunity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("community", community);
        Set<String> ids = merchantCommunityMapper.selectList(queryWrapper).parallelStream()
                .map(SysMerchantCommunity::getMerchantId).collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return merchantMapper.selectBatchIds(ids);
    }

    @Override
    public SysHasAgenAreaVo selectAddress(String countyId) {
        return SysHasAgenAreaVo.of(addressMapper.selectTreeByMinCode(countyId));
    }

    @Override
    public void updateUserLawId(String userId, String data) {
        merchantMapper.updateUserLawId(userId, data);
    }

    @Override
    public int addProfitMoney(String shoperId, BigDecimal totalIncome) {
        return merchantMapper.addProfitMoney(shoperId, totalIncome.doubleValue());
    }
}
