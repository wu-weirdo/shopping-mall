package com.edaochina.shopping.api.dao.sys;

import com.edaochina.shopping.domain.dto.sys.UpdContractInfoDTO;
import com.edaochina.shopping.domain.entity.sys.ContractInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 合同信息表 Mapper 接口
 * </p>
 *
 * @since 2019-05-31
 */
public interface ContractInfoMapper {

    int insertContractInfo(ContractInfo contractInfo);

    int updateContractInfo(UpdContractInfoDTO dto);

    int updateAutoContract(@Param("id") String id, @Param("autoSignTransactionId") String autoSignTransactionId, @Param("autoSignResult") String autoSignResult);

    int updateContractUrl(@Param("id") String id, @Param("downloadContractUrl") String downloadContractUrl, @Param("showContractUrl") String showContractUrl);

    ContractInfo queryById(@Param("contractId") String contractId);

    ContractInfo queryByUserSignTId(@Param("transactionId") String transactionId);
}
