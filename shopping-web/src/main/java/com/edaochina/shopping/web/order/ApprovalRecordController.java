package com.edaochina.shopping.web.order;


import com.edaochina.shopping.api.facade.order.ApprovalRecordFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.order.ApprovalRecordDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/sys/approvalRecord")
public class ApprovalRecordController {

    @Autowired
    private ApprovalRecordFacade approvalRecordFacade;

    /**
     * 查询审批记录
     *
     * @return
     */
    @ApiOperation("后台查看商品审批记录")
    @PostMapping("/getApprovalRecordList")
    public AjaxResult getApprovalRecordList(@RequestBody ApprovalRecordDTO approvalRecordDTO) {
        return BaseResult.successResult(approvalRecordFacade.getApprovalRecordList(approvalRecordDTO));
    }

}

