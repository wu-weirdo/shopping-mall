package com.edaochina.shopping.domain.dto.sys;

public class UpdContractInfoDTO {

    private String id;

    private String userPhone;

    private String userSignTransactionId;

    private String userSignResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserSignTransactionId() {
        return userSignTransactionId;
    }

    public void setUserSignTransactionId(String userSignTransactionId) {
        this.userSignTransactionId = userSignTransactionId;
    }

    public String getUserSignResult() {
        return userSignResult;
    }

    public void setUserSignResult(String userSignResult) {
        this.userSignResult = userSignResult;
    }


    public UpdContractInfoDTO(String id, String userPhone, String userSignTransactionId, String userSignResult) {
        this.id = id;
        this.userPhone = userPhone;
        this.userSignTransactionId = userSignTransactionId;
        this.userSignResult = userSignResult;
    }
}
