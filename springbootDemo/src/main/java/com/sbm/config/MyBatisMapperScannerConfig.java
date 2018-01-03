package com.sbm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chenwangming on 2017/12/22.
 */
@Configuration
@MapperScan("com.sbm.domain.*.dao")
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.sbm.domain");
        return mapperScannerConfigurer;
    }
}
