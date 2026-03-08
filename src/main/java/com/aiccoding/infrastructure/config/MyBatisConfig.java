/**
 * MyBatis配置类
 * <p>
 * 用于配置MyBatis的相关组件，包括SqlSessionFactory、MapperScannerConfigurer等<br>
 * 支持分页插件、驼峰命名转换、逻辑删除等功能的配置<br>
 * 遵循阿里巴巴Java开发规范，使用@Slf4j进行日志记录
 * </p>
 *
 * @author 小王
 * @version 1.0
 * @since 2026-03-08
 */
package com.aiccoding.infrastructure.config;

import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis配置类
 * <p>
 * 配置MyBatis的核心组件，包括：<br>
 * 1. SqlSessionFactory：MyBatis的核心工厂类<br>
 * 2. MapperScannerConfigurer：自动扫描Mapper接口<br>
 * 3. PlatformTransactionManager：事务管理器<br>
 * 4. PageInterceptor：分页插件（如果需要）
 * </p>
 */
@Slf4j
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class MyBatisConfig {

    /**
     * 默认Mapper XML文件位置
     */
    private static final String DEFAULT_MAPPER_LOCATIONS = "classpath:mapper/**/*.xml";

    /**
     * 默认实体类别名包
     */
    private static final String DEFAULT_TYPE_ALIASES_PACKAGE = "com.aiccoding.domain.entity";

    /**
     * 注入数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 配置SqlSessionFactory
     * <p>
     * 创建并配置MyBatis的SqlSessionFactory，设置数据源、配置信息、Mapper位置等<br>
     * 使用@ConditionalOnMissingBean确保不重复创建
     * </p>
     *
     * @return SqlSessionFactory实例
     * @throws Exception 配置异常
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        log.info("开始初始化MyBatis SqlSessionFactory");
        
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        log.debug("已设置数据源");
        
        // 设置Mapper XML文件位置
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(DEFAULT_MAPPER_LOCATIONS));
        log.debug("已设置Mapper XML文件位置：{}", DEFAULT_MAPPER_LOCATIONS);
        
        // 设置实体类别名包
        sqlSessionFactoryBean.setTypeAliasesPackage(DEFAULT_TYPE_ALIASES_PACKAGE);
        log.debug("已设置实体类别名包：{}", DEFAULT_TYPE_ALIASES_PACKAGE);
        
        // 配置MyBatis Configuration
        Configuration configuration = new Configuration();
        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名转换
        configuration.setCacheEnabled(true); // 启用二级缓存
        configuration.setLazyLoadingEnabled(true); // 启用懒加载
        configuration.setMultipleResultSetsEnabled(true); // 允许多结果集
        configuration.setUseColumnLabel(true); // 使用列标签
        configuration.setUseGeneratedKeys(true); // 使用生成的主键
        configuration.setDefaultExecutorType(org.apache.ibatis.session.ExecutorType.REUSE); // 默认执行器类型
        configuration.setDefaultStatementTimeout(25000); // 默认语句超时时间（毫秒）
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class); // 日志实现
        
        sqlSessionFactoryBean.setConfiguration(configuration);
        log.debug("已配置MyBatis Configuration");
        
        // 添加分页插件（可选）
        // sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor()});
        
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        log.info("MyBatis SqlSessionFactory初始化完成");
        
        return sqlSessionFactory;
    }

    /**
     * 配置Mapper扫描器
     * <p>
     * 自动扫描指定包下的Mapper接口，并将其注册为Spring Bean<br>
     * 这样在Service中就可以直接@Autowired注入Mapper接口了
     * </p>
     *
     * @return MapperScannerConfigurer实例
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        log.info("开始配置MyBatis Mapper扫描器");
        
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        
        // 设置Mapper接口所在的包
        configurer.setBasePackage("com.aiccoding.domain.mapper");
        log.debug("已设置Mapper接口包路径：com.aiccoding.domain.mapper");
        
        // 设置SqlSessionFactory Bean名称
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        
        // 设置注解过滤（只扫描有@Mapper注解的接口）
        configurer.setAnnotationClass(org.apache.ibatis.annotations.Mapper.class);
        
        log.info("MyBatis Mapper扫描器配置完成");
        return configurer;
    }

    /**
     * 配置事务管理器
     * <p>
     * 配置基于JDBC的事务管理器，用于管理数据库事务<br>
     * 支持声明式事务管理（@Transactional注解）
     * </p>
     *
     * @return PlatformTransactionManager实例
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        log.info("开始配置事务管理器");
        
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        log.debug("已设置事务管理器的数据源");
        
        log.info("事务管理器配置完成");
        return transactionManager;
    }

    /**
     * 配置分页插件（PageHelper）
     * <p>
     * 如果需要使用分页功能，可以取消注释此方法<br>
     * 需要在pom.xml中添加pagehelper-spring-boot-starter依赖
     * </p>
     *
     * @return PageInterceptor实例
     */
    /*
    @Bean
    public PageInterceptor pageInterceptor() {
        log.info("开始配置MyBatis分页插件");
        
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        
        // 分页插件配置
        properties.setProperty("helperDialect", "mysql"); // 数据库方言
        properties.setProperty("reasonable", "true"); // 合理化分页
        properties.setProperty("supportMethodsArguments", "true"); // 支持通过Mapper接口参数来传递分页参数
        properties.setProperty("params", "count=countSql"); // 用于从对象中根据属性名取值
        
        pageInterceptor.setProperties(properties);
        log.info("MyBatis分页插件配置完成");
        return pageInterceptor;
    }
    */

    /**
     * 自定义ConfigurationCustomizer（可选）
     * <p>
     * 如果需要更细粒度的MyBatis配置，可以实现此方法<br>
     * 例如：配置类型处理器、语言驱动等
     * </p>
     *
     * @return ConfigurationCustomizer实例
     */
    /*
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 自定义配置
            log.debug("应用自定义MyBatis配置");
        };
    }
    */
}