package com.edaochina.shopping.domain.dto.live;

/**
 * DefaultQueryDTO
 *
 * @author wangpenglei
 * @since 2019/9/9 10:21
 */
public class DefaultQueryDTO {

    private String openId;

    private String userId;

    @Override
    public String toString() {
        return "DefaultQueryDTO{" +
                "openId='" + openId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
