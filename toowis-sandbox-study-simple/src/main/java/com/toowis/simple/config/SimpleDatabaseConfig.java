package com.toowis.simple.config;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.toowis.simple.annotation.DBXMapper;

@Configuration
@EnableTransactionManagement
@MapperScans({
    @MapperScan( basePackages = { "com.toowis.simple.mapper" }
        , annotationClass = Mapper.class
    ),
    @MapperScan( basePackages = { "com.toowis.simple.mapper" }
        , annotationClass = DBXMapper.class
        , sqlSessionFactoryRef  = SimpleDatabaseConfig.DBX_SQL_SESSION_FACTORY
    )
})
public class SimpleDatabaseConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(SimpleDatabaseConfig.class);
    
    SimpleDatabaseConfig() {
        logger.trace("init SimpleDatabaseConfig");
    }
    
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }
    
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(DataSource source) {
        return new DataSourceTransactionManager(source);
    }
    
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource source) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(source);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/com/toowis/simple/mapper/xml/*.xml")
        );
        return sessionFactory.getObject();
    }
    
    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
    
    // -----------------------------------------------------------------------------------------------------------------
    // 다중 DB설정
    // -----------------------------------------------------------------------------------------------------------------
    public static final String DBX_DATASOURCE           = "dataSourceDBX";
    public static final String DBX_TRANSACTION_MANAGER  = "transactionManagerDBX";
    public static final String DBX_SQL_SESSION_FACTORY  = "sessionFactoryDBX";
    public static final String DBX_SQL_SESSION_TEMPLATE = "sessionTemplateDBX";
    
    @Bean(name = DBX_DATASOURCE)
    @ConfigurationProperties(prefix = "datasource.dbx")
    public DataSource dataSourceDBX() {
        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }
    
    @Bean(name = DBX_TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManagerDBX(@Qualifier(DBX_DATASOURCE) DataSource source) {
        return new DataSourceTransactionManager(source);
    }
    
    @Bean(name = DBX_SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactoryDBX(@Qualifier(DBX_DATASOURCE) DataSource source) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(source);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/com/toowis/simple/mapper/xml/*.xml")
        );
        return sessionFactory.getObject();
    }
    
    @Bean(name = DBX_SQL_SESSION_TEMPLATE)
    public SqlSessionTemplate sqlSessionTemplateDBX(@Qualifier(DBX_SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
}
