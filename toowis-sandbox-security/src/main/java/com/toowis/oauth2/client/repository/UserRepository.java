package com.toowis.oauth2.client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.toowis.oauth2.client.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    // @Table(name ="tb_user") => User @Query는 객체를 사용해야 한다.
    // @Query("select id, email, name, provider, role, provider FROM User WHERE email = :email")
    @Query
    Optional<User> findByEmail(String email);
}
