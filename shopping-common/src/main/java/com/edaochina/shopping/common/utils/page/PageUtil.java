package com.edaochina.shopping.common.utils.page;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PageUtil {
    /**
     * 代码分页工具
     * mybatisPlus转化为通用page返回
     */
    public <T> PageResult<T> getPageResult(List<T> list, Pages page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setList(list);
        pageResult.setPages(page);
        return pageResult;
    }
}
