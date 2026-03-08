package com.aiccoding.startup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * AI Coding 智能编程平台启动类
 *
 * @author aiccoding
 * @date 2026-03-08
 * @description 项目主入口，负责启动Spring Boot应用
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.aiccoding"})
@MapperScan("com.aiccoding.infrastructure.repository")
public class AiccodingApplication {

    /**
     * 应用程序主入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(AiccodingApplication.class, args);
    }

}
