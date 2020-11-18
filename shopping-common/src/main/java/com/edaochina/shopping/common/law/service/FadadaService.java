package com.edaochina.shopping.common.law.service;

import com.edaochina.shopping.common.utils.date.DateUtil;
import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.client.authForplatform.ApplyClientNumCert;
import com.fadada.sdk.client.authForplatform.PersonDeposit;
import com.fadada.sdk.client.authForplatform.model.PersonDepositReq;
import com.fadada.sdk.client.authForplatform.model.PublicSecurityEssentialFactor;
import com.fadada.sdk.client.request.ExtsignReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author jintian
 * @date 2019/5/27 15:14
 */
@Component
public class FadadaService {

    @Value("${fadada.app_id}")
    private String appId;

    @Value("${fadada.app_secret}")
    private String secret;

    @Value("${fadada.version}")
    private String version;

    @Value("${fadada.host}")
    private String host;

    @Value("${fadada.app_company_code}")
    private String companyCode;

    @Value("${fadada.return_url}")
    private String return_url;

    @Value("${fadada.notify_url}")
    private String notifyUrl;

    /**
     * 注册账号
     *
     * @param userId
     * @return
     */
    public String registerAccount(String userId) {
        FddClientBase base = getFddClientBase();
        return base.invokeregisterAccount(userId, "1");
    }

    /**
     * 用户实名认证
     *
     * @param costomerId    客户编号
     * @param name          姓名
     * @param idCard        身份证号
     * @param transcationId 交易id
     * @return
     */
    public String userDeposit(String costomerId, String name, String idCard, String transcationId) {
        PersonDeposit personDeposit = new PersonDeposit(appId, secret, version, host);
        PersonDepositReq req = new PersonDepositReq();
        //客户编号
        req.setCustomer_id(costomerId);
        //存证名称
        req.setPreservation_name("用户存在");
        //存证数据提供方
        req.setPreservation_data_provider("用户上传");
        //姓名
        req.setName(name);
        //证件类型 默认是0：身份证
        req.setDocument_type("0");
        //证件号
        req.setIdcard(idCard);
        //实名时间
        req.setVerified_time(DateUtil.formatDateTime(new Date()));
        //实名存证类型
        req.setVerified_type("1");

        PublicSecurityEssentialFactor public_security_essential_factor = new PublicSecurityEssentialFactor();
        //1:公安部二要素(姓名+身份证);
        public_security_essential_factor.setApplyNum(transcationId);
        req.setPublic_security_essential_factor(public_security_essential_factor);
        return personDeposit.invokePersonDeposit(req);
    }


    /**
     * 上传模板
     *
     * @param templateId 模板id
     * @param file       模板文件
     */
    public String uploadTemplate(String templateId, File file) {
        FddClientBase base = getFddClientBase();
        String result = base.invokeUploadTemplate(templateId, file, "");
        return result;
    }

    private FddClientBase getFddClientBase() {
        return new FddClientBase(appId, secret, version, host);
    }

    /**
     * 上传合同
     *
     * @param fileName   合同名
     * @param file       合同文件
     * @param contractId 合同id (建表存合同)
     * @return
     */
    public String uploadContract(String fileName, File file, String contractId) {
        FddClientBase base = getFddClientBase();
        return base.invokeUploadDocs(contractId, fileName, file, null, ".pdf");
    }

    /**
     * 手动签署合同
     *
     * @param req
     * @return
     */
    public String userSign(ExtsignReq req, String phone) {
        FddClientBase base = getFddClientBase();
        return base.invokeExtSign(req, phone, "", "");
    }

    /**
     * 用户签署合同
     *
     * @param customerId    用户id
     * @param transactionId 合同签署交易号
     * @param contractId    合同id
     * @param title         合同标题
     * @param phone         用户手机号
     * @return 合同签署跳转页面
     */
    public String userSign(String customerId, String transactionId, String contractId, String title, String phone, String returnUrl) {
        ExtsignReq req = new ExtsignReq();
        req.setCustomer_id(customerId);//客户编号
        req.setTransaction_id(transactionId);//交易号
        req.setContract_id(contractId);//合同编号
        req.setDoc_title(title);//文档标题
        if (StringUtils.isNotBlank(returnUrl)) {
            req.setReturn_url(returnUrl);
        } else {
            req.setReturn_url(return_url);
        }
        req.setNotify_url(notifyUrl);
        req.setOuth_customer_id("");
        return userSign(req, phone);
    }


    /**
     * 自动签署合同
     *
     * @return
     */
    public String autoSign(ExtsignReq req) {
        FddClientBase base = getFddClientBase();
        return base.invokeExtSignAuto(req);
    }

    /**
     * 自动签署合同
     *
     * @param transactionId 交易号
     * @param contractId    合同编号
     * @param title         合同标题
     * @return
     */
    public String autoSign(String transactionId, String contractId, String title) {
        ExtsignReq req = new ExtsignReq();
        req.setCustomer_id(companyCode);
        req.setTransaction_id(transactionId);
        req.setContract_id(contractId);
        req.setClient_role("1");
        req.setSign_keyword(" 杭州成典网络技术有限公司 ");
        req.setDoc_title(title);
        return autoSign(req);
    }

    public void applyCertificate(String customerId, String evidenceNo) {
        ApplyClientNumCert applyClientNumCert = new ApplyClientNumCert(appId, secret, version, host);
        applyClientNumCert.invokeapplyClinetNumcert(customerId, evidenceNo);
        /*return executorService.submit(new UserCertificateCallable(customerId, evidenceNo));*/
    }

    public String getUserSignature(String customerId, String userName) {
        FddClientBase base = getFddClientBase();
        return base.invokecustomSignature(customerId, userName);
    }

    public String uploadUserSignature(String customerId, String imgBase64) {
        FddClientBase base = getFddClientBase();
        return base.invokeaddSignature(customerId, imgBase64);
    }

    class UserCertificateCallable implements Callable {
        private String customerId;

        private String evidenceNo;

        @Override
        public Boolean call() {
            ApplyClientNumCert applyClientNumCert = new ApplyClientNumCert(appId, secret, version, host);
            applyClientNumCert.invokeapplyClinetNumcert(customerId, evidenceNo);
            return true;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getEvidenceNo() {
            return evidenceNo;
        }

        public void setEvidenceNo(String evidenceNo) {
            this.evidenceNo = evidenceNo;
        }

        public UserCertificateCallable(String customerId, String evidenceNo) {
            this.customerId = customerId;
            this.evidenceNo = evidenceNo;
        }
    }
}
