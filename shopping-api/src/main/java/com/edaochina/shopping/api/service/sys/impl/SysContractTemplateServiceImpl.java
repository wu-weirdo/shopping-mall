package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.SysContractTemplateMapper;
import com.edaochina.shopping.api.dao.sys.SysDictionaryParamMapper;
import com.edaochina.shopping.api.service.sys.SysContractTemplateService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.pdf.PdfCreateUtil;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.entity.sys.SysContractTemplate;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 合同模板表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-24
 */
@Service
public class SysContractTemplateServiceImpl implements SysContractTemplateService {

    @Autowired
    SysDictionaryParamMapper sysDictionaryParamMapper;

    @Autowired
    SysContractTemplateMapper sysContractTemplateMapper;

    @Override
    public SysContractTemplate queryByUserType(String type) {
        SysDictionaryParam sysDictionaryParam = sysDictionaryParamMapper.querySysValue("contract_template", type);
        SysContractTemplate contractTemplate = sysContractTemplateMapper.queryByRemark(sysDictionaryParam.getSysValue());
        if (contractTemplate == null) {
            contractTemplate = new SysContractTemplate();
        }
        return contractTemplate;
    }

    @Override
    public String createMerchantContract() {
        SysContractTemplate sysContractTemplate = queryByUserType(RoleConstants.MERCHANT_ROLE_STRING);
        String fileName = "/usr/local/merchantContact/" + IdUtils.nextId() + ".pdf";
        String context = sysContractTemplate.getContent();
        // 生成pdf合同
        if (!PdfCreateUtil.createPdf(fileName, context, sysContractTemplate.getName())) {
            throw new YidaoException(ReturnData.CREATE_CONTRACT_FAIL);
        }
        // 商户的合同文件名放到缓存中
        RedisTool.set("merchantContractFileName", fileName);
        return fileName;
    }
}
