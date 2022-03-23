package com.toowis.oauth2.client.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.toowis.oauth2.client.domain.CustomOAuth2User;
import com.toowis.oauth2.client.service.CustomOAuth2UserService;
import com.toowis.oauth2.client.service.CustomUserDetailsService;
import com.toowis.oauth2.client.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
//@PropertySource("classpath:application.yml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    /**
     * ClientRegistration + CommonOAuth2Provider 자동 주인하지 않고 개발자 직접 구현하는 방법
     * 
     * ClientRegistration
     * org.springframework.security.oauth2.client.registration.ClientRegistrationRepository#findByRegistrationId
     * └- org.springframework.security.oauth2.client.registration.ClientRegistration#getRegistrationId
     * 
     * org.springframework.security.config.oauth2.client.CommonOAuth2Provider
     * 
     * @EnableAutoConfiguration(exclude = { WebMvcAutoConfiguration.class }) Application 설정
     * @EnableAutoConfiguration(exclude = { WebMvcAutoConfiguration.class }) Application 설정
     * 
     * HttpSecurity
     * .clientRegistrationRepository(clientRegistrationRepository())
     * .authorizedClientService(authorizedClientService())
     * 
     * 참조
     * https://godekdls.github.io/Spring%20Security/oauth2/
     * 
     * @Configuration
     * public class OAuth2LoginConfig {
     *     @Bean
     *     public ClientRegistrationRepository clientRegistrationRepository() {
     *         return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
     *     }
     * 
     *     private ClientRegistration googleClientRegistration() {
     *         return ClientRegistration.withRegistrationId("google")
     *             .clientId("google-client-id")
     *             .clientSecret("google-client-secret")
     *             .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
     *             .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
     *             .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
     *             .scope("openid", "profile", "email", "address", "phone")
     *             .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
     *             .tokenUri("https://www.googleapis.com/oauth2/v4/token")
     *             .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
     *             .userNameAttributeName(IdTokenClaimNames.SUB)
     *             .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
     *             .clientName("Google")
     *             .build();
     *     }
     * }
     * 
     * -----------------------------------------------------------------------------------------------------------------
     *
     * private final Environment env;
     * private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
     * private static List<String> clients = Arrays.asList("google", "kakao");
     * 
     * @Bean
     * public ClientRegistrationRepository clientRegistrationRepository() {
     *     List<ClientRegistration> registrations = clients.stream()
     *             .map(c -> getRegistration(c))
     *             .filter(registration -> registration != null)
     *             .collect(Collectors.toList());
     *     
     *     return new InMemoryClientRegistrationRepository(registrations);
     * }
     * 
     * private ClientRegistration getRegistration(String client){
     *     
     *     // API Client Id 불러오기
     *     String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");
     *     
     *     // API Client Id 값이 존재하는지 확인하기
     *     if (clientId == null) {
     *         return null;
     *     }
     *     
     *     // API Client Secret 불러오기
     *     String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-secret");
     *     
     *     switch (client) {
     *     case "google":
     *         return CommonOAuth2Provider.GOOGLE.getBuilder(client)
     *                 .clientId(clientId)
     *                 .clientSecret(clientSecret)
     *                 .build();
     *     case "kakao":
     *         return AdditionalOAuth2Provider.KAKAO.getBuilder(client)
     *                 .clientId(clientId)
     *                 .clientSecret(clientSecret)
     *                 .scope("profile_nickname")
     *                 .build();
     *     default:
     *         return null;
     *     }
     * }
     * 
     * @Bean
     * public OAuth2AuthorizedClientService authorizedClientService() {
     *     return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
     * }
     */
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Autowired private CustomOAuth2UserService customOAuth2UserService;
    @Autowired private UserService userService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        logger.info("invoke configure....");
        
        http.authorizeRequests()
            .antMatchers("/", "/login", "/public/**", "/default/error").permitAll()
            .anyRequest().authenticated()
            .and().logout().logoutSuccessUrl("/login").permitAll()
            .and().exceptionHandling().accessDeniedPage("/public/4xx")
            .and()
            .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("email")     // ID field name
                .passwordParameter("pass" )     // PW field name
                .defaultSuccessUrl("/private/user")
            .and()
            .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {
                        
                        logger.info("처리 성공 했습니다.\n\n{}", authentication.getName());
                        logger.info("{}", authentication.getDetails());
                        logger.info("{}", authentication.getPrincipal());
                        
                        // OAuth2User의 Attribute DTO로 만들었음
                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                        
                        // Service 호출
                        userService.processOAuthPostLogin(oauthUser.getName());
                        
                        response.sendRedirect("/private/user");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                            AuthenticationException exception) throws IOException, ServletException {
                        logger.error("처리 실패 했습니다.\n\n{}", exception);
                    }
                })
            ;

    }
}
