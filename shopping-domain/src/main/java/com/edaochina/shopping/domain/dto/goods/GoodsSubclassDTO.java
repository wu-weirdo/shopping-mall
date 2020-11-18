package com.edaochina.shopping.domain.dto.goods;

import java.io.Serializable;

public class GoodsSubclassDTO implements Serializable {

    private String subclassName;

    public String getSubclassName() {
        return subclassName;
    }

    public void setSubclassName(String subclassName) {
        this.subclassName = subclassName;
    }
}
