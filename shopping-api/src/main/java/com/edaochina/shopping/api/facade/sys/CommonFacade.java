package com.edaochina.shopping.api.facade.sys;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.sys.SmsVerifyDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zzk
 */
public interface CommonFacade {
    /**
     * 图片上传
     *
     * @param file 文件
     * @return 路径
     */
    AjaxResult uploadImage(MultipartFile file);

    AjaxResult sendSmsCode(SmsVerifyDTO smsVerifyDTO);

    AjaxResult appUpload(String fileUrl);

    boolean loginSmsSend(String phone);

    /**
     * 发送修改密码验证码
     *
     * @param phone
     * @param accounType
     * @return
     */
    boolean sendUpdatePasswordMsg(String phone, String accounType);

    void sendQuestionnaire();

    boolean sendQuestionnaireToUser(String phone);
}
