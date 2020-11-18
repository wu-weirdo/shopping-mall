package com.edaochina.shopping.api.facade.user;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.domain.dto.user.CommunityAppListDTO;
import com.edaochina.shopping.domain.dto.user.CommunityListDTO;
import com.edaochina.shopping.domain.dto.user.CommunitySelectDTO;
import com.edaochina.shopping.domain.dto.user.CommunityUpdDTO;
import com.edaochina.shopping.domain.entity.sys.CommunityDTO;
import com.edaochina.shopping.domain.entity.sys.SysUserHistoryCommunity;
import com.edaochina.shopping.domain.vo.user.CommunityAppListVO;
import com.edaochina.shopping.domain.vo.user.CommunitySelectVO;

import java.util.List;

/**
 * @Author zzk
 * @Date 2019/1/11
 */
public interface CommunityFacade {
    AjaxResult addCommunity(CommunityDTO dto);

    AjaxResult getSysList(CommunityListDTO dto);

    AjaxResult getAppList(CommunityAppListDTO dto);

    AjaxResult getSysAll();

    /**
     * @return 已被代理的区县
     */
    AjaxResult getAgentList();

    AjaxResult getDetail(String id);

    AjaxResult delete(String id);

    AjaxResult updateComm(CommunityUpdDTO dto);

    /**
     * 通过区县获取小区列表
     *
     * @param countyId
     * @return
     */
    AjaxResult getSysListByCountyId(String countyId);

    AjaxResult insertHistory(SysUserHistoryCommunity sysUserHistoryCommunity);

    /**
     * 小区选择
     *
     * @param dto 查询参数
     * @return 小区选择数据
     */
    AjaxResultGeneric<CommunitySelectVO> select(CommunitySelectDTO dto);

    AjaxResult selectHistory(String userId, String openId, int limit, String currentCommunityId);

    AjaxResultGeneric<List<CommunityAppListVO>> queryCommunity(CommunitySelectDTO dto);
}
