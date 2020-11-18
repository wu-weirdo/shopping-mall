package com.edaochina.shopping.common.law;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.client.FddClientExtra;
import com.fadada.sdk.client.authForplatform.ApplyClientNumCert;
import com.fadada.sdk.client.authForplatform.CompanyDeposit;
import com.fadada.sdk.client.authForplatform.HashDeposit;
import com.fadada.sdk.client.authForplatform.PersonDeposit;
import com.fadada.sdk.client.authForplatform.model.*;
import com.fadada.sdk.client.request.ExtsignReq;
import com.fadada.sdk.test.util.ConfigUtil;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import java.io.File;
import java.io.IOException;

public class auth4pltfom {
    private static String APP_ID = "";
    private static String APP_SECRET = "";
    private static String V = "2.0";
    private static String HOST = "https://testapi.fadada.com:8443/api/";

    private String customer_name;
    private String id_card = "";// 身份证号码;
    private String ident_type = "";// 身份证类型 ;
    private String mobile;
    private String contract_id;
    private String template_id;
    private String transaction_id;
    private String email;
    private File file;
    private StringBuffer response = new StringBuffer("==================Welcome ^_^ ==================");

    public void before() {
        String timeStamp = HttpsUtil.getTimeStamp();

        customer_name = ConfigUtil.getPersonName();
        contract_id = "CONT" + timeStamp;// 直接上传接口合同编号
        template_id = "TEMP" + timeStamp;// 模板编号
        transaction_id = "TRAN_" + timeStamp;// 手动签交易号
        email = "ep" + timeStamp + "@fadada.com";// 个人邮箱
        id_card = "";// 身份证号码;
        mobile = "";// 手机号;
        file = new File(ConfigUtil.getFilePath());

        response.append("\n").append("名字:").append(customer_name);
        response.append("\n").append("身份证号码:").append(id_card);
        response.append("\n").append("证件类型:").append(ident_type);
        response.append("\n").append("手机号:").append(mobile);
        response.append("\n").append("邮箱:").append(email);
        response.append("\n").append("上传合同编号:").append(contract_id);
        response.append("\n").append("上传模板编号:").append(template_id);
        response.append("\n").append("手动签交易号:").append(transaction_id);
    }

    public void test1() {
        regAccount();//1注册账号
        HashDeposit();//2实名信息哈希存证
        PersonDeposit();//3个人实名信息存证
        CompanyDeposit();//4企业实名信息存证
        ApplyClientNumCert();//5编号证书申请
        addSignature1();//6印章上传
        addSignature2();//6印章上传
        customSignature();//7自定义印章
    }

    public void Test2() {
        uploadContract();//8合同上传
        uploadTemplate();//9模板上传
        GenerateContract();//10模板填充
    }

    public void Test3() {
        uploadContract();//8合同上传
        extsignAuto();//11自动签署
        extsign();//12手动签署接口
        viewContract();//13合同查看
        downloadContract();//14合同下载
        contractFiling();//15合同下载
    }


    public void regAccount() {
        response.append("\n").append("1.注册账号:");
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String open_id = transaction_id;
        String account_type = "2";
        String result = base.invokeregisterAccount(open_id, account_type);
        response.append("\n").append(result);
    }

