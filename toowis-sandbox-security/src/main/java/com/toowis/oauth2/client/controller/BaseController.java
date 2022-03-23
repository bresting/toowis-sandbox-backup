package com.toowis.oauth2.client.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.toowis.oauth2.client.domain.CustomOAuth2User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BaseController {
    
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    
    @GetMapping("/public/{page}")
    public String publicPage(@PathVariable String page) {
        return "public/" + page;
    }
    
    @GetMapping("/private/user")
    public String user() {
        
        // 사용자 정보 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if ( authentication.getPrincipal() instanceof com.toowis.oauth2.client.service.CustomUserDetailsService.MyUserDetails ) {
            return "private/user";
        }
        
        // UsernamePasswordAuthenticationToken [Principal=com.toowis.oauth2.client.service.CustomUserDetailsService$MyUserDetails@10c53090, Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=CD44096CFAF56CB9AA889DB7796808D5], Granted Authorities=[ROLE_USER]]
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        
        logger.info("getAuthentication : {}", authentication);
        logger.info("getName : {}", authentication.getName());
        logger.info("getCredentials : {}", authentication.getCredentials());
        logger.info("getDetails : {}", authentication.getDetails());
        logger.info("-------");
        logger.info("> {}", user.getAttributes());
        
        CustomOAuth2User custom = (CustomOAuth2User) authentication.getPrincipal();
        logger.info(" getEmail : {}", custom.getEmail());
        
        return "private/user";
    }
    
    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    
    private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;
   
    /**
     * ClientRegistrationRepository을 login.htm VIEW에서 Oauth2 조립한다.
     * @return
     */
    @GetMapping("/login")
    public String login(Model model) {
        
        // ClientRegistrationRepository 주입 받아 처리
        // private final ClientRegistrationRepository clientRegistrationRepository;
        // Iterable<ClientRegistration> clientRegistrations = null;
        // ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        // if ( type != ResolvableType.NONE
        //   && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
        //     clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        // }
        // clientRegistrations.forEach( registration ->
        //     oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId() )
        // );
        
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
        
        inMemoryClientRegistrationRepository.forEach(registration -> {
            oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId() );
        } );
        
        model.addAttribute("oauth2UrlMap", oauth2AuthenticationUrls);
        
        return "login";
    }
    
}

