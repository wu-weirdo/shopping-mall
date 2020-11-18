package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.dto.sys.UpdContractInfoDTO;
import com.edaochina.shopping.domain.entity.sys.ContractInfo;

/**
 * <p>
 * 合同信息表 服务类
 * </p>
 *
 * @since 2019-05-31
 */
public interface ContractInfoService {

    int addContractInfo(ContractInfo contractInfo);

    int updContractInfo(UpdContractInfoDTO dto);

    int updAutoContract(String id, String autoSignTransactionId, String autoSignResult);

    int updContractUrl(String id, String downloadContractUrl, String showContractUrl);

    ContractInfo queryById(String contractId);

    ContractInfo queryByUserSignTId(String transaction_id);
}
