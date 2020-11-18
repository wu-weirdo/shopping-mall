package com.edaochina.shopping.domain.entity.sys;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysNotice extends Model<SysNotice> {

    private Integer id;

    /**
     * 滚动条通知名称
     */
    private String noticeName;

    /**
     * 滚动条通知内容
     */
    private String noticeContent;

    /**
     * 是否删除(0:未删除,1:已删除)
     */
    private Integer delFlg;

    /**
     * 状态(0:显示,1:隐藏)
     */
    private Integer noticeStatus;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
