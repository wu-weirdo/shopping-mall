package com.edaochina.shopping.api.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.order.ApprovalRecordMapper;
import com.edaochina.shopping.api.service.order.ApprovalRecordService;
import com.edaochina.shopping.domain.constants.ApprovalFlagConstants;
import com.edaochina.shopping.domain.entity.order.ApprovalRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Service
public class ApprovalRecordServiceImpl extends ServiceImpl<ApprovalRecordMapper, ApprovalRecord> implements ApprovalRecordService {

    @Resource
    ApprovalRecordMapper approvalRecordMapper;

    @Override
    public List<ApprovalRecord> getApprovalRecordListForTask() {
        QueryWrapper<ApprovalRecord> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.eq("approval_flag", ApprovalFlagConstants.ACCEPT);
        recordQueryWrapper.last(" limit 100");
        return approvalRecordMapper.selectList(recordQueryWrapper);
    }

    @Override
    public ApprovalRecord getLastAcceptApprovalRecord(String goodsId) {
        return approvalRecordMapper.getLastAcceptApprovalRecord(goodsId);
    }

}
