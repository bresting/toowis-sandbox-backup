package com.toowis.simple.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 기본값 jasyptStringEncryptor
 * com.ulisesbocchio.jasyptspringboot.configuration.EncryptablePropertyResolverConfiguration
 * 
 * 변경할 경우 application.yml 에 설정
 * jasypt:
 *   encryptor:
 *     bean: jasyptStringEncryptor
 *     
 * @Bean("jasyptStringEncryptor")
 */
@Configuration
public class CommonConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
        
        // TODO VM Option -Djasypt.encryptor.password=KeyValue
        // System.getProperty("jasypt.encryptor.password")
        String key = "key";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPoolSize("1"); // 인스턴스 pool
        encryptor.setConfig(config);
        return encryptor;
    }
}
