package com.bom.config;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by jindb on 2017/6/29.
 */
@Configuration
public class MyBatisConfig {
    @Autowired
    private Environment env;
    /**
     * 创建数据源
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     */
//    @Bean
//    //@Primary
//    public DataSource getDataSource() throws Exception{
//        Properties props = new Properties();
//        props.put("driverClassName", env.getProperty("spring.datasource.driver-class-name"));
//        props.put("url", env.getProperty("spring.datasource.url"));
//        props.put("username", env.getProperty("spring.datasource.username"));
//        props.put("password", env.getProperty("spring.datasource.password"));
//        return DruidDataSourceFactory.createDataSource(props);
//    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource mybatisConfigXml = resolver.getResource("classpath:mybatis/mybatis-config.xml");
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage("com.bom.domain.*.model");//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis_mysql/*/*.xml"));//指定xml文件位置
        return fb.getObject();
    }
}