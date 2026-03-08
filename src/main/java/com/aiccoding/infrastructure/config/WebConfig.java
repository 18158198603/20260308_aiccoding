package com.aiccoding.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Web配置类
 *
 * @author aiccoding
 * @date 2026-03-08
 * @description 配置Web相关的功能，包括跨域请求等
 */
@Configuration
public class WebConfig {

    /**
     * 配置跨域过滤器
     *
     * @return CorsFilter实例
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许的来源
        config.addAllowedOriginPattern("*");
        
        // 允许的方法
        config.addAllowedMethod("*");
        
        // 允许的头部
        config.addAllowedHeader("*");
        
        // 允许携带凭证
        config.setAllowCredentials(true);
        
        // 预检请求缓存时间
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
