package com.edaochina.shopping.web.data;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.trade.ChainPayInfo;
import com.edaochina.shopping.domain.dto.trade.ChainPayInfoAffirmDto;
import com.edaochina.shopping.domain.dto.trade.ChainRecipientDto;
import com.edaochina.shopping.domain.entity.trade.ChainRecipientDetail;
import com.edaochina.shopping.api.service.trade.ChainRecipientDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 供应链发货管理(ChainRecipientDetail)表控制层
 *
 * @author makejava
 * @since 2019-11-13 15:05:07
 */
@RestController
@RequestMapping("sys/chainRecipientDetail")
public class SysChainRecipientDetailController {
    /**
     * 服务对象
     */
    @Resource
    private ChainRecipientDetailService chainRecipientDetailService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ChainRecipientDetail selectOne(Integer id) {
        return this.chainRecipientDetailService.queryById(id);
    }

    /**
     * 上传支付以及收货地址信息
     *
     * @return
     */
    @RequestMapping("uploadPayInfo")
    public AjaxResult uploadPayInfo(@RequestBody ChainPayInfo chainPayInfo) {
        return BaseResult.successResult(chainRecipientDetailService.uploadPayInfo(chainPayInfo));
    }

    /**
     * 查询列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("getChainRecipientList")
    public AjaxResult getChainRecipientList(@RequestBody ChainRecipientDto dto) {
        return BaseResult.successResult(chainRecipientDetailService.getChainRecipientList(dto));
    }

    /**
     * 确认支付信息
     *
     * @return
     */
    @RequestMapping("payInfoAffirm")
    public AjaxResult payInfoAffirm(@RequestBody ChainPayInfoAffirmDto dto) {
        return BaseResult.successResult(chainRecipientDetailService.payInfoAffirm(dto));
    }
}