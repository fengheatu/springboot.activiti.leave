package com.river;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: he.feng
 * @date: 13:40 2017/12/7
 * @desc: spring boot 启动类
 **/
@ComponentScan
@Configuration
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@EnableTransactionManagement
@MapperScan(basePackages = "com.river.dao.mapper")
public class ServletInitializer  extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServletInitializer.class,args);
    }
}
