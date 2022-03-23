package com.toowis.simple.util;

import org.junit.jupiter.api.Test;

public class CryptoUtilTest {

    @Test
    public void testEncryptAES256() {
        String plainText = "비밀번호";
        String key = "secretKey";
        String encrypted = CryptoUtil.encryptAES256(plainText, key);

        System.out.println("MD5       : " + plainText + " - " + CryptoUtil.md5(plainText));
        System.out.println("SHA-256   : " + plainText + " - " + CryptoUtil.sha256(plainText));
        System.out.println("AES256 ENC: " + encrypted);
        System.out.println("AES256 DEC: " + CryptoUtil.decryptAES256(encrypted, key));
    }

}
