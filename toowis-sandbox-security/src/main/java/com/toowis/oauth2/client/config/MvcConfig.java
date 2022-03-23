package com.toowis.oauth2.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /**
         * http://localhost:7070/public/page01                           ┌----registry.addViewController("/").setViewName("public/index");
         *                       -------------                          ---
         *                             └------ HttpSecurity antMatchers("/", /"default/error", "/public/**")
         *                                                                   -----------------  -----------
         *                               ┌------------------------------------------┘OK             └-------┐
         *                             ----------------                                                     |
         *  registry.addViewController("/default/error").setViewName("public/4xx");                         |
         *                                                          -------------   ┌----------┐            |
         *                                                                └---------┘     --------------    |
         *                                                            resources/templates/public/4xx.html   |
         *                                                                                                  |
         *                                                        ┌-----------------------------------------┘
         *                                      Controller -----------------
         *                                      @GetMapping("/public/{page}")
         *                                      public String publicPage(@PathVariable String page) {
         *                                          return "public/" + page;
         *                                      }
         * 
         * 접근 안되면 HttpSecurity 패턴확인
         * 
         * addViewController는 컨트롤과 동일하다.
         * @GetMapping("/public/error") String method() { return "public/403" }
         */
        registry.addViewController("/").setViewName("public/index");
        registry.addViewController("/default/error").setViewName("public/4xx");
    }
}