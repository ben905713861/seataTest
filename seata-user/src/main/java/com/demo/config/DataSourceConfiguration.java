package com.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import io.seata.rm.datasource.DataSourceProxy;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据源代理
 * @author wangzhongxiang
 */
@Configuration
@MapperScan("com.demo.mapper")
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource originDataSource() {
        return new DruidDataSource();
    }

    /**
     * seata数据源代理，必须加@Primary
     * @param originDataSource
     * @return
     */
    @Bean
    @Primary
    public DataSourceProxy dataSourceProxy(@Qualifier("originDataSource") DataSource originDataSource) {
        return new DataSourceProxy(originDataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public MybatisSqlSessionFactoryBean
        sqlSessionFactoryBean(@Qualifier("dataSourceProxy") DataSource dataSourceProxy) {
        // 这里用 MybatisSqlSessionFactoryBean 代替了 SqlSessionFactoryBean，否则 MyBatisPlus 不会生效
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSourceProxy);
        return mybatisSqlSessionFactoryBean;
    }

}
