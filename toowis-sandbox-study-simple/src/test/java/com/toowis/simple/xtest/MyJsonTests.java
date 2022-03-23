package com.toowis.simple.xtest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.toowis.simple.dto.SimpleDto;

@JsonTest
public class MyJsonTests {
    
    private static final Logger logger = LoggerFactory.getLogger(MyJsonTests.class);
    
    @Autowired
    private JacksonTester<SimpleDto> json;
    
    @Test
    public void testSerialize() throws Exception {
        SimpleDto simple = new SimpleDto("test", "test@email.com", 5);
        logger.info("{}", json.write(simple).getJson());
        assertThat(json.write(simple)).hasJsonPathStringValue("@.email");
        assertThat(json.write(simple)).extractingJsonPathValue("@.email").isEqualTo("test@email.com");
    }
    
    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"name\":\"test\",\"email\":\"test@email.com\",\"age\":5}";
        
        assertThat(this.json.parse(content)).isEqualTo(new SimpleDto("test", "test@email.com", 5));
        assertThat(this.json.parseObject(content).getEmail()).isEqualTo("Ford");
    }
}
