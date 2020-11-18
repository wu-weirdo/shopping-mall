package com.edaochina.shopping.domain.entity.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 地址数据表 by张志侃
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAddress extends Model<SysAddress> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId("code")
    private String code;

    /**
     * 名字
     */
    private String name;

    /**
     * 父编号
     */
    private String parentCode;

    /**
     * 等级
     */
    private String level;

    /**
     * 高德adcode
     */
    private String adcode;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
