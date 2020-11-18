package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

@Data
public class UpdUserPhoneDTO {

    private String userId;

    private String sessionKey;

    /**
     * 加密数据
     */
    private String encryptedData;

    /**
     * 加密算法的初始向量
     */
    private String iv;
}
