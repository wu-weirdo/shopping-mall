package com.edaochina.shopping.domain.base.jwt;

import com.edaochina.shopping.domain.constants.RedisConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.vo.user.MenuVO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author :         Jason
 * @createDate :     2018/10/12 20:41
 * @description :
 */
@Data
@Accessors(chain = true)
public class JWTBean implements Serializable {

    /**
     * 用户编号
     */
    private String id;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 用户名称
     */
    private String name;


    /**
     * 过期时间
     */
    private Date expiresTime;

    /**
     * 菜单列表
     */
    private List<MenuVO> menu;

    public String getTokenKey() {
        return RedisConstants.SYS_TOKEN + this.getId() + this.getRole();
    }

    public boolean isAdmin() {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        return role.equalsIgnoreCase(RoleConstants.ADMIN_ROLE_STRING);
    }

    public boolean isMerchant() {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        return role.equalsIgnoreCase(RoleConstants.MERCHANT_ROLE_STRING);
    }

    public boolean isAgent() {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        return role.equalsIgnoreCase(RoleConstants.AGENT_ROLE_STRING);
    }

}
