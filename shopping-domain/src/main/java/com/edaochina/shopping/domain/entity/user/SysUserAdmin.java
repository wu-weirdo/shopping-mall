package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class SysUserAdmin extends Model<SysUserAdmin> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String account;

    private String password;

    private String salt;

    private String name;

    private Integer status;

    private String phone;

    private Integer roleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
