package com.edaochina.shopping.web.sys;

import com.edaochina.shopping.domain.dto.sys.TencentTokenDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jintian
 * @date 2019/7/31 10:49
 */
@RestController
@RequestMapping("app/tencent")
public class AppTencentController {

    private Logger logger = LoggerFactory.getLogger(AppTencentController.class);

    /**
     * 接收通知消息
     *
     * @return
     */
    @RequestMapping("acceptMsg")
    public String acceptMsg(TencentTokenDTO dto, HttpServletRequest req) {
        if (StringUtils.isNotBlank(dto.getEchostr())) {
            return dto.getEchostr();
        } else {
            // TODO 接收通知事件

            // TODO 用户关注后获取关注人的openId

            // TODO 更加openId获取用户的uid,更新到对应人的tencentOpenId上去
            return "";
        }
    }
}
