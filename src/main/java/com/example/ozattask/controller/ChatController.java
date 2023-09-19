package com.example.ozattask.controller;

import com.example.ozattask.dto.SendMessageRequestDto;
import com.example.ozattask.service.ChatService;
import com.example.ozattask.service.UserService;
import com.example.ozattask.util.AvatarGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private AvatarGenerator avatarGenerator;

    @GetMapping("/{chatId}")
    public String getChat(@PathVariable Long chatId, Model model) {
        model.addAttribute("signedUser", userService.getCurrentUser());
        model.addAttribute("chatInfo", chatService.getChat(chatId));
        model.addAttribute("messageRequestDto", new SendMessageRequestDto());

        // Avatar
        String username = String.valueOf(userService.getCurrentUser().getUsername());
        String avatarStyle = AvatarGenerator.generateAvatar(username);
        model.addAttribute("avatarStyle", avatarStyle);

        return "chat";
    }

    @PostMapping("/message")
    public String sendMessage(@ModelAttribute SendMessageRequestDto messageRequestDto,  @ModelAttribute("chatId") Long chatId) {
        chatService.sendMessageToChat(messageRequestDto, chatId);
        return "redirect:/chat/" + chatId;
    }
}
