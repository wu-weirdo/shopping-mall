package com.edaochina.shopping.api.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.user.AdminDTO;
import com.edaochina.shopping.domain.dto.user.LoginDTO;
import com.edaochina.shopping.domain.entity.user.SysUserAdmin;
import com.edaochina.shopping.domain.vo.user.SysUserVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-10
 */
public interface SysUserAdminService extends IService<SysUserAdmin> {

    /**
     * 管理员注册
     *
     * @param dto
     * @return
     */
    AjaxResult adminRegister(AdminDTO dto);

    SysUserVO adminCheck(LoginDTO dto);

    PageResult adminList(AdminDTO adminDTO);

    AjaxResult adminDetail(String id);

    AjaxResult adminUpdate(AdminDTO dto);

    Integer getRoleId(String account);
}
