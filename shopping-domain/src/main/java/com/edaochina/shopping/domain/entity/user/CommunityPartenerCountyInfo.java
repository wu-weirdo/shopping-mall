package com.edaochina.shopping.domain.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author jintian
 * @date 2019/7/24 17:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommunityPartenerCountyInfo extends CountyInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 代理商id
     */
    private String communityPartenerId;

    /**
     * 是否删除(0:未删除,1:已删除)
     */
    private Integer delFlg;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
