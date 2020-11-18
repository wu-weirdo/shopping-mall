package com.edaochina.shopping.api.facade.law.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.api.facade.law.FadadaLawFacade;
import com.edaochina.shopping.api.service.sys.ContractInfoService;
import com.edaochina.shopping.api.service.sys.SysContractTemplateService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.api.service.user.UserService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.law.service.FadadaService;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.law.DepositUser;
import com.edaochina.shopping.domain.dto.law.FadadaReturnInfo;
import com.edaochina.shopping.domain.dto.sys.UpdContractInfoDTO;
import com.edaochina.shopping.domain.entity.sys.ContractInfo;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * @author jintian
 * @date 2019/5/31 10:37
 */
@Component
public class FadadaLawFacadeImpl implements FadadaLawFacade {

    private Logger logger = LoggerFactory.getLogger(FadadaLawFacadeImpl.class);

    @Autowired
    private FadadaService fadadaService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserMerchantService merchantService;

    @Autowired
    private SysContractTemplateService sysContractTemplateService;

    @Autowired
    private ContractInfoService contractInfoService;

    @Override
    public String registerAccount(String userId, String type) {
        logger.info("law registerAccount userId:" + userId);
        String result = fadadaService.registerAccount(userId);
        JSONObject resultJson = JSON.parseObject(result);
        // 解析註冊后內容，將id更新到用戶信息中區
        if ("1".equals(resultJson.getString("code"))) {
            if (RoleConstants.USER_ROLE_STRING.equals(type)) {
                userService.updateUserLawId(userId, resultJson.getString("data"));
            }
            if (RoleConstants.MERCHANT_ROLE_STRING.equals(type)) {
                merchantService.updateUserLawId(userId, resultJson.getString("data"));
            }
        }
        handlerDeposit(userId, type, resultJson.getString("data"));
        return result;
    }

    /**
     * 用户实名处理
     */
    private void handlerDeposit(String userId, String type, String data) {
        DepositUser depositUser = getDepositUserByUserIdAndType(userId, type);
        // 实名处理
        String depositTrancationId = IdUtils.nextId();
        String depositResult = fadadaService.userDeposit(data, depositUser.getUserName(), depositUser.getUserIdCard(), depositTrancationId);
        logger.info("depositResult :" + depositResult);
        // 实名存证编号获取
        String depositId = JSON.parseObject(depositResult).getString("data");
        // 证书申请
        // 异步方式
        fadadaService.applyCertificate(data, depositId);
        // 签章生成
        String signaTureResult = fadadaService.getUserSignature(data, depositUser.getUserName());
        logger.info("signaTureResult :" + signaTureResult);
        String imgBase64 = JSON.parseObject(signaTureResult).getString("data");
        String base64 = JSON.parseObject(imgBase64).getString("signature_img_base64");
        // 签章上传
        String result = fadadaService.uploadUserSignature(data, base64);
        logger.info("upload user signature:" + result);
    }

    private DepositUser getDepositUserByUserIdAndType(String userId, String type) {
        DepositUser depositUser = new DepositUser();
        if (RoleConstants.USER_ROLE_STRING.equals(type)) {
            SysUser user = userService.getById(userId);
            depositUser.setUserIdCard("342921199303011615");
            depositUser.setUserName(user.getName());
        }
        if (RoleConstants.MERCHANT_ROLE_STRING.equals(type)) {
            SysUserMerchant userMerchant = merchantService.getById(userId);
            depositUser.setUserIdCard(userMerchant.getIdentityNo());
            depositUser.setUserName(userMerchant.getName());
        }
        return depositUser;
    }

    public String userSign(String userId, String type, String contractId, String returnUrl) {
        String phone = "";
        String customerId = "";
        String title = "";
        String transactionId = IdUtils.nextId();
        if (RoleConstants.USER_ROLE_STRING.equals(type)) {
            SysUser user = userService.getById(userId);
            phone = user.getPhone();
            // 获取客户id
            customerId = user.getLawConsumerId();
        }
        if (RoleConstants.MERCHANT_ROLE_STRING.equals(type)) {
            SysUserMerchant merchant = merchantService.getById(userId);
            phone = merchant.getPhone();
            // 获取客户id
            customerId = merchant.getLawConsumerId();
        }
        // 根据合同id获取合同标题
        ContractInfo contractInfo = contractInfoService.queryById(contractId);
        title = contractInfo.getContractName();
        String result = fadadaService.userSign(customerId, transactionId, contractId, title, phone, returnUrl);
        contractInfoService.updContractInfo(new UpdContractInfoDTO(contractId, phone, transactionId, result));
        return result;
    }

    @Override
    public String uploadContract(String userId, String type) {
        // 获取当前合同地址
        String contractFileName = RedisTool.get("merchantContractFileName");
        if (StringUtils.isBlank(contractFileName)) {
            contractFileName = sysContractTemplateService.createMerchantContract();
        }
        File contractFile = new File(contractFileName);
        // 合同生成不使用零时生成方式，编辑后异步生成
        String contractId = IdUtils.nextId();
        String result = fadadaService.uploadContract("商户入驻协议", contractFile, contractId);
        logger.info("upload result ");
        // 记录到表中
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setId(contractId);
        contractInfo.setContractName("商户入驻协议");
        contractInfo.setUserId(userId);
        contractInfo.setUserType(Integer.valueOf(type));
        contractInfo.setContractTemplateId(1);

        contractInfoService.addContractInfo(contractInfo);
        return contractId;
    }

    @Override
    public void acceptSignReturn(FadadaReturnInfo returnInfo) {
        logger.info("acceptSignReturn req:" + JSON.toJSONString(returnInfo));
        // 如果签署成功
        if ("3000".equals(returnInfo.getResult_code())) {
            // 根据交易号查询签署合同，获取签署合同编号和标题
            ContractInfo contractInfo = contractInfoService.queryByUserSignTId(returnInfo.getTransaction_id());
            String transactionId = IdUtils.nextId();
            String contarctId = contractInfo.getId();
            String title = contractInfo.getContractName();
            // 自动签署
            String result = fadadaService.autoSign(transactionId, contarctId, title);
            logger.info("acceptSignReturn result:" + JSON.toJSONString(result));
            // 记录到表中
            contractInfoService.updAutoContract(contarctId, transactionId, result);
            SysUserMerchant sysUserMerchant = merchantService.getById(contractInfo.getUserId());
            sysUserMerchant.setStatus(1);
            // 更新商户状态
            merchantService.updateById(sysUserMerchant);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String merchantSignContrat(String userId, String returnUrl) {
        SysUserMerchant sysUserMerchant = merchantService.getById(userId);
        if (sysUserMerchant == null) {
            throw new YidaoException(ReturnData.NOT_USER);
        }
        // 判断用户法律效应id是否存在
        if (StringUtils.isBlank(sysUserMerchant.getLawConsumerId())) {
            registerAccount(userId, RoleConstants.MERCHANT_ROLE_STRING);
        }
        String contractId = uploadContract(userId, RoleConstants.MERCHANT_ROLE_STRING);
        return userSign(userId, RoleConstants.MERCHANT_ROLE_STRING, contractId, returnUrl);
    }
}
