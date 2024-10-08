package com.repository;

import org.springframework.stereotype.Repository;

import com.model.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// use for finding user information in db
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    // check email
    Optional<UserInfo> findByEmail(String email);
    // check username or email
    Optional<UserInfo> findByUsernameOrEmail(String username, String email);
    // check username
    Optional<UserInfo> findByUsername(String username);
    // check if username exists
    Boolean existsByUsername(String username);
    // check if email exists
    Boolean existsByEmail(String email);
}
