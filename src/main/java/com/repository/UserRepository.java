package com.repository;

import org.springframework.stereotype.Repository;

import com.model.UserInfo;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// use for interact with database
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    @Bean
    Optional<UserInfo> findByUsername(String username); // Query to find user by username in database
}
