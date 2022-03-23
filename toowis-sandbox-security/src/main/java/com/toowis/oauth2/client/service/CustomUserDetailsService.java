package com.toowis.oauth2.client.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.toowis.oauth2.client.domain.User;
import com.toowis.oauth2.client.repository.UserMapper;
import com.toowis.oauth2.client.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // TODO 테스트
        // if ("bresting@gmail.com".equals(username)) {
        //     User user = new User();
        //     user.setId(1);
        //     user.setPassword("$2a$10$E2y9hpP.ymhrJn9DBehwP.h2Ta4w.7JlOCFJKWNQpwjEj.ZR3H2uq");
        //     user.setEmail("bresting@gmail.com");
        //     user.setName("사용자명");
        //     user.setProvider(AuthProvider.LOCAL);
        //     user.setRole(Role.ROLE_USER);
        //     return new MyUserDetails(user);
        // }
        
        // Mybatis
        Optional<User> userMaper = userMapper.findByEmail(username);
        userMaper.ifPresent( t -> System.out.println( "Mybatis : " + t ) );
        
        // JPA
        Optional<User> user = userRepository.findByEmail(username);
        user.ifPresent( t -> System.out.println( "JPA : " + t ) );
        
        if ( user.isEmpty() ) {
            throw new UsernameNotFoundException("Could not find user");
        }
        
        // TODO 테스트
        return new MyUserDetails(user.get());
    }
    
    public static class MyUserDetails implements UserDetails {
        
        private static final long serialVersionUID = 1L;
        
        private User user;
        
        public MyUserDetails(User user) {
            this.user = user;
        }
        
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
            
            //Set<Role> roles = user.getRoles();
            // List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            // for (Role role : roles) {
            //     authorities.add(new SimpleGrantedAuthority(role.name()));
            // }
            
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}

