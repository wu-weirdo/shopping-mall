package com.edaochina.shopping.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {
        "com.edaochina.shopping",
        "com.edaochina.shopping.common.exception",
        "com.edaochina.shopping.common.configuration",
        "com.edaochina.shopping.api.dao",
        "com.edaochina.shopping.common.utils.cache"})
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

}
