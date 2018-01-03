package com.sbm;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 1).@SpringBootApplication标注启动配置入口，run()方法会创建一个Spring应用上下文(Application Context)。
 * SpringBoot通过启动内嵌的Servlet容器（默认tomcat）用来处理Http请求。
 * 2).@RestController是特殊的Controller，他的返回值直接作为Http Response的Body部分返回给浏览器
 * 3).Spring WebMvc框架会将Servlet容器里收到的Http请求根据路径分发到对应的@Controller下进行处理。
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan

//启动注解事务管理
@EnableTransactionManagement
@RestController
public class Applaction {
    public static void main(String[] args) {
        SpringApplication.run(Applaction.class, args);
    }
}
