package com.aiccoding.startup;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * AI Coding 智能编程平台启动类
 * <p>
 * 项目主入口，负责启动Spring Boot应用<br>
 * 遵循阿里巴巴Java开发规范，使用@Slf4j进行日志记录<br>
 * 支持多环境配置和健康检查端点
 * </p>
 *
 * @author 小王
 * @version 1.0
 * @since 2026-03-08
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.aiccoding"})
@MapperScan({
    "com.aiccoding.infrastructure.repository",
    "com.aiccoding.domain.mapper"
})
public class AiccodingApplication {

    /**
     * 应用程序主入口
     * <p>
     * 启动Spring Boot应用，并在启动时打印应用访问信息<br>
     * 支持通过命令行参数和环境变量配置应用行为
     * </p>
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        log.info("开始启动 AI Coding 智能编程平台...");
        
        SpringApplication app = new SpringApplication(AiccodingApplication.class);
        Environment env = app.run(args).getEnvironment();
        
        logApplicationStartup(env);
    }

    /**
     * 记录应用启动信息
     * <p>
     * 在控制台输出应用的访问地址、健康检查和API文档链接<br>
     * 方便开发人员和运维人员快速了解应用状态
     * </p>
     *
     * @param env Spring环境配置
     */
    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path", "/");
        
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("无法获取主机地址，使用localhost", e);
        }

        // 构建基础URL
        String baseUrl = protocol + "://" + hostAddress + ":" + serverPort + contextPath;
        
        // 构建健康检查URL
        String healthUrl = baseUrl + "actuator/health";
        
        // 构建API文档URL
        String swaggerUrl = baseUrl + "swagger-ui.html";

        log.info("\n----------------------------------------------------------");
        log.info("应用 '{}' 启动成功！", env.getProperty("spring.application.name", "AI Coding"));
        log.info("版本: {}", env.getProperty("info.version", "1.0.0"));
        log.info("运行环境: {}", env.getProperty("spring.profiles.active", "default"));
        log.info("\n访问地址:");
        log.info("  • 应用首页: \u001B[32m{}\u001B[0m", baseUrl);
        log.info("  • 健康检查: \u001B[32m{}\u001B[0m", healthUrl);
        log.info("  • API文档: \u001B[32m{}\u001B[0m", swaggerUrl);
        log.info("\n管理命令:");
        log.info("  • 停止应用: Ctrl+C");
        log.info("\n----------------------------------------------------------");
    }
}
