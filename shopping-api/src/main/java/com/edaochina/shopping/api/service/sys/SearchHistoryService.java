package com.edaochina.shopping.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.sys.SearchHistory;

import java.util.List;

/**
 * (SearchHistory)表服务接口
 *
 * @author wangpenglei
 * @since 2019-09-29 14:41:41
 */
public interface SearchHistoryService extends IService<SearchHistory> {

    /**
     * 查询小区热搜
     *
     * @param community 小区id
     * @return 热搜列表
     */
    List<String> hot(String community);
}