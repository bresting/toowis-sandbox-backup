package toowis.tip001;


import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 자동설정 정보
 * {@link org.springframework.boot.autoconfigure.SpringBootApplication}
 * /META-INF/spring.factories
 * 
 * org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
 * </pre>
 * <pre>
 * http://localhost:8080/factory
 * </pre>
 */
@SpringBootApplication
public class EmbeddedServletApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmbeddedServletApplication.class, args);
    }
    
    @Bean
    FilterRegistrationBean<CustomXFilter> xFilterRegistrationBean() {
        FilterRegistrationBean<CustomXFilter> bean = new FilterRegistrationBean<CustomXFilter>(new CustomXFilter());
        bean.setOrder(0);
        return bean;
    }
    
    @Bean
    FilterRegistrationBean<CustomYFilter> yFilterRegistrationBean() {
        FilterRegistrationBean<CustomYFilter> bean = new FilterRegistrationBean<CustomYFilter>(new CustomYFilter());
        bean.setOrder(1);
        return bean;
    }
    
    // 빈으로 만들어 처리
    // @Bean
    // public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sessionManagerCustomizer() {
    //     return factory -> factory.addContextCustomizers(context -> context.setSessionTimeout(24 * 60));
    // }
    // @Bean
    // public WebServerFactoryCustomizer<TomcatServletWebServerFactory> contextPath() {
    //     return factory -> factory.setContextPath("/factory");
    // }
    // @Bean
    // public WebServerFactoryCustomizer<TomcatServletWebServerFactory> factory() {
    //     return new ServletCustomizer();
    // }
}

/**
 * spring boot 2.x 변경됨
 * https://www.baeldung.com/embeddedservletcontainercustomizer-configurableembeddedservletcontainer-spring-boot
 * EmbeddedServletContainerCustomizer -> WebServerFactoryCustomizer
 */
@Component
class ServletCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.setContextPath("/factory");
    }
}

// @Component -> @Bean xFilterRegistrationBean에서 설정
class CustomXFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("▶ CustomXFilter init - {}", filterConfig);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("CustomXFilter do filter - {}, {}", request, response);
        chain.doFilter(request, response);
    }
    public void destroy() {
        logger.info("▶ CustomXFilter destroy");
    }
}

class CustomYFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("▶ CustomYFilter init - {}", filterConfig);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("CustomYFilter do filter - {}, {}", request, response);
        chain.doFilter(request, response);
    }
    public void destroy() {
        logger.info("▶ CustomYFilter destroy");
    }
}

@RestController
class GreetingsController {
    @RequestMapping("/greetings")
    public Map<String, String> greetings() {
        return Collections.singletonMap("greetings", "hello embedded servlet");
    }
}