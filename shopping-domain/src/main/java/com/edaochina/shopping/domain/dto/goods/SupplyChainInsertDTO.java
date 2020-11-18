package com.edaochina.shopping.domain.dto.goods;

import com.edaochina.shopping.domain.entity.goods.SupplyChain;

import java.util.List;

/**
 * SupplyChainInsertDTO
 *
 * @author wangpenglei
 * @since 2019/11/6 10:11
 */
public class SupplyChainInsertDTO extends SupplyChain {

    private List<String> images;

    @Override
    public String toString() {
        return "SupplyChainInsertDTO{" +
                "images=" + images +
                "} " + super.toString();
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
