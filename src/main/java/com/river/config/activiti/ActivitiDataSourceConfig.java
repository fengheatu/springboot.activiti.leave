package com.river.config.activiti;

import com.alibaba.druid.pool.DruidDataSource;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author: he.feng
 * @date: 17:36 2017/12/7
 * @desc:
 **/
@Configuration
public class ActivitiDataSourceConfig extends AbstractProcessEngineAutoConfiguration {

    @Resource
    private ActivitiDataSourceProperties activitiDataSourceProperties;

    @Bean
    @Primary
    public DataSource activitiDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(activitiDataSourceProperties.getUrl());
        druidDataSource.setDriverClassName(activitiDataSourceProperties.getDriverClassName());
        druidDataSource.setUsername(activitiDataSourceProperties.getUsername());
        druidDataSource.setPassword(activitiDataSourceProperties.getPassword());
        return druidDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(activitiDataSource());
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(activitiDataSource());
        configuration.setTransactionManager(transactionManager());
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setJobExecutorActivate(true);
        return configuration;
    }
}
