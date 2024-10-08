package com.service;

import com.model.UserInfo;
import com.repository.UserRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Collections;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        // find username or email in database
        UserInfo user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                 .orElseThrow(() ->
                         new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        // check which user is in what role and create a permission for that role
        Set<GrantedAuthority> authorities = user
            .getRoles() != null ? user.getRoles().stream()
            .map((role) -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toSet()) : Collections.emptySet();

        // return user detail and permission
        return new User(user.getUsername(),
                user.getPassword(),
                authorities);
    }
}
