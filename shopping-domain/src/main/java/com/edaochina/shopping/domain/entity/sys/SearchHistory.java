package com.edaochina.shopping.domain.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (SearchHistory)表实体类
 *
 * @author wangpenglei
 * @since 2019-09-29 14:41:39
 */
@SuppressWarnings("serial")
public class SearchHistory extends Model<SearchHistory> {
    //主键
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户id
    private String uid;
    //创建日期
    private LocalDateTime createTime;
    //搜索内容
    private String content;
    //小区id
    private String communityid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommunityid() {
        return communityid;
    }

    public void setCommunityid(String communityid) {
        this.communityid = communityid;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}