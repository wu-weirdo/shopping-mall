package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author zzk
 * @Date 2018/12/28
 */
@Data
public class LoginDTO implements Serializable {

    @NotBlank(message = "账号不能为空")
    private String account;
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 用户类型 1 管理员 2团长 3 商家 4 代理商
     */
    @NotBlank(message = "用户类型不能为空")
    private String roleId;

    private String openid;
}
