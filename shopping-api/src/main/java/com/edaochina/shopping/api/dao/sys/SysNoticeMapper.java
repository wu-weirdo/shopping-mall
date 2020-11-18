package com.edaochina.shopping.api.dao.sys;

import com.edaochina.shopping.domain.entity.sys.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysNoticeMapper {

    List<SysNotice> selectNoticeList();

    int updateNotice(SysNotice sysNotice);

    int updateDelFlg(@Param("id") Integer id, @Param("delFlg") Integer delFlg);

    int addNotice(SysNotice sysNotice);

    int updateStatus(@Param("id") Integer id, @Param("noticeStatus") Integer noticeStatus);

    List<SysNotice> appNoticeList();

    Integer getNoticeShowNum();
}
