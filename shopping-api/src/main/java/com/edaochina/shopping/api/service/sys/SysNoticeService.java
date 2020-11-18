package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.sys.SysNotice;

import java.util.List;

public interface SysNoticeService {

    List<SysNotice> selectNoticeList();

    int updateNotice(SysNotice sysNotice);

    int deleteNotice(Integer id);

    int addNotice(SysNotice sysNotice);

    int updateStatus(Integer id, Integer noticeStatus);

    List<SysNotice> appNoticeList();

    int getNoticeShowNum();
}
