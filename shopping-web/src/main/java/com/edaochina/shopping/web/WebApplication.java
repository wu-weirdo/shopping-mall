package com.edaochina.shopping.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 */
@SpringBootApplication(scanBasePackages = {
        "com.edaochina.shopping.common.exception",
        "com.edaochina.shopping.common.handler",
        "com.edaochina.shopping.common.utils.cache",
        "com.edaochina.shopping.common.configuration",
        "com.edaochina.shopping",
        "com.edaochina.shopping.api.dao",
        "com.edaochina.shopping.api.state"
})
@EnableSwagger2
@EnableCaching
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
