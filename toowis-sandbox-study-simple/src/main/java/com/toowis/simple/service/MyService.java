package com.toowis.simple.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.toowis.simple.mapper.SimpleMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyService {
    
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    private final SimpleMapper mapper;
    
    public void printDBTime() {
        String result = mapper.selectSimple();
        logger.info("{}", result);
    }
    
    public String getSomeDetail(String input) {
        String time = mapper.selectSimple();
        logger.info("요청시간 {}", time);
        
        return input + " - " + time;
    }
}
