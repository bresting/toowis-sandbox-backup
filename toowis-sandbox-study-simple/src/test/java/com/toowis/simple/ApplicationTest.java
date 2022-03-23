package com.toowis.simple;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
public class ApplicationTest {
    
    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoad() throws Exception{
        System.out.println("Hikari Datasource ============================================================");
        System.out.println(">>>>> " + dataSource.getConnection().getMetaData().getURL());
        System.out.println("Hikari Datasource ============================================================");
    }
}
