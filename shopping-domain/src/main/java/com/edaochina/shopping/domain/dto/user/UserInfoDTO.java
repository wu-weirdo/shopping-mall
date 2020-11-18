package com.edaochina.shopping.domain.dto.user;

/**
 * UserInfoDTO
 *
 * @author wangpenglei
 * @since 2019/10/14 16:38
 */
public class UserInfoDTO {

    private Integer gender;

    private String name;

    private String phone;

    private String address;

    private String userId;

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "gender=" + gender +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
