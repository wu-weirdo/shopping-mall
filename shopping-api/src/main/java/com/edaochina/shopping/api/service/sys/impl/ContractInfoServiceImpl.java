package com.edaochina.shopping.api.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.edaochina.shopping.api.dao.sys.ContractInfoMapper;
import com.edaochina.shopping.api.service.sys.ContractInfoService;
import com.edaochina.shopping.domain.dto.sys.UpdContractInfoDTO;
import com.edaochina.shopping.domain.entity.sys.ContractInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 合同信息表 服务实现类
 * </p>
 *
 * @since 2019-05-31
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {

    private Logger logger = LoggerFactory.getLogger(ContractInfoServiceImpl.class);

    @Resource
    ContractInfoMapper contractInfoMapper;

    @Override
    public int addContractInfo(ContractInfo contractInfo) {
        logger.info("addContractInfo req:" + JSON.toJSONString(contractInfo));
        return contractInfoMapper.insertContractInfo(contractInfo);
    }

    @Override
    public int updContractInfo(UpdContractInfoDTO dto) {
        logger.info("updContractInfo req:" + JSON.toJSONString(dto));
        return contractInfoMapper.updateContractInfo(dto);
    }

    @Override
    public int updAutoContract(String id, String autoSignTransactionId, String autoSignResult) {
        logger.info("updAutoContract req:" + "{id:" + id + ",autoSignTransactionId:" + autoSignTransactionId + ",autoSignResult:" + autoSignResult + "}");
        return contractInfoMapper.updateAutoContract(id, autoSignTransactionId, autoSignResult);
    }

    @Override
    public int updContractUrl(String id, String downloadContractUrl, String showContractUrl) {
        logger.info("updContractUrl req:" + "{id:" + id + ",downloadContractUrl:" + downloadContractUrl + ",showContractUrl:" + showContractUrl + "}");
        return contractInfoMapper.updateContractUrl(id, downloadContractUrl, showContractUrl);
    }

    @Override
    public ContractInfo queryById(String contractId) {
        return contractInfoMapper.queryById(contractId);
    }

    @Override
    public ContractInfo queryByUserSignTId(String transaction_id) {
        return contractInfoMapper.queryByUserSignTId(transaction_id);
    }
}
