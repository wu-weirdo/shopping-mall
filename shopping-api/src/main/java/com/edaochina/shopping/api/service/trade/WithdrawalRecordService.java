package com.edaochina.shopping.api.service.trade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.trade.*;
import com.edaochina.shopping.domain.entity.trade.PaymentRecord;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import com.edaochina.shopping.domain.vo.trade.WithdrawalRecordVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-29
 */
public interface WithdrawalRecordService extends IService<WithdrawalRecord> {

    PageResult<WithdrawalRecordVo> selectWithdrawalList(SysWithdrawalRecordListDTO dto);

    boolean approval(WithdrawalRecordApprovalDTO dto);

    boolean apply(WithdrawalApplyDTO dto, BigDecimal total, BigDecimal chargeFee);

    PageResult<WithdrawalRecord> selectApplyRecord(AppWithdrawalRecordListDTO dto);

    void withdrawalStatusHandler(PaymentRecord record);

    WithdrawalRecord getLastWithdrawalToBankInfo(LastWithdrawalDTO lastWithdrawalDTO);

    List<PaymentRecord> queryUnkown();
}
