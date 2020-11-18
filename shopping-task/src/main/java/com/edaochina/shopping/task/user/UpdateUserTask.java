package com.edaochina.shopping.task.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.service.sys.WxTencentService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.api.service.user.UserService;
import com.edaochina.shopping.domain.entity.user.SysUser;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Configuration
@EnableScheduling
@Component
public class UpdateUserTask {

    private Logger logger = LoggerFactory.getLogger(UpdateUserTask.class);

    @Resource
    private UserService userService;

    @Resource
    private SysUserMerchantService sysUserMerchantService;

    @Resource
    private WxTencentService wxTencentService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateUserTask() {
        logger.info("updateUserTask start");
        SysUser sysUser = new SysUser();
        sysUser.setMemberType(0);
        // 用户绑定区县信息重置
      /*  sysUser.setProvinceName("");
        sysUser.setProvinceCode("");
        sysUser.setCityCode("");
        sysUser.setCityName("");
        sysUser.setCountyCode("");
        sysUser.setCountyName("");
        sysUser.setUserCityAddress("");*/
        // 来源商家重置
        sysUser.setOrigin("");
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        // 获取半小时未回调的用户
        Date date1 = DateUtils.addMinutes(new Date(), -5);
        userQueryWrapper.le("member_time", date1);
        userService.update(sysUser, userQueryWrapper);

        SysUserMerchant sysUserMerchant = new SysUserMerchant();
        sysUserMerchant.setMemberType(0);
        QueryWrapper<SysUserMerchant> sysUserMerchantQueryWrapper = new QueryWrapper<>();
        sysUserMerchantQueryWrapper.le("member_time", date1);
        sysUserMerchantService.update(sysUserMerchant, sysUserMerchantQueryWrapper);
        logger.info("updateUserTask end");
    }

    //@Scheduled(cron = "0 * * * * ?")
    public void updateUserTencentTask() {
        logger.info("updateUserTencentTask start");
        wxTencentService.updateUsersTencentOpenId();
        logger.info("updateUserTencentTask end");
    }
}
