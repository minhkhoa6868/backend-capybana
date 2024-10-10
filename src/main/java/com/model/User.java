package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "username" }),
                @UniqueConstraint(columnNames = { "email" })
})
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String username;
        private String email;
        private String password;
        private Boolean isAdmin;

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Boolean getIsAdmin() {
                return isAdmin;
        }

        public void setIsAdmin(Boolean isAdmin) {
                this.isAdmin = isAdmin;
        }

}
