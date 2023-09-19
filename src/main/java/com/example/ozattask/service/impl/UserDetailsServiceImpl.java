package com.example.ozattask.service.impl;

import com.example.ozattask.model.User;
import com.example.ozattask.model.Role;
import com.example.ozattask.repository.UserRepository;
import com.example.ozattask.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User with such username doesn't exit");
        }
        return user;
    }

    public void signup(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());
        if(userDB == null){
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    public User getAccountByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void create(User user) {
        userRepository.save(user);
    }
}
