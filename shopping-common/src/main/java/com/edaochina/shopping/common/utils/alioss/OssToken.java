package com.edaochina.shopping.common.utils.alioss;

public class OssToken {
    public String expiration;
    public String accessKeyId;
    public String accessKeySecret;
    public String securityToken;
    public String code;
    public String errorMess;

    public OssToken(String expiration, String accessKeyId,
                    String accessKeySecret, String securityToken, String code) {
        super();
        this.expiration = expiration;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
        this.code = code;
    }

    public OssToken() {
        super();
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMess() {
        return errorMess;
    }

    public void setErrorMess(String errorMess) {
        this.errorMess = errorMess;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

}
