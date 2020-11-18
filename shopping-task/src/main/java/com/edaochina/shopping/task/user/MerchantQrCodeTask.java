package com.edaochina.shopping.task.user;

import com.edaochina.shopping.api.dao.user.SysMerchantCommunityMapper;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jintian
 * @date 2019/5/14 13:58
 */
@Configuration
@EnableScheduling
@Component
public class MerchantQrCodeTask {
    private Logger logger = LoggerFactory.getLogger(MerchantQrCodeTask.class);

    private final SysUserMerchantService sysUserMerchantService;
    private final SysMerchantCommunityMapper sysMerchantCommunityMapper;

    @Autowired
    public MerchantQrCodeTask(SysUserMerchantService sysUserMerchantService, SysMerchantCommunityMapper sysMerchantCommunityMapper) {
        this.sysUserMerchantService = sysUserMerchantService;
        this.sysMerchantCommunityMapper = sysMerchantCommunityMapper;
    }

    //@Scheduled(cron = "0 * * * * ?")
    public void initMerchantQrCode() {
        logger.info("initMerchantQrCode start");
        List<SysUserMerchant> merchantList = sysUserMerchantService.queryNoQrCode();
        merchantList.forEach(merchant -> {
            sysUserMerchantService.getQrCodeByMerchantId(merchant.getId());
        });
        logger.info("initMerchantQrCode start");
    }

    //@Scheduled(cron = "0/10 * * * * ?")
    /*public void initMerchantCommunity() {
        logger.info("initMerchantCommunity start");
        QueryWrapper<SysUserMerchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        List<SysUserMerchant> merchantList = sysUserMerchantService.list(queryWrapper);
        merchantList.forEach(merchant -> {
            for(String community: merchant.getCommunity().split(",")){
                sysMerchantCommunityMapper.insertMerchantCommunity(merchant.getId(),community);
            }
        });
        logger.info("initMerchantCommunity start");
    }*/
}
