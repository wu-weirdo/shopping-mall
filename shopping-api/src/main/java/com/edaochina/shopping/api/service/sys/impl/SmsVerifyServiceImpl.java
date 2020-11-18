package com.edaochina.shopping.api.service.sys.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.sys.SmsVerifyMapper;
import com.edaochina.shopping.api.service.sys.SmsVerifyService;
import com.edaochina.shopping.domain.entity.sys.SmsVerify;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信验证码表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-15
 */
@Service
public class SmsVerifyServiceImpl extends ServiceImpl<SmsVerifyMapper, SmsVerify> implements SmsVerifyService {

}
