package com.repository;

import java.util.Optional;

import com.model.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

// use for finding Role name in db
public interface RoleRepository extends JpaRepository<RoleInfo, Long> {
    
    Optional<RoleInfo> findByName(String name);
}
