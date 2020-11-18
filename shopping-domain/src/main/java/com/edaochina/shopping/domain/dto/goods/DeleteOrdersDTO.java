package com.edaochina.shopping.domain.dto.goods;

import java.io.Serializable;

public class DeleteOrdersDTO implements Serializable {

    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
