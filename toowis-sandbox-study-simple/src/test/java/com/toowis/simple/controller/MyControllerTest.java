package com.toowis.simple.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = MyController.class)
@AutoConfigureMybatis
@ImportAutoConfiguration( classes = {
        com.toowis.simple.config.CustomDatabaseConfig.class
      , com.toowis.simple.config.CustomPropertySource.class
})
public class MyControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @BeforeEach
    public void setUp(WebApplicationContext applicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 인코딩 필터 추가
                .alwaysDo(print())
                .build();
    }
    
    @Test
    @DisplayName("컨트롤러 테스트")
    public void testGetPage01() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get("/page01"))
        .andExpect(status().isOk());
    }
}
