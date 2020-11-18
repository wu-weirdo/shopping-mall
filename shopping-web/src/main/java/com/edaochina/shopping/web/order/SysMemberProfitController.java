package com.edaochina.shopping.web.order;

import com.edaochina.shopping.api.service.trade.MemberOrderCalcDetailService;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.AjaxResultGeneric;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.order.MemberOrderCountDTO;
import com.edaochina.shopping.domain.vo.order.SysMemberOrderMonthVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jintian
 * @date 2019/4/1 15:50
 */
@RestController
@RequestMapping("/sys/memberProfit")
public class SysMemberProfitController {

    @Resource
    MemberOrderCalcDetailService memberOrderCalcDetailService;

    @RequestMapping("queryUserMemberOrderCount")
    public AjaxResult queryUserMemberOrderCount(@RequestBody MemberOrderCountDTO memberOrderCountDto) {
        return BaseResult.successResult(memberOrderCalcDetailService.queryUserMemberOrderCount(memberOrderCountDto));
    }

    @GetMapping("month")
    public AjaxResultGeneric<List<SysMemberOrderMonthVO.MonthDetail>> month(MemberOrderCountDTO dto) {
        return BaseResult.successGenericResult(memberOrderCalcDetailService.month(dto));
    }

    @PostMapping(value = "/export")
    @OperationLogMark("月度分润导出")
    public AjaxResultGeneric export(@RequestBody MemberOrderCountDTO dto) {
        return BaseResult.successGenericResult(memberOrderCalcDetailService.export(dto));
    }

}
