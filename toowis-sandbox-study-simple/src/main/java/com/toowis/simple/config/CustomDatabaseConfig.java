package com.toowis.simple.config;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariDataSource;

/**
 * property를 직접 핸들링 하는 방법
 * @author Administrator
 */
@Configuration
@MapperScan(basePackages = { "com.toowis.simple.mapper" }
    , annotationClass = Mapper.class
    , sqlSessionFactoryRef = "customSessionFactory"
    , sqlSessionTemplateRef = "customSessionTemplate"
)
public class CustomDatabaseConfig {
    
    private final CustomPropertySource customPropertySource;
    
    public CustomDatabaseConfig(CustomPropertySource customPropertySource) {
        this.customPropertySource = customPropertySource;
    }
    
    @Bean(name="customDataSource")
    public DataSource customDataSource() {
        HikariDataSource ds = DataSourceBuilder.create().type(HikariDataSource.class)
                .url(customPropertySource.getUrl())
                .driverClassName(customPropertySource.getDriverClassName())
                .username(customPropertySource.getUsername())
                .password(customPropertySource.getPassword())
                .build();
        
        ds.setMaximumPoolSize(1);
        ds.setMinimumIdle(1);
        
        return ds;
    }
    
    @Bean(name = "customSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("customDataSource") DataSource source) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(source);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/com/toowis/simple/mapper/xml/*.xml"));
        return sessionFactory.getObject();
    }
    
    @Bean(name = "customSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("customSessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
}