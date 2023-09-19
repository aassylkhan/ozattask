package com.example.ozattask.controller;

import com.example.ozattask.dto.ChatDto;
import com.example.ozattask.service.ChatService;
import com.example.ozattask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ChatService chatService;

    @GetMapping()
    public String admin(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("chats", chatService.getChats());
        model.addAttribute("chatForm", new ChatDto());
        return "admin";
    }

    @PostMapping("/chat/add")
    public String createChat(@ModelAttribute ChatDto chatForm) {
        chatService.createChat(chatForm);
        return "redirect:/admin";
    }

    @DeleteMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        chatService.deleteChatMessageByUserId(userId);
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @DeleteMapping("/chat/delete/{chatId}")
    public String deleteChat(@PathVariable("chatId") Long chatId) {
        chatService.deleteChat(chatId);
        return "redirect:/admin";
    }
}