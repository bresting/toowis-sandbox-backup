package com.toowis.oauth2.client.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.toowis.oauth2.client.model.AuthProvider;
import com.toowis.oauth2.client.model.Role;

import lombok.Data;

@Data
@Entity
@Table(name ="tb_user")
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    private long id;
    
    private String password;
    private String email;
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; 
    
}
