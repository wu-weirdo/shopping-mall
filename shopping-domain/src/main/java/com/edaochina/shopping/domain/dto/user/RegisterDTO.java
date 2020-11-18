package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author zzk
 * @Date 2018/12/26
 */
@Data
public class RegisterDTO implements Serializable {
    @NotBlank(message = "账号不能为空")
    protected String account;
    @NotBlank(message = "手机号不能为空")
    protected String phone;
    @NotBlank(message = "密码不能为空")
    protected String password;
    @NotBlank(message = "姓名不能为空")
    protected String name;

    @NotBlank(message = "身份证号码不能为空")
    protected String identityNo;
}
