package com.toowis.oauth2.client.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.toowis.oauth2.client.domain.User;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM TB_USER WHERE EMAIL = #{email}")
    Optional<User> findByEmail(@Param("email") String email);
    
}
