package com.edaochina.shopping.api.dao.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.user.CommunityAppListDTO;
import com.edaochina.shopping.domain.dto.user.CommunityListDTO;
import com.edaochina.shopping.domain.dto.user.CommunitySelectDTO;
import com.edaochina.shopping.domain.entity.user.Community;
import com.edaochina.shopping.domain.vo.sys.SysHasAgenAreaVo;
import com.edaochina.shopping.domain.vo.user.CommunityAppListVO;
import com.edaochina.shopping.domain.vo.user.CommunityListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 小区表 by zzk
 * Mapper 接口
 * </p>
 *
 * @author zzk
 * @since 2019-01-07
 */
@Repository
public interface CommunityMapper extends BaseMapper<Community> {

    List<CommunityAppListVO> selectByCoord(CommunityAppListDTO dto);

    List<CommunityAppListVO> selectInId(CommunitySelectDTO dto);

    List<CommunityAppListVO> selectByHistory(CommunitySelectDTO dto);

    List<Community> getSysListByCountyId(@Param("countyId") String countyId);

    /**
     * 已代理的小区
     *
     * @return 已代理的小区列表
     */
    List<Community> getAgentList();

    List<SysHasAgenAreaVo> queryDistinctArea();

    int deleteCommunity(String id);

    List<CommunityAppListVO> queryCommunity(@Param("countyCode") String code, @Param("longitude") Double longitude, @Param("latitude") Double latitude, @Param("search") String search);

    List<CommunityAppListVO> selectInIdAndHasGoods(CommunitySelectDTO dto);

    List<CommunityListVO> querySysCommunitys(CommunityListDTO dto);
}