package com.example.ozattask.controller;

import com.example.ozattask.dto.UserDto;
import com.example.ozattask.service.ChatService;
import com.example.ozattask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final ChatService chatService;
    private final UserService userService;

    @GetMapping()
    public String homePage(Model model) {
        model.addAttribute("chats", chatService.getChats());
        UserDto currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("signedUser", currentUser);
        }
        return "home";
    }

    @GetMapping("/login")
    public String authorization() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/logout")
    public String logOut() {
        return "home";
    }

}