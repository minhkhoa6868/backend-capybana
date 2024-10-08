package com.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

// user information that will store in database
@Setter
@Getter
@Entity
@Table(name = "roles")
public class RoleInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String name;
}
