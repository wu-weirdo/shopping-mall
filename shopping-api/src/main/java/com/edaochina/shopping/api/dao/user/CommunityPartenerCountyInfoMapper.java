package com.edaochina.shopping.api.dao.user;

import com.edaochina.shopping.domain.entity.user.CommunityPartenerCountyInfo;
import com.edaochina.shopping.domain.entity.user.CountyInfo;

import java.util.List;

/**
 * @author jintian
 * @date 2019/7/24 17:35
 */
public interface CommunityPartenerCountyInfoMapper {
    List<CommunityPartenerCountyInfo> communityPartenerCountyInfoList(String id);

    int insertPartenerCounty(CommunityPartenerCountyInfo communityPartenerCountyInfo);

    int removeByPartenerId(String id);

    List<String> partenerCountyStrList(String partenerId);

    List<CountyInfo> partenerCountyList(String partenerId);
}
