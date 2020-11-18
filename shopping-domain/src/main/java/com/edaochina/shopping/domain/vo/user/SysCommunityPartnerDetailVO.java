package com.edaochina.shopping.domain.vo.user;

import com.edaochina.shopping.domain.entity.user.CommunityPartenerCountyInfo;
import com.edaochina.shopping.domain.entity.user.SysCommunityPartner;

import java.util.List;

/**
 * 群社合伙人详情
 *
 * @author jintian
 * @date 2019/7/24 15:08
 */
public class SysCommunityPartnerDetailVO extends SysCommunityPartner {

    //  合作区县信息
    private List<CommunityPartenerCountyInfo> areaInfos;

    public List<CommunityPartenerCountyInfo> getAreaInfos() {
        return areaInfos;
    }

    public void setAreaInfos(List<CommunityPartenerCountyInfo> areaInfos) {
        this.areaInfos = areaInfos;
    }
}
