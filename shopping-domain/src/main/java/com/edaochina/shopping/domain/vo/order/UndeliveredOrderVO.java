package com.edaochina.shopping.domain.vo.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class UndeliveredOrderVO implements Serializable {

    /**
     * 未送达数目
     */
    private Integer undeliveredNum;
}
