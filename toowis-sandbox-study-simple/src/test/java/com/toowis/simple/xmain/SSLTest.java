package com.toowis.simple.xmain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SSLTest {
    
    public static SSLContext sslContext(String keystoreFile, String password) throws GeneralSecurityException, IOException {
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream in = new FileInputStream(keystoreFile)) {
            keystore.load(in, password.toCharArray());
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, password.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        return sslContext;
    }
    
    public static void main(String[] args) throws Exception {
        
        SSLContext ssl = SSLTest.sslContext("C:/neo-edition/tools/jdk-11.0.14.1+1/lib/security/cacerts", "changeit");
        HttpsURLConnection.setDefaultSSLSocketFactory(ssl.getSocketFactory());
        
        
        URL u = new URL("https://apim-gateway.lfcorp.com");
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        
        System.out.println(conn.getResponseCode());
    }
}
