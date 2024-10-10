package com.repository;

import org.springframework.stereotype.Repository;

import com.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

// use for finding user information in db
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
