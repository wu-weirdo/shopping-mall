package com.edaochina.shopping.domain.vo.message;

import com.edaochina.shopping.domain.entity.message.MessageMeta;

/**
 * todo
 *
 * @author wangpenglei
 * @since 2019/10/14 15:38
 */
public class MessageMetaVo extends MessageMeta {

    private Integer complete;

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }
}
