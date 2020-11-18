package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryMerchantOrderDTO implements Serializable {

    private String id;

    private String shopId;

    private Pages pages;

    /**
     * 查询条件里面的核销状态（0-待核销，1-已经核销）
     */
    private String paramStatus;
    //使用码搜索字段
    private String search;
}
