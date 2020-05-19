package com.hd.beast.configs.datasources;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@MapperScan(basePackages = "com.hd.beast.mapper.mapper1",sqlSessionFactoryRef = "sqlSessionFactory1")
public class DatsSourceConfig1 {
    @Bean(name="dataSource1")
    // 表示这个数据源是默认数据源
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.data1",ignoreInvalidFields = true)
    public DataSource getDataSource1(){

        return DataSourceBuilder.create().build();
//        return DataSourceBuilder.create(dataSourceProperties.getClassLoader())
//                .type(HikariDataSource.class)
//                .driverClassName(dataSourceProperties.determineDriverClassName())
//                .username(dataSourceProperties.determineUsername())
//                .password(dataSourceProperties.determinePassword())
//                .url(dataSourceProperties.determineUrl())
//                .build();
    }

    @Bean(name="sqlSessionFactory1")
    @Primary
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("dataSource1")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapping/mapping1/*Mapper.xml"));
        return bean.getObject();
    }
    @Bean("sqlSessionTemplate1")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("sqlSessionFactory1") SqlSessionFactory sqlSessionFactory1){
       return new SqlSessionTemplate(sqlSessionFactory1);
    }
}
