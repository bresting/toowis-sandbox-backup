package com.toowis.simple.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyServiceTest {
    
    @Autowired
    private MyService myService;
    
    @Test
    public void testService() {
        String someDetail = myService.getSomeDetail("value");
        System.out.println(someDetail);
    }
}
