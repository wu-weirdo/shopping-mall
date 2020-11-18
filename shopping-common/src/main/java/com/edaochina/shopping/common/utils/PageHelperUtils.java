package com.edaochina.shopping.common.utils;

import com.edaochina.shopping.domain.base.page.PageResult;
import com.edaochina.shopping.domain.base.page.Pages;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * pageHelper 辅助类
 *
 * @author jintian
 * @date 2019/8/26 17:54
 */
public class PageHelperUtils {

    /**
     * 返回内容转换
     *
     * @param pageInfo
     * @param <T>
     * @return
     */
    public static <T> PageResult parseToPageResult(PageInfo<T> pageInfo) {
        PageResult<T> pageResult = new PageResult<>();
        Pages pages = new Pages();
        pages.setTotal((int) pageInfo.getTotal());
        pages.setPageIndex(pageInfo.getPageNum());
        pages.setPageSize(pageInfo.getPageSize());
        pageResult.setList(pageInfo.getList());
        pageResult.setPages(pages);
        return pageResult;
    }

    /**
     * pageHelper设置查询页和查询的数量
     *
     * @param pages
     */
    public static void setPageHelper(Pages pages) {
        if (pages == null) {
            pages = new Pages();
        }
        PageHelper.startPage(pages.getPageIndex(), pages.getPageSize());
    }


    /**
     * 返回内容转换
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> PageResult parseToPageResult(List<T> list) {
        PageResult<T> pageResult = new PageResult<>();
        Pages pages = new Pages();
        if (list instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page page = (com.github.pagehelper.Page) list;
            pages.setPageIndex(page.getPageNum());
            pages.setPageSize(page.getPageSize());
            pages.setTotal((int) page.getTotal());
        } else if (list != null) {
            pages.setPageIndex(1);
            pages.setPageSize(list.size());
            pages.setTotal(list.size());
        }
        pageResult.setList(list);
        pageResult.setPages(pages);
        return pageResult;
    }
}
