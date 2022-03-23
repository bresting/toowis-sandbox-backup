package com.toowis.oauth2.client.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    public void processOAuthPostLogin(String username) {
        System.out.println("사용자 처리..." + username);
    }
}
