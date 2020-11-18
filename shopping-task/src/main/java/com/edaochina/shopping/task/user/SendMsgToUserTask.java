package com.edaochina.shopping.task.user;

import com.edaochina.shopping.api.facade.sys.CommonFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @author jintian
 * @date 2019/8/23 10:27
 */
@Configuration
@EnableScheduling
@Component
public class SendMsgToUserTask {

    @Autowired
    private CommonFacade commonFacade;

    //@Scheduled(cron = "0/20 * * * * ?")
    public void sendMsgToUser() {
        commonFacade.sendQuestionnaire();
    }
}
