package com.toowis.simple.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyRestControllerTest {
    
    @LocalServerPort
    private int port;
    
    private WebClient webClient;
    
    @BeforeEach
    protected void setUp() throws Exception {
        
        System.out.println("실행포트 : " + port);
        
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    public void testGetRest() {
        String block = webClient.get().uri("/get").retrieve().bodyToMono(String.class).block();
        System.out.println(block);
    }
}
