package com.edaochina.shopping.api.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.sys.SearchHistory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (SearchHistory)表数据库访问层
 *
 * @author wangpenglei
 * @since 2019-09-29 14:41:40
 */
public interface SearchHistoryDao extends BaseMapper<SearchHistory> {

    /**
     * 查询小区热搜
     *
     * @param community 小区id
     * @return 热搜列表
     */
    @Select("SELECT content " +
            "FROM search_history WHERE " +
            "communityid = #{community} GROUP BY content ORDER BY count( 1 ) DESC LIMIT 10;")
    List<String> hot(@Param("community") String community);

}