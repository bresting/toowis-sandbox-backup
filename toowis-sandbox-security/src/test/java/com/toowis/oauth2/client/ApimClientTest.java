package com.toowis.oauth2.client;

import javax.net.ssl.SSLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

// https://codingtim.github.io/webclient-testing/
// https://www.programcreek.com/java-api-examples/?code=OpenAPITools%2Fopenapi-generator%2Fopenapi-generator-master%2Fsamples%2Fclient%2Fpetstore%2Fjava%2Fwebclient%2Fsrc%2Fmain%2Fjava%2Forg%2Fopenapitools%2Fclient%2FApiClient.java#
@ExtendWith(MockitoExtension.class)
public class ApimClientTest {
    
    @BeforeEach
    void setUp() throws Exception {
        //orderApiClient = new OrderApiClient( new ClientConfig().webClient() );
    }
    
    @Test
    @DisplayName("토큰발급")
    public void getToken() {
        HttpClient httpClient = HttpClient.create().secure(t -> {
            try {
                t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build());
            } catch (SSLException e) {
                e.printStackTrace();
            }
        });
        
        // HttpClient httpClient = HttpClient.create().secure(reactor.netty.tcp.SslProvider.defaultClientProvider());
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type"   , "client_credentials"          );
        params.add("client_id"    , "vf6LzfxduLEiX8xZV8t9SFpsyisa");
        params.add("client_secret", "csfWgc8I7f3AWNqQTbeHYzwqQiQa");
        
        //.create("https://apim-gateway.lfcorp.com")
        String token = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://apim-gateway.lfcorp.com")
                .build()
                .post()
                .uri("/token")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(params) )
                .retrieve()
                .toEntity(String.class)
                .block()
                .getBody()
            ;
        
        System.out.println(token);
    }
    
    
    public static class MyJson {
        public String name;
        public Integer age;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
        @Override
        public String toString() {
            return "MyJson [name=" + name + ", age=" + age + "]";
        }
        
    }
}
