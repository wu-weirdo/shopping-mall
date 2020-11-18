package com.edaochina.shopping.api.facade.sys.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.edaochina.shopping.api.facade.sys.CommonFacade;
import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.api.service.sys.SmsVerifyService;
import com.edaochina.shopping.api.service.user.UserService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.common.utils.alisms.AliSmsUtil;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.constants.SmsConstant;
import com.edaochina.shopping.domain.dto.sys.SmsVerifyDTO;
import com.edaochina.shopping.domain.entity.sys.SmsVerify;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.google.common.base.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zzk
 */
@Service("commonFacade")
public class CommonFacadeImpl implements CommonFacade {
    private final CommonService commonService;

    private volatile static boolean taskFlag = false;

    @Resource
    private UserService userService;
    @Resource
    private SmsVerifyService smsVerifyService;

    @Autowired
    public CommonFacadeImpl(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public AjaxResult uploadImage(MultipartFile file) {
        return BaseResult.successResult(commonService.uploadImage(file));
    }

    @Override
    public AjaxResult sendSmsCode(SmsVerifyDTO smsVerifyDTO) {
        String code = IdUtils.nextId(4);
        smsVerifyDTO.setCode(code);
        //如果是发送注册验证码,先判断手机号是否已被绑定
        List<SysUser> sysUsers = userService.checkPhone(smsVerifyDTO);
        if (CollectionUtils.isNotEmpty(sysUsers)) {
            return BaseResult.failedResult(ReturnData.PHONE_USED.getCode(), ReturnData.PHONE_USED.getMsg());
        }
        smsVerifyDTO.setTemplateCode(SmsConstant.VERIFY_CODE);
        //发短信
        SendSmsResponse response;
        try {
            response = AliSmsUtil.sendSms(smsVerifyDTO);
        } catch (ClientException e) {
            e.printStackTrace();
            return BaseResult.failedResult(ReturnData.SEND_ERROR.getCode(), ReturnData.SEND_ERROR.getMsg());
        }
        //保存短信回执信息
        saveSmsVerify(smsVerifyDTO, response);
        return BaseResult.successResult(response);
    }

    @Override
    public AjaxResult appUpload(String fileUrl) {
        return BaseResult.successResult(commonService.appUploadImage(fileUrl));
    }

    @Override
    public boolean loginSmsSend(String phone) {
        SmsVerifyDTO smsVerifyDTO = new SmsVerifyDTO();
        smsVerifyDTO.setPhone(phone);
        smsVerifyDTO.setTemplateCode(SmsConstant.LOGIN_CHECK_CODE);
        // 生成验证码并存到缓存中
        String code = IdUtils.nextId(4);

        RedisTool.set(SmsConstant.LOGIN_CHECK_CODE_REDIS_HEAD + phone, code, 300);
        smsVerifyDTO.setCode(code);
        SendSmsResponse response;
        try {
            response = AliSmsUtil.sendSms(smsVerifyDTO);
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        //保存短信回执信息
        saveSmsVerify(smsVerifyDTO, response);
        return true;
    }


    @Override
    public boolean sendUpdatePasswordMsg(String phone, String accounType) {
        SmsVerifyDTO smsVerifyDTO = new SmsVerifyDTO();
        smsVerifyDTO.setPhone(phone);
        smsVerifyDTO.setTemplateCode(SmsConstant.UPDSTE_PASSWORD_CODE);
        // 生成验证码并存到缓存中
        String code = IdUtils.nextId(6);
        smsVerifyDTO.setCode(code);
        SendSmsResponse response;
        try {
            response = AliSmsUtil.sendSms(smsVerifyDTO);
            RedisTool.set(SmsConstant.UPDATE_PASSWORD_REDIS_HEAD + accounType + phone, code, 300);
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        //保存短信回执信息
        saveSmsVerify(smsVerifyDTO, response);
        return true;
    }

    @Override
    public void sendQuestionnaire() {
        if (taskFlag) {
            return;
        }
        taskFlag = true;
        int n = 100;
        SmsVerifyDTO smsVerifyDTO = new SmsVerifyDTO();
        for (int i = 5; n == 100 && i < 10; i++) {
            List<SysUser> sysUsers = userService.queryHasPhone(i * n, n);
            smsVerifyDTO.setTemplateCode(SmsConstant.QUESTIONNAIRE_CODE);
            for (SysUser sysUser : sysUsers) {
                smsVerifyDTO.setPhone(sysUser.getPhone());
                SendSmsResponse response = null;
                try {
                    response = AliSmsUtil.sendSms(smsVerifyDTO);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                //保存短信回执信息
                saveSmsVerify(smsVerifyDTO, response);
            }
        }
        taskFlag = false;
    }

    @Override
    public boolean sendQuestionnaireToUser(String phone) {
        SmsVerifyDTO smsVerifyDTO = new SmsVerifyDTO();
        smsVerifyDTO.setTemplateCode(SmsConstant.QUESTIONNAIRE_CODE);
        smsVerifyDTO.setPhone(phone);
        SendSmsResponse response = null;
        try {
            response = AliSmsUtil.sendSms(smsVerifyDTO);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //保存短信回执信息
        saveSmsVerify(smsVerifyDTO, response);
        return true;
    }

    private void saveSmsVerify(SmsVerifyDTO smsVerifyDTO, SendSmsResponse response) {
        SmsVerify smsVerify = new SmsVerify();
        smsVerify.setId(IdUtils.nextId());
        smsVerify.setPhone(smsVerifyDTO.getPhone());
        smsVerify.setCode(smsVerifyDTO.getCode());
        smsVerify.setBizId(response.getBizId());
        smsVerify.setReturnCode(response.getCode());
        smsVerify.setReturnMsg(response.getMessage());
        smsVerify.setCreateTime(LocalDateTime.now());
        smsVerifyService.save(smsVerify);
        //检查异常code,优化用户提示
        checkMessage(response);
    }

    private void checkMessage(SendSmsResponse response) {
        //判断是否触发业务限流：每分钟1条,每小时5条,每天10条
        if (Objects.equal(response.getCode(), SmsConstant.LIMIT_CONTROL)) {
            //阿里云返回信息 "触发小时级流控Permits:5"  "触发天级流控Permits:10"
            String message = response.getMessage();
            char index = message.charAt(2);
            String lastChar = message.substring(message.indexOf(":") + 1);
            switch (index) {
                case '分':
                    response.setMessage("发送失败,每分钟限发送" + lastChar + "条验证码");
                    break;
                case '小':
                    response.setMessage("发送失败,每小时限发送" + lastChar + "条验证码");
                    break;
                case '天':
                    response.setMessage("发送失败,每天限发送" + lastChar + "条验证码");
                default:
                    break;
            }
        }
        //判断手机号是否非法
        if (Objects.equal(response.getCode(), SmsConstant.MOBILE_NUMBER_ILLEGAL)) {
            response.setMessage(ReturnData.ERROR_PHONE_NUM.getMsg());
        }
    }
}
