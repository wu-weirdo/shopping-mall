package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.dao.sys.SysNoticeMapper;
import com.edaochina.shopping.api.service.sys.SysNoticeService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.domain.entity.sys.SysNotice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysNoticeServiceImpl implements SysNoticeService {

    @Resource
    SysNoticeMapper sysNoticeMapper;

    @Override
    public List<SysNotice> selectNoticeList() {
        return sysNoticeMapper.selectNoticeList();
    }

    @Override
    public int updateNotice(SysNotice sysNotice) {
        return sysNoticeMapper.updateNotice(sysNotice);
    }

    @Override
    public int deleteNotice(Integer id) {
        return sysNoticeMapper.updateDelFlg(id, 1);
    }

    @Override
    public int addNotice(SysNotice sysNotice) {
        return sysNoticeMapper.addNotice(sysNotice);
    }

    @Override
    public int updateStatus(Integer id, Integer noticeStatus) {
        // 检查滚动条显示数
        if (noticeStatus == 0 && appNoticeList().size() > 0) {
            throw new YidaoException(ReturnData.NOTICE_ONLY_ONE_SHOW);
        }
        return sysNoticeMapper.updateStatus(id, noticeStatus);
    }

    @Override
    public List<SysNotice> appNoticeList() {
        return sysNoticeMapper.appNoticeList();
    }

    @Override
    public int getNoticeShowNum() {
        return sysNoticeMapper.getNoticeShowNum();
    }

}
