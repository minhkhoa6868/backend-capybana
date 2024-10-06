package com.service;

import com.model.UserInfo;
import com.repository.UserRepository;

// import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    // user for interacting with database
    @Autowired
    private UserRepository userRepository;

    // value of usename is passed is function
    // after that if username appears, then it will return user detail (in this case is username and password)
    // after it will use this password to authenticate with password that user fills in
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // find username 
        Optional<UserInfo> user = userRepository.findByUsername(username);

        // if username appears in database -> return user detail
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }

    // public List<UserInfo> handleGetAllUser() {
    //     return this.userRepository.findAll();
    // }
}
