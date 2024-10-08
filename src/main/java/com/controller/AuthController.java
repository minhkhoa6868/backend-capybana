package com.controller;

import com.dto.LoginDto;
import com.dto.SignupDto;
import com.model.RoleInfo;
import com.model.UserInfo;
import com.repository.RoleRepository;
import com.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> LoginUser(@RequestBody LoginDto loginDto) {
        try {
            // Attempt to authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

            // Set the authentication in the security context if successful
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>("User signed in successfully!", HttpStatus.OK);

        } catch (UsernameNotFoundException ex) {
            // Handle case when the user is not found
            return new ResponseEntity<>("User not found with username or email.", HttpStatus.NOT_FOUND);

        } catch (BadCredentialsException ex) {
            // Handle case when the password is incorrect
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);

        } catch (Exception ex) {
            // Catch any other exceptions that may occur
            return new ResponseEntity<>("An error occurred during login. Please try again.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> SignupUser(@RequestBody SignupDto signupDto) {

        // add check for username exists in a DB
        if (userRepository.existsByUsername(signupDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        UserInfo user = new UserInfo();
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        // fetch the role and ensure it's managed
        RoleInfo role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // ensure that user has a single role
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}
