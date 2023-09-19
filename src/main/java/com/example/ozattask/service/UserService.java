package com.example.ozattask.service;

import com.example.ozattask.dto.UserDto;
import com.example.ozattask.model.User;
import com.example.ozattask.repository.UserRepository;
import com.example.ozattask.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsService;

    public List<UserDto> getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User admin = userDetailsService.getAccountByUsername(username);
        List<User> users = userRepository.findAll();
        users.remove(admin);

        return users.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build()).collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDto getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userDetailsService.getAccountByUsername(username);

        return currentUser != null ? UserDto.builder()
                .lastName(currentUser.getLastName())
                .firstName(currentUser.getFirstName())
                .username(currentUser.getUsername())
                .build() : null;
    }
}
