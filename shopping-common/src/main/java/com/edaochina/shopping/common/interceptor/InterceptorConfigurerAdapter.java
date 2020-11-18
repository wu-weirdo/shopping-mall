package com.edaochina.shopping.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author :         Jason
 * ` * @since  :     2018/10/16 10:52
 * 拦截器配置
 */
@Configuration
@EnableWebMvc
public class InterceptorConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new YidaoHandler())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/app/**")
//                .excludePathPatterns("/**/common/**")
//                .excludePathPatterns("/**/login")
//                .excludePathPatterns("/**/register/**")
//                .excludePathPatterns("/**/getWxToken")
//                .excludePathPatterns("/**/address/area/hasAgentAreaList")
//                .excludePathPatterns("/**/community/listByCountyId")
//                .excludePathPatterns("/**/detail/merchant");
        super.addInterceptors(registry);
    }
}
