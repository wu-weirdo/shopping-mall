package com.edaochina.shopping.domain.dto.live;

import javax.validation.constraints.NotBlank;

/**
 * CheckDTO
 *
 * @author wangpenglei
 * @since 2019/9/9 10:32
 */
public class CheckDTO extends DefaultQueryDTO {

    @NotBlank
    private String code;

    @Override
    public String toString() {
        return "CheckDTO{" +
                "code='" + code + '\'' +
                "} " + super.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
