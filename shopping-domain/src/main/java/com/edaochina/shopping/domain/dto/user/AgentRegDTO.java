package com.edaochina.shopping.domain.dto.user;

import com.edaochina.shopping.domain.entity.user.AgentCountyInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author zzk
 * @Date 2018/12/26
 */
@Data
public class AgentRegDTO extends RegisterDTO {
    @NotBlank(message = "头像不能为空")
    private String photo;
    private int payment;

    private List<AgentCountyInfo> agentAreaInfos;
}
