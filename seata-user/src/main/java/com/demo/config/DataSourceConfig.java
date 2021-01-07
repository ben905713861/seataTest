/// **
// * @author 作者: Ben
// * @date 创建日期: 2021年1月7日
// * @description
// */
//
// package com.demo.config;
//
// import javax.sql.DataSource;
//
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Primary;
//
// import com.alibaba.druid.pool.DruidDataSource;
// import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//
// @Configuration
// public class DataSourceConfig {
//
// @Bean
// @ConfigurationProperties(prefix = "spring.datasource")
// public DataSource druidDataSource() {
// return new DruidDataSource();
// }
//
// @Bean
// public MybatisSqlSessionFactoryBean
// sqlSessionFactoryBean(@Qualifier("druidDataSource") DataSource druidDataSource) {
// System.err.println(druidDataSource.getClass());
// MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
// mybatisSqlSessionFactoryBean.setDataSource(druidDataSource);
// return mybatisSqlSessionFactoryBean;
// }
//
// }