    public void CompanyDeposit() {
        response.append("\n").append("4.企业信息实名存证:");
        CompanyDeposit companyDeposit = new CompanyDeposit(APP_ID, APP_SECRET, V, HOST);
        CompanyDepositReq req = new CompanyDepositReq();
        req.setCustomer_id("");

        /**=======存证相关===========*/
        req.setPreservation_name("");//存证名称
        req.setPreservation_desc("存证描述");//存证描述
        req.setPreservation_data_provider("存证数据提供方");//存证数据提供方

        req.setCompany_name("法大大");//企业名称

        /**=======证件相关===========*/
        req.setDocument_type(""); //证件类型1:三证合一2：旧版营业执照

        req.setCredit_code(id_card); //统一社会信用代码 document_type =1时必填
        req.setCredit_code_file(file); //统一社会信用代码电子版
//        req.setLicence(""); //营业注册号
        req.setLicence_file(file); //营业注册号电子版

//        req.setOrganization("");//组织机构代码 document_type =2时必填
        req.setOrganization_file(file);//组织机构代码电子版
//        req.setVerified_time("");//实名时间

        /**=======认证方式相关===========*/
        req.setVerified_mode("1");//实名认证方式 1:授权委托书2:银行对公打款
        //verifiedMode =1必填
        req.setPower_attorney_file(file);//调资源维护接口返回 verifiedMode =1必填

        //verifiedMode =2必填
//        req.setPublic_branch_bank("");//己方银行支行 verified_mode =2填
//        req.setPublic_bank_account("");//己方银行账号 verified_mode =2填

//        req.setCustomer_bank("");//客户打款银行
//        req.setCustomer_branch_bank("");//客户银行支行
//        req.setCustomer_bank_account("");//客户银行帐号

//        req.setPay_type("");//打款类型  1.随机码 2.随机金额
//        req.setAmount_or_random_code("");//打款金额/打款随机码
//        req.setUser_back_fill_amount_or_random_code("");//用户回填金额/打款随机码

        req.setCompany_principal_type("1");//企业负责人身份:1.法人，2代理人

        /**=======法人信息===========*/
        req.setLegal_name("");//法人姓名
        req.setLegal_idcard(id_card);//法人身份证号
        req.setTransaction_id("");//交易号
        req.setIdcard_positive_file(file);//证件照正面
        req.setIdcard_negative_file(file);//证件照反面
        req.setLive_detection_file(file);//活体检测图

        /**=======企业负责人实名存证信息===========*/
        CompanyPrincipalVerifiedMsg msg = new CompanyPrincipalVerifiedMsg();
        msg.setCustomer_id("");//企业负责人客户编号

        //存证描述相关
        msg.setPreservation_name("存证名称");//存证名称
        msg.setPreservation_desc("存证描述");//存证描述
        msg.setPreservation_data_provider("存证数据提供方");//存证数据提供方

        //负责人信息相关
        msg.setDocument_type("1");//证件类型 默认是1：身份证
        msg.setName("");//姓名
        msg.setIdcard(id_card);//证件号
//        msg.setMobile("");//手机号


        PublicSecurityEssentialFactor public_security_essential_factor = new PublicSecurityEssentialFactor();
        MobileEssentialFactor mobile_essential_factor = new MobileEssentialFactor();
        BankEssentialFactor bank_essential_factor = new BankEssentialFactor();
        MobileAndBankEssentialFactor mobile_and_bank_essential_factor = new MobileAndBankEssentialFactor();
        //1:公安部二要素(姓名+身份证);
        public_security_essential_factor.setApplyNum("123");
        public_security_essential_factor.setQueryTime("");
        public_security_essential_factor.setSystemFlag("");
        public_security_essential_factor.setIdcardCompare("");
        public_security_essential_factor.setNameCompare("");
        public_security_essential_factor.setVrifiedProvider("");
        //2:手机三要素(姓名+身份证+手机号);
        mobile_essential_factor.setTransactionId("");
        mobile_essential_factor.setVerifiedProvider("");
        mobile_essential_factor.setResult("");
        //3:银行卡三要素(姓名+身份证+银行卡);
        bank_essential_factor.setBank("");
        bank_essential_factor.setBankAccount("");
        bank_essential_factor.setTransactionId("");
        bank_essential_factor.setVerifiedProvider("");
        bank_essential_factor.setResult("");
        //4:四要素(姓名+身份证+手机号+银行卡)
        mobile_and_bank_essential_factor.setBank("");
        mobile_and_bank_essential_factor.setBankAccount("");
        mobile_and_bank_essential_factor.setTransactionId("");
        mobile_and_bank_essential_factor.setMobileVerificationCode("");
        mobile_and_bank_essential_factor.setVerifiedProvider("");
        mobile_and_bank_essential_factor.setResult("");

        //存证类型相关
//        msg.setVerified_time("");//实名时间
        /**
         * 1:公安部二要素(姓名+身份证);
         * 2:手机三要素(姓名+身份证+手机号);
         * 3:银行卡三要素(姓名+身份证+银行卡);
         * 4:四要素(姓名+身份证+手机号+银行卡)
         */
        msg.setVerified_type("1");
        msg.setPublic_security_essential_factor(public_security_essential_factor);//verified_type =1 公安部二要素
//        msg.setMobile_essential_factor(mobile_essential_factor);//verified_type =2手机三要素
//        msg.setBank_essential_factor(bank_essential_factor);//verified_type =3银行卡三要素
//        msg.setMobile_and_bank_essential_factor(mobile_and_bank_essential_factor);//verified_type =4四要素
        //liveDetection(活体检测信息)：
        LiveDetection live_detection = new LiveDetection();
        live_detection.setTransactionId("123");//活体检测交易号
        live_detection.setResult("成功");//活体检测结果
        msg.setLive_detection(live_detection);
        req.setCompany_principal_verified_msg(msg);//json 企业负责人实名存证信息
        String result = companyDeposit.invokeCompanyDeposit(req);
        response.append("\n").append(result);
    }


    public void HashDeposit() {
        response.append("\n").append("2.实名信息哈希存证:");
        HashDeposit hashDeposit = new HashDeposit(APP_ID, APP_SECRET, V, HOST);
        String customer_id = "";
        String preservation_name = "";
        String preservation_desc = "success";
        String result = hashDeposit.invokeHashDeposit(customer_id, preservation_name, preservation_desc, file, transaction_id);
        response.append("\n").append(result);
    }

