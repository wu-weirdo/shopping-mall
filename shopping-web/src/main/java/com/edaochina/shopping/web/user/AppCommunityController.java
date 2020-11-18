package com.edaochina.shopping.web.user;

import com.edaochina.shopping.api.facade.user.CommunityFacade;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.utils.ValidUtil;
import com.edaochina.shopping.domain.dto.user.CommunityAppListDTO;
import com.edaochina.shopping.domain.dto.user.CommunitySelectDTO;
import com.edaochina.shopping.domain.entity.sys.SysUserHistoryCommunity;
import com.edaochina.shopping.domain.vo.user.CommunityAppListVO;
import com.edaochina.shopping.domain.vo.user.CommunitySelectVO;
import io.swagger.annotations.Api;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 社区
 *
 * @author zzk
 */
@RestController
@RequestMapping("/app/community")
@Api(tags = "小区相关")
public class AppCommunityController {

    @Resource
    CommunityFacade communityFacade;

    /**
     * 查询列表
     *
     * @return 小区列表
     */
    @GetMapping(value = "/list")
    public AjaxResult list(@Valid CommunityAppListDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.check(bindingResult);
        }
        return communityFacade.getAppList(dto);
    }

    /**
     * 添加历史小区，如果没有绑定小区就绑定小区
     *
     * @param sysUserHistoryCommunity 用户选择小区信息
     * @return 是否成功
     */
    @PostMapping("/binding")
    public AjaxResult insertHistory(@RequestBody SysUserHistoryCommunity sysUserHistoryCommunity) {
        return communityFacade.insertHistory(sysUserHistoryCommunity);
    }

    /**
     * @param userId             用户id
     * @param openId             微信id
     * @param limit              查询数量
     * @param currentCommunityId 当前小区id
     * @return 用户历史小区
     */
    @GetMapping("/history")
    public AjaxResult history(String userId, String openId, @RequestParam(required = false, defaultValue = "3") int limit, String currentCommunityId) {
        return communityFacade.selectHistory(userId, openId, limit, currentCommunityId);
    }

    /**
     * @return 用户选择小区
     */
    @GetMapping("/select")
    public AjaxResultGeneric<CommunitySelectVO> select(@Valid CommunitySelectDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.checkGeneric(bindingResult);
        }
        return communityFacade.select(dto);
    }

    /**
     * 3.2
     * 小区搜索
     */
    @RequestMapping("queryCommunity")
    public AjaxResultGeneric<List<CommunityAppListVO>> queryCommunity(CommunitySelectDTO dto) {
        if (dto.getLatitude() == null || dto.getLongitude() == null
                || dto.getLatitude() == 0.00 || dto.getLongitude() == 0.00) {
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER);
        }
        return communityFacade.queryCommunity(dto);
    }


}
