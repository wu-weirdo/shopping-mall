package com.edaochina.shopping.api.facade.order.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edaochina.shopping.api.facade.order.ApprovalRecordFacade;
import com.edaochina.shopping.api.service.goods.GoodsImgsService;
import com.edaochina.shopping.api.service.goods.SysGoodsSubclassService;
import com.edaochina.shopping.api.service.goods.SysGoodsTypeService;
import com.edaochina.shopping.api.service.order.ApprovalRecordService;
import com.edaochina.shopping.api.service.user.AgentCountyInfoService;
import com.edaochina.shopping.api.service.user.CommunityService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.utils.BeanUtil;
import com.edaochina.shopping.common.utils.page.PageUtil;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.order.ApprovalRecordDTO;
import com.edaochina.shopping.domain.entity.goods.GoodsSubclass;
import com.edaochina.shopping.domain.entity.goods.GoodsType;
import com.edaochina.shopping.domain.entity.order.ApprovalRecord;
import com.edaochina.shopping.domain.entity.user.Community;
import com.edaochina.shopping.domain.vo.goods.GoodsImgsVO;
import com.edaochina.shopping.domain.vo.order.ApprovalRecordVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author User
 */
@Service("appprovalRecordFacadeImpl")
public class ApprovalRecordFacadeImpl implements ApprovalRecordFacade {

    private final ApprovalRecordService approvalRecordService;

    private final GoodsImgsService goodsImgsService;

    private final SysGoodsTypeService goodsTypeService;

    private final SysGoodsSubclassService sysGoodsSubclassService;

    private final SysUserMerchantService sysUserMerchantService;

    private final CommunityService communityService;

    private final AgentCountyInfoService agentCountyInfoService;

    public ApprovalRecordFacadeImpl(ApprovalRecordService approvalRecordService, GoodsImgsService goodsImgsService, SysGoodsTypeService goodsTypeService, SysGoodsSubclassService sysGoodsSubclassService, SysUserMerchantService sysUserMerchantService, CommunityService communityService, AgentCountyInfoService agentCountyInfoService) {
        this.approvalRecordService = approvalRecordService;
        this.goodsImgsService = goodsImgsService;
        this.goodsTypeService = goodsTypeService;
        this.sysGoodsSubclassService = sysGoodsSubclassService;
        this.sysUserMerchantService = sysUserMerchantService;
        this.communityService = communityService;
        this.agentCountyInfoService = agentCountyInfoService;
    }

    /**
     * 获取审批记录
     *
     * @param approvalRecordDTO 查询参数
     * @return 审批记录
     */
    @Override
    public PageResult getApprovalRecordList(ApprovalRecordDTO approvalRecordDTO) {
        QueryWrapper<ApprovalRecord> recordQueryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(approvalRecordDTO.getShopId())) {
            recordQueryWrapper.eq("shop_id", approvalRecordDTO.getShopId());
        }

        if (StringUtils.isNotEmpty(approvalRecordDTO.getAgentId())) {
            QueryWrapper<Community> communityQueryWrapper = new QueryWrapper<>();
            //  代理商区县信息
            communityQueryWrapper.in("county_id", agentCountyInfoService.agentCountyStrList(approvalRecordDTO.getAgentId()));
            List<Community> list = communityService.list(communityQueryWrapper);
            Set<String> shopIds = queryShopIdsByCommunity(list, sysUserMerchantService);
            if (CollectionUtils.isNotEmpty(shopIds)) {
                recordQueryWrapper.in("shop_id", shopIds);
            } else {
                recordQueryWrapper.eq("shop_id", -1);
            }
        }

        if (StringUtils.isNotEmpty(approvalRecordDTO.getGoodsName())) {
            recordQueryWrapper.like("goods_name", approvalRecordDTO.getGoodsName());
        }

        if (StringUtils.isNotEmpty(approvalRecordDTO.getGoodsId())) {
            recordQueryWrapper.like("goods_id", approvalRecordDTO.getGoodsId());
        }

        if (StringUtils.isNotEmpty(approvalRecordDTO.getGoodsTypeId())) {
            recordQueryWrapper.eq("goods_type_id", approvalRecordDTO.getGoodsTypeId());
        }

        if (approvalRecordDTO.getApprovalFlag() != null && !"".equals(approvalRecordDTO.getApprovalFlag().toString())) {
            recordQueryWrapper.eq("approval_flag", approvalRecordDTO.getApprovalFlag());
        }

        if (approvalRecordDTO.getEndTime() != null) {
            recordQueryWrapper.le("approval_time", approvalRecordDTO.getEndTime());
        }

        if (approvalRecordDTO.getStartTime() != null) {
            recordQueryWrapper.ge("approval_time", approvalRecordDTO.getStartTime());
        }
        recordQueryWrapper.orderByDesc("approval_time");
        Pages pages = approvalRecordDTO.getPages();
        IPage<ApprovalRecord> page = approvalRecordService.page(new Page<>(pages.getPageIndex(), pages.getPageSize()), recordQueryWrapper);
        List<ApprovalRecordVO> list = null;
        try {
            list = BeanUtil.listBeanToList(page.getRecords(), ApprovalRecordVO.class);
            for (ApprovalRecordVO approvalRecordVO : list) {

                List<GoodsImgsVO> goodsSubclassList = goodsImgsService.getGoodsImgsList(approvalRecordVO.getGoodsId());
                if (CollectionUtils.isNotEmpty(goodsSubclassList)) {
                    approvalRecordVO.setImgUrl(goodsSubclassList.get(0).getImgUrl());
                }

                GoodsType goodsType = goodsTypeService.getById(approvalRecordVO.getGoodsTypeId());
                if (goodsType != null) {
                    approvalRecordVO.setTypeName(goodsType.getTypeName());
                }

                GoodsSubclass goodsSubclass = sysGoodsSubclassService.getById(approvalRecordVO.getGoodsSubclassId());
                if (goodsSubclass != null) {
                    approvalRecordVO.setSubclassName(goodsSubclass.getSubclassName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        pages.setTotal((int) page.getTotal());
        return PageUtil.getPageResult(list, pages);
    }

}
