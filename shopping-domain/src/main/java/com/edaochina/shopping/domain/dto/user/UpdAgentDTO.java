package com.edaochina.shopping.domain.dto.user;

import com.edaochina.shopping.domain.entity.user.AgentCountyInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author zzk
 * @Date 2019/1/2
 */
@Data
public class UpdAgentDTO implements Serializable {
    private String id;
    private String name;
    private String photo;
    private String phone;
    private String identityNo;
    private String password;
    private Integer status;
    private Integer payment;
    private List<AgentCountyInfo> agentAreaInfos;
}
