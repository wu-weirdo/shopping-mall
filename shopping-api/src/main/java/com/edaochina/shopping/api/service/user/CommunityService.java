package com.edaochina.shopping.api.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.dto.user.CommunityAppListDTO;
import com.edaochina.shopping.domain.dto.user.CommunityListDTO;
import com.edaochina.shopping.domain.dto.user.CommunityUpdDTO;
import com.edaochina.shopping.domain.entity.sys.CommunityDTO;
import com.edaochina.shopping.domain.entity.sys.SysAreaInfo;
import com.edaochina.shopping.domain.entity.user.Community;
import com.edaochina.shopping.domain.vo.sys.SysBackCommunityAreaInfo;
import com.edaochina.shopping.domain.vo.user.CommunityListVO;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 小区表 by zzk
 * 服务类
 * </p>
 *
 * @author zzk
 * @since 2019-01-07
 */
public interface CommunityService extends IService<Community> {
    /**
     * 添加小区
     *
     * @param dto
     * @return
     */
    AjaxResult addCommunity(CommunityDTO dto);

    PageResult<CommunityListVO> getSysList(CommunityListDTO dto);

    /**
     * app端小区列表
     *
     * @return
     */
    AjaxResult getAppList(CommunityAppListDTO dto);

    List<Community> getSysAll();

    /**
     * 小区详情
     *
     * @param id
     * @return
     */
    Community getDetail(String id);

    /**
     * 删除小区
     *
     * @param id
     * @return
     */
    int deleteComm(String id);

    int updateComm(CommunityUpdDTO dto);

    List<Community> getSysListByCountyId(String countyId);

    SysAreaInfo selectHasAgentAreaList();

    List<Community> getAgentList();

    Collection<SysBackCommunityAreaInfo> queryHasCommunityList();
}
