package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.sys.SysAddress;
import com.edaochina.shopping.domain.vo.sys.SysHasAgenAreaVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 地址数据表 by张志侃 Mapper 接口
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
@Mapper
@Repository
public interface SysAddressMapper extends BaseMapper<SysAddress> {

    List<SysHasAgenAreaVo> queryDistinctArea();

    @Select("SELECT three.* " +
            "FROM cdshopping.sys_address three " +
            "WHERE three.CODE = #{code} UNION " +
            "SELECT two.* " +
            "FROM cdshopping.sys_address three " +
            "LEFT JOIN cdshopping.sys_address two ON three.parent_code = two.code " +
            "WHERE three.CODE = #{code} UNION " +
            "SELECT o.* " +
            "FROM cdshopping.sys_address three " +
            "LEFT JOIN cdshopping.sys_address two ON three.parent_code = two.code " +
            "LEFT JOIN cdshopping.sys_address o ON two.parent_code = o.code " +
            "WHERE three.CODE = #{code}")
    List<SysAddress> selectTreeByMinCode(@Param("code") String code);
}
