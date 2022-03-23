package com.toowis.simple.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

/**
 * <pre>
 *  @ContextConfiguration( locations = { "file:src/main/webapp/WEB-INF/applicationContext.xml" } )
 *  src/main/webapp/WEB-INF/applicationContext.xml
 *  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
 *      <property name="dataSource" ref="dataSource"/>
 *      <property name="configLocation" value="classpath:mybatis-config.xml"/>
 *      <property name="mapperLocations" value="classpath:/mappers/*.xml"/>
 *  </bean>
 *  <bean id="boardMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
 *      <property name="mapperInterface" value="com.freeboard02.domain.board.BoardMapper"/>
 *      <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
 *  </bean>
 * 
 * @ExtendWith(SpringExtension.class)
 * @Transactional
 * @Rollback(value = false)
 * </pre>
 * 
 * ---------------------------------------------------------------------------------------------------------------------
 * 
 * <pre>
 * JUnit5 사용 시 작성, MybatisTest 2.0.1버전 이상에서 생략 가능
 * @ExtendWith(SpringExtension.class)
 * JUnit4 사용 시 작성
 * @RunWith(SpringRunner.class)
 * 
 * @MybatisTest 어노테이션 추가
 * 
 * 실 데이터베이스에 연결 시 필요한 어노테이션
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 *
 * @see https://wave1994.tistory.com/181
 * @see https://plz-exception.tistory.com/28
 * </pre>
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ImportAutoConfiguration( classes = {
        com.toowis.simple.config.CustomDatabaseConfig.class
      , com.toowis.simple.config.CustomPropertySource.class
})
@MybatisTest
public class SimpleMapperTest {
    
    @Autowired private SimpleMapper simpleMapper;
    
    @Test
    @DisplayName("단순조회")
    public void testSelectSimple() {
        String result = simpleMapper.selectSimple();
        System.out.println(result);
    }

    @Test
    @DisplayName("코드목록조회")
    public void testSelectListCommonCode() {
        List<Map<String, Object>> resultList =  simpleMapper.selectListCommonCode("DEV_CODE");
        System.out.println(resultList);
    }
    
    @Test
    @DisplayName("코드수정")
    public void testUpdateCommonCode2() {
        String time = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss").format(LocalDateTime.now());
        simpleMapper.updateCommonCode("이클립스_개발툴 > " + time);
    }
}
