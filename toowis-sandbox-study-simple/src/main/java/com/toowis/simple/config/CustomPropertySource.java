package com.toowis.simple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource(value = "file:other/extra.properties", ignoreResourceNotFound = true)
})
public class CustomPropertySource {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomPropertySource.class);
    
    @Value("${datasource.dbx.driver-class-name}")
    private String driverClassName;
    
    @Value("${datasource.dbx.jdbc-url}")
    private String url;
    
    @Value("${datasource.dbx.username}")
    private String username;
    
    @Value("${datasource.dbx.password}")
    private String password;
 
    @Value("${system.key}")
    private String key;
    
    public String getDriverClassName() {
        return driverClassName;
    }
 
    public String getUrl() {
        return url;
    }
 
    public String getUsername() {
        return username;
    }
 
    public String getPassword() {
        // String password = CryptoUtil.decryptAES256(password, key);
        logger.debug("key [{}]", key);
        logger.debug("driverClassName: [{}], url: [{}], username:[{}], password[{}]"
                , driverClassName
                , url
                , username
                , password
                );

        return password;
    }
}