package com.edaochina.shopping.domain.dto.order;

import java.io.Serializable;

public class WriteOffDTO implements Serializable {

    private String id;

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
