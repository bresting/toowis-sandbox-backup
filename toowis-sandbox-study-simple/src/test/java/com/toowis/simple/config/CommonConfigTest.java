package com.toowis.simple.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommonConfigTest {

    @Test
    @DisplayName("암호화복호화")
    public void 암호화복호화Test() throws Exception {
        String username = "postgres";
        
        String salt = "key";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(salt);
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setStringOutputType("base64");
        
        String encryptValue = encryptor.encrypt(username);
        String decryptValue = encryptor.decrypt(encryptValue);
        
        System.out.println(encryptValue);
        System.out.println(decryptValue);
    }
}
