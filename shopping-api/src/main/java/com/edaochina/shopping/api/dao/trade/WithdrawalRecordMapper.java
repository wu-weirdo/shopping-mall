package com.edaochina.shopping.api.dao.trade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.trade.LastWithdrawalDTO;
import com.edaochina.shopping.domain.dto.trade.SysWithdrawalRecordListDTO;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import com.edaochina.shopping.domain.vo.trade.WithdrawalRecordVo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-29
 */
public interface WithdrawalRecordMapper extends BaseMapper<WithdrawalRecord> {

    List<WithdrawalRecordVo> queryByDto(SysWithdrawalRecordListDTO dto);

    WithdrawalRecord getLastWithdrawalToBankInfo(LastWithdrawalDTO lastWithdrawalDTO);
}
