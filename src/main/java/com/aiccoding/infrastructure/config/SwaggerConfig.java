/**
 * Swagger API文档配置类
 * <p>
 * 用于配置SpringDoc OpenAPI（Swagger UI），生成API文档<br>
 * 支持API分组、安全认证、全局参数等高级功能<br>
 * 遵循阿里巴巴Java开发规范，使用@Slf4j进行日志记录
 * </p>
 *
 * @author 小王
 * @version 1.0
 * @since 2026-03-08
 */
package com.aiccoding.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档配置类
 * <p>
 * 配置SpringDoc OpenAPI以生成交互式API文档<br>
 * 访问地址：http://localhost:8080/api/swagger-ui.html 或 http://localhost:8080/api/swagger-ui/index.html<br>
 * 配置内容包括：API基本信息、联系人信息、许可证、安全认证等
 * </p>
 */
@Slf4j
@Configuration
public class SwaggerConfig {

    /**
     * 应用名称
     */
    @Value("${spring.application.name:aiccoding}")
    private String applicationName;

    /**
     * 应用版本
     */
    @Value("${info.version:1.0.0}")
    private String applicationVersion;

    /**
     * 应用描述
     */
    @Value("${info.description:AI Coding Platform API Documentation}")
    private String applicationDescription;

    /**
     * 联系人姓名
     */
    @Value("${swagger.contact.name:小王}")
    private String contactName;

    /**
     * 联系人邮箱
     */
    @Value("${swagger.contact.email:admin@aiccoding.com}")
    private String contactEmail;

    /**
     * 联系人URL
     */
    @Value("${swagger.contact.url:https://www.aiccoding.com}")
    private String contactUrl;

    /**
     * 服务条款URL
     */
    @Value("${swagger.license.url:https://www.aiccoding.com/license}")
    private String licenseUrl;

    /**
     * 配置OpenAPI基本信息
     * <p>
     * 设置API的标题、版本、描述、联系人、许可证等信息<br>
     * 这些信息将在Swagger UI页面顶部显示
     * </p>
     *
     * @return OpenAPI配置对象
     */
    @Bean
    public OpenAPI customOpenAPI() {
        log.info("开始初始化Swagger OpenAPI配置");
        
        // 创建联系信息
        Contact contact = new Contact()
                .name(contactName)
                .email(contactEmail)
                .url(contactUrl);

        // 创建许可证信息
        License license = new License()
                .name(licenseName)
                .url(licenseUrl);

        // 创建API信息
        Info apiInfo = new Info()
                .title(applicationName + " API Documentation")
                .version(applicationVersion)
                .description(applicationDescription)
                .contact(contact)
                .license(license);

        // 创建OpenAPI对象
        OpenAPI openAPI = new OpenAPI()
                .info(apiInfo);

        // 配置JWT认证（如果需要）
        configureSecurity(openAPI);
        
        log.info("Swagger OpenAPI配置初始化完成");
        log.info("API文档访问地址: http://localhost:8080/api/swagger-ui.html");
        
        return openAPI;
    }

    /**
     * 配置API安全认证
     * <p>
     * 配置JWT Bearer Token认证方式，用于在Swagger UI中进行API测试<br>
     * 如果不需要认证，可以注释掉此方法或其中的配置
     * </p>
     *
     * @param openAPI OpenAPI配置对象
     */
    private void configureSecurity(OpenAPI openAPI) {
        log.debug("配置Swagger安全认证");
        
        // 配置JWT Bearer认证方案
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // 将认证方案添加到OpenAPI
        openAPI.schemaRequirement("BearerAuth", bearerScheme);

        // 配置全局安全要求（可选）
        // 如果所有API都需要认证，可以取消注释下面的代码
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");
        openAPI.addSecurityItem(securityRequirement);
        
        log.debug("Swagger安全认证配置完成");
    }

    /**
     * 配置API分组（可选）
     * <p>
     * 如果需要将API按模块分组显示，可以使用GroupedOpenApi<br>
     * 需要在pom.xml中添加springdoc-openapi-starter-webmvc-ui依赖
     * </p>
     *
     * @return GroupedOpenApi配置（示例）
     */
    /*
    @Bean
    public GroupedOpenApi publicApi() {
        log.debug("配置Swagger公共API分组");
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/api/public/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        log.debug("配置Swagger管理API分组");
        return GroupedOpenApi.builder()
                .group("admin-apis")
                .pathsToMatch("/api/admin/**")
                .build();
    }
    */

    /**
     * 自定义OpenAPI自定义器（可选）
     * <p>
     * 用于进一步自定义OpenAPI配置，如添加全局参数、修改响应等<br>
     * 可以根据业务需求扩展此方法
     * </p>
     */
    /*
    @Bean
    public OpenApiCustomiser customiser() {
        return openApi -> {
            log.debug("应用OpenAPI自定义配置");
            // 在这里添加自定义的OpenAPI配置
            // 例如：添加全局Header参数、修改响应头等
        };
    }
    */
}