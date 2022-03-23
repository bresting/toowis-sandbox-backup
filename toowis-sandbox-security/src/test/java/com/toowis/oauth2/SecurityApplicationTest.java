package com.toowis.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecurityApplicationTest {

    @Autowired
    private DataSource ds;
    
    @Test
    @DisplayName("DB연결확인")
    public void testDatasource() {
        
        System.out.println(ds);
        
        try (Connection conn = ds.getConnection()) {
            assertThat(conn).isInstanceOf(Connection.class);
            
            System.out.println(getLong(conn, "select 10 + 10"));
            assertThat(20).isEqualTo(getLong(conn, "select 10 + 10"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long getLong(Connection conn, String sql) {
        long result = -1;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rest = stmt.executeQuery(sql);
            if (rest.next()) {
                result = rest.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
