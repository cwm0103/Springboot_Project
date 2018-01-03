package com.bom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by jindb on 2017/7/25.
 *
 * @author: jindb
 * @date: 2017/7/25 14:19
 * @description:
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("analysis/index");

        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }



}
