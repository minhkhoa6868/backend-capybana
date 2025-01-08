package com.service;

import com.model.User;
import com.repository.UserRepository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public void handleDeleteAll() {
        this.userRepository.deleteAll();

    }

    public User fetchUserById(long id) {
        return this.userRepository.findById(id);
    }

    public List<User> fetchAllUser() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.fetchUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setUsername(reqUser.getUsername());
            currentUser.setPassword(reqUser.getPassword());
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public long handleGetUserIDByUsername(String username) {
        User alpha = this.userRepository.findByUsername(username);
        return alpha.getId();
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public boolean isUsernameExist(String email) {
        return this.userRepository.existsByUsername(email);
    }

}
