package com.edaochina.shopping.domain.dto.goods;

import lombok.Data;

import java.util.List;

/**
 * 批量审批商品请求
 *
 * @author jintian
 * @date 2019/10/11 13:31
 */
@Data
public class SysBatchApprovalGoodsDTO extends ApprovalGoodsDTO {

    private String userId;

    private List<String> ids;
}
