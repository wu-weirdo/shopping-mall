package com.edaochina.shopping.common.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.edaochina.shopping.common.utils.LoggerUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @BelongsProject: customize
 * @BelongsPackage: com.edaochina.shopping.common.configuration
 * @Author: Jason
 * @CreateTime: 2018-10
 * @Description: Druid连接池配置
 */
@Configuration
public class DruidConfig {

    /**
     * 加载时读取指定的配置信息,前缀为spring.datasource.druid
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource() {
        LoggerUtils.debug(DruidConfig.class, "DruidConfig开始启动");
        return new DruidDataSource();
    }
}
