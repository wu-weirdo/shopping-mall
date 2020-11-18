package com.edaochina.shopping.domain.dto.user;

import lombok.Data;

/**
 * @author jintian
 * @date 2019/10/25 16:37
 */
@Data
public class CheckInvitatCodeDTO {

    private String communitys;

    private String invitatCode;

    private String[] communityList;
}
