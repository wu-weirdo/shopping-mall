package com.edaochina.shopping.api.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.sys.SearchHistoryDao;
import com.edaochina.shopping.api.service.goods.AppGoodsService;
import com.edaochina.shopping.api.service.sys.SearchHistoryService;
import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.domain.entity.sys.SearchHistory;
import com.edaochina.shopping.domain.vo.goods.AppGoodsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (SearchHistory)表服务实现类
 *
 * @author wangpenglei
 * @since 2019-09-29 14:41:41
 */
@Service("searchHistoryService")
public class SearchHistoryServiceImpl extends ServiceImpl<SearchHistoryDao, SearchHistory> implements SearchHistoryService {

    private final AppGoodsService goodsService;

    public SearchHistoryServiceImpl(AppGoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public List<String> hot(String community) {
        QueryAppGoodsDTO dto = new QueryAppGoodsDTO();
        dto.setCommunity(community);
        dto.setGoodsSalesOrder(true);
        dto.setPages(new Pages(1, 8));
        return goodsService.getGoodsList(dto).parallelStream().map(AppGoodsVO::getGoodsName).collect(Collectors.toList());
    }

}