    public void HashDeposit1() {
        response.append("\n").append("4.实名信息哈希存证:");
        HashDeposit hashDeposit = new HashDeposit(APP_ID, APP_SECRET, V, HOST);
        String customer_id = "";
        String preservation_name = "123";
        String preservation_desc = "success";
        String file_name = "";//fileName 文件名
        String file_size = "";//fileSize 文件大小
        String original_sha256 = "";//sha256 sha256值
        String noper_time = "";//文件最后修改时间
        file_name = file.getName();//fileName 文件名
        file_size = String.valueOf(file.length());//fileSize 文件大小
        original_sha256 = FddEncryptTool.sha256(file);//sha256 sha256值
        noper_time = String.valueOf(file.lastModified() / 1000);//文件最后修改时间
        String result = hashDeposit.invokeHashDeposit(customer_id, preservation_name, preservation_desc, file.getName(), noper_time, file_size, original_sha256, transaction_id);
        response.append("\n").append(result);
    }

    public void PersonDeposit() {
        response.append("\n").append("3.个人实名信息存证:");
        PersonDeposit personDeposit = new PersonDeposit(APP_ID, APP_SECRET, V, HOST);
        PersonDepositReq req = new PersonDepositReq();
        req.setCustomer_id(""); //客户编号

        req.setPreservation_name("存证名称"); //存证名称
        req.setPreservation_desc(""); //存证描述
        req.setPreservation_data_provider("存证数据提供方");//存证数据提供方

        req.setName("姓名");//姓名
        req.setDocument_type("");//证件类型 默认是0：身份证
        req.setIdcard(id_card);//证件号
        req.setIdcard_positive_file(file);//证件照正面
        req.setIdcard_negative_file(file);//证件照反面
        req.setMobile("");//手机号
        req.setVerified_time("");//实名时间
        req.setVerified_type("4");//实名存证类型

        PublicSecurityEssentialFactor public_security_essential_factor = new PublicSecurityEssentialFactor();
        MobileEssentialFactor mobile_essential_factor = new MobileEssentialFactor();
        BankEssentialFactor bank_essential_factor = new BankEssentialFactor();
        MobileAndBankEssentialFactor mobile_and_bank_essential_factor = new MobileAndBankEssentialFactor();
        //1:公安部二要素(姓名+身份证);
        public_security_essential_factor.setApplyNum("123");
        public_security_essential_factor.setQueryTime("");
        public_security_essential_factor.setSystemFlag("");
        public_security_essential_factor.setIdcardCompare("");
        public_security_essential_factor.setNameCompare("");
        public_security_essential_factor.setVrifiedProvider("");
        //2:手机三要素(姓名+身份证+手机号);
        mobile_essential_factor.setTransactionId("");
        mobile_essential_factor.setVerifiedProvider("");
        mobile_essential_factor.setResult("");
        //3:银行卡三要素(姓名+身份证+银行卡);
        bank_essential_factor.setBank("");
        bank_essential_factor.setBankAccount("");
        bank_essential_factor.setTransactionId("");
        bank_essential_factor.setVerifiedProvider("");
        bank_essential_factor.setResult("");
        //4:四要素(姓名+身份证+手机号+银行卡)
        mobile_and_bank_essential_factor.setBank("");
        mobile_and_bank_essential_factor.setBankAccount("");
        mobile_and_bank_essential_factor.setTransactionId("");
        mobile_and_bank_essential_factor.setMobileVerificationCode("");
        mobile_and_bank_essential_factor.setVerifiedProvider("");
        mobile_and_bank_essential_factor.setResult("123");

//        req.setPublic_security_essential_factor(public_security_essential_factor);//verified_type =1 公安部二要素
//        req.setMobile_essential_factor(mobile_essential_factor);//verified_type =2手机三要素
//        req.setBank_essential_factor(bank_essential_factor);//verified_type =3银行卡三要素
        req.setMobile_and_bank_essential_factor(mobile_and_bank_essential_factor);//verified_type =4四要素

        LiveDetection liveDetection = new LiveDetection();//活体检测信息
        liveDetection.setTransactionId("");//活体检测交易号
        liveDetection.setResult("");//活体检测结果
        req.setLive_detection(liveDetection);
        req.setLive_detection_file(file);

        String result = personDeposit.invokePersonDeposit(req);
        response.append("\n").append(result);
    }


    public void ApplyClientNumCert() {
        response.append("\n").append("6.编号证书申请:");
        ApplyClientNumCert applyClientNumCert = new ApplyClientNumCert(APP_ID, APP_SECRET, V, HOST);
        String customer_id = "";
        String evidence_no = "";
        String result = applyClientNumCert.invokeapplyClinetNumcert(customer_id, evidence_no);
        response.append("\n").append(result);
    }

    public void addSignature1() {
        response.append("\n").append("印章上传:");
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        File imgfile = new File("");
        String result = base.invokeaddSignature("", imgfile, "");
        response.append("\n").append(result);
    }

    public void addSignature2() {
        response.append("\n").append("印章上传:");
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String signature_img_base64 = "";
        String result = base.invokeaddSignature("", signature_img_base64);
        response.append("\n").append(result);
    }

    public void customSignature() {
        response.append("\n").append("自定义印章:");
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String customer_id = "";
        String content = "content";
        String result = base.invokecustomSignature(customer_id, content);
        response.append("\n").append(result);
    }

    public void uploadContract() {
        response.append("\n").append("上传合同接口");
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String result = base.invokeUploadDocs(contract_id, "合同标题", null, "", ".pdf");
        response.append("\n").append(result);
    }

    public void uploadTemplate() {
        response.append("\n").append("上传模板");
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String doc_url = "";
        String result = base.invokeUploadTemplate(template_id, file, doc_url);
        response.append("\n").append(result);
    }

    public void GenerateContract() {
        try {
            response.append("\n").append("合同生成");
            FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
            String font_size = "";
            String font_type = "";
            String paramter = "";
            paramter = getparamter();
            String dynamic_tables = "";
            dynamic_tables = getdynamic_tables();
            String result = base.invokeGenerateContract(template_id, contract_id, "", font_size, font_type, paramter, dynamic_tables);
            response.append("\n").append(result);
            String viewpdf_url = JSON.parseObject(result).getString("viewpdf_url");
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + viewpdf_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extsign() {
        response.append("\n").append("手动签署接口:");
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        ExtsignReq req = new ExtsignReq();
        req.setCustomer_id("");
        req.setTransaction_id(transaction_id);
        req.setContract_id("");
        req.setDoc_title("1");
        req.setReturn_url("");
        String result = base.invokeExtSign(req);
        response.append("\n").append(result);
    }

    public void extsignAuto() {
        try {
            response.append("\n").append("自动签");
            FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
            ExtsignReq req = new ExtsignReq();
            req.setCustomer_id("");
            req.setTransaction_id(transaction_id);
            req.setContract_id("");
            req.setClient_role("1");
            req.setSign_keyword("");
            req.setDoc_title("1");
            String result = base.invokeExtSignAuto(req);
            response.append("\n").append(result);
            String viewpdf_url = JSON.parseObject(result).getString("viewpdf_url");
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + viewpdf_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewContract() {
        try {
            response.append("\n").append("合同查看");
            FddClientExtra extra = new FddClientExtra(APP_ID, APP_SECRET, V, HOST);
            String contract_id = "";
            String result = extra.invokeViewPdfURL(contract_id);
            response.append("\n").append(result);
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void downloadContract() {
        response.append("\n").append("合同下载");
        String contract_id = "";
        FddClientExtra extra = new FddClientExtra(APP_ID, APP_SECRET, V, HOST);
        String result = extra.invokeDownloadPdf(contract_id);
        response.append("\n").append(result);
    }

    public void contractFiling() {
        response.append("\n").append("合同归档");
        FddClientBase base = new FddClientBase(APP_ID, APP_SECRET, V, HOST);
        String contract_id = "";
        String result = base.invokeContractFilling(contract_id);
        response.append("\n").append(result);
    }


    public void after() {
        System.out.println(response);
    }

    private String getparamter() {
        JSONObject paramter = new JSONObject();
//		 paramter.put("homeUrl","√");
        paramter.put("borrower", "深圳法大大");
        return paramter.toString();
    }

    private String getdynamic_tables() {
        JSONArray dynamic_tables = new JSONArray();
        JSONObject dynamic2 = new JSONObject();
        dynamic2.put("insertWay", 1);
//        dynamic2.put("keyword", "");
        dynamic2.put("pageBegin", "1");
        dynamic2.put("cellHeight", "16.0");

        dynamic2.put("colWidthPercent", new int[]{3, 4, 4, 4});
        dynamic2.put("theFirstHeader", "附二");
        dynamic2.put("cellHorizontalAlignment", "1");
        dynamic2.put("cellVerticalAlignment", "5");
        dynamic2.put("headers", new String[]{"序号", "借款人", "贷款人", "金额"});
        String row1[] = new String[]{"1", "小网", "小易", "1000"};
        String row2[] = new String[]{"2", "小云", "小音", "2000"};
        String row3[] = new String[]{"3", "小乐", "天马", "3000"};
        dynamic2.put("datas", new String[][]{row1, row2, row3});
        dynamic2.put("headersAlignment", "1");
        dynamic2.put("tableWidthPercentage", 80);
        dynamic_tables.add(dynamic2);
        System.out.println(dynamic_tables.toString());
        return dynamic_tables.toString();
    }
}
