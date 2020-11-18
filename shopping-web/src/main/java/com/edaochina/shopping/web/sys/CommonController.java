package com.edaochina.shopping.web.sys;


import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.api.facade.sys.CommonFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.sys.SmsVerifyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zzk
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonFacade commonFacade;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 返回结果
     */
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file) {
        return commonFacade.uploadImage(file);
    }

    @PostMapping("/sms/send")
    public AjaxResult requestRegisterSms(@RequestBody SmsVerifyDTO smsVerifyDTO) {
        return commonFacade.sendSmsCode(smsVerifyDTO);
    }

    /**
     * app登录短信发送
     *
     * @param phone
     * @return
     */
    @RequestMapping("/login/smsSend")
    public AjaxResult loginSmsSend(String phone) {
        return new AjaxResult(commonFacade.loginSmsSend(phone));
    }

    /**
     * 微信小程序图片上传
     *
     * @param req
     * @return
     */
    @RequestMapping("appUpload")
    public AjaxResult appUpload(@RequestBody JSONObject req) {
        return commonFacade.appUpload(req.getString("fileUrl"));
    }

    @RequestMapping("sendQuestionnaireToUser")
    public AjaxResult sendQuestionnaireToUser(String phone) {
        return new AjaxResult(commonFacade.sendQuestionnaireToUser(phone));
    }
}
