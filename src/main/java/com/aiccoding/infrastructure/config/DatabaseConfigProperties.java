package com.aiccoding.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库配置属性类
 *
 * @author aiccoding
 * @date 2026-03-08
 * @description 用于绑定数据库相关配置属性
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfigProperties {

    /**
     * JDBC驱动类名
     */
    private String driverClassName;

    /**
     * 数据库连接URL
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * HikariCP连接池最大连接数
     */
    private int maximumPoolSize = 10;

    /**
     * HikariCP连接池最小空闲连接数
     */
    private int minimumIdle = 5;

    // Getters and Setters

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(int minimumIdle) {
        this.minimumIdle = minimumIdle;
    }
}
