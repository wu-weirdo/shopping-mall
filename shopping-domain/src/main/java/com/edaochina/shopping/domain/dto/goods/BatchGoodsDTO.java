package com.edaochina.shopping.domain.dto.goods;

import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 商品批量修改dto
 * @since : 2019/7/3 15:18
 */
public class BatchGoodsDTO {

    private List<String> ids;

    /**
     * 是否删除（10否，20是）
     */
    private Integer deleteFlag;

    /**
     * 上架状态
     */
    private Integer putawayStatus;

    @Override
    public String toString() {
        return "BatchGoodsDTO{" +
                "ids=" + ids +
                ", deleteFlag=" + deleteFlag +
                ", putawayStatus=" + putawayStatus +
                '}';
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getPutawayStatus() {
        return putawayStatus;
    }

    public void setPutawayStatus(Integer putawayStatus) {
        this.putawayStatus = putawayStatus;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
