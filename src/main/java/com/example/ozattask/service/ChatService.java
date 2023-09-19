package com.example.ozattask.service;

import com.example.ozattask.dto.ChatDto;
import com.example.ozattask.dto.ChatFullInfoDto;
import com.example.ozattask.dto.ChatMessageDto;
import com.example.ozattask.dto.SendMessageRequestDto;
import com.example.ozattask.dto.UserDto;
import com.example.ozattask.model.Chat;
import com.example.ozattask.model.ChatMessage;
import com.example.ozattask.model.User;
import com.example.ozattask.repository.ChatMessageRepository;
import com.example.ozattask.repository.ChatRepository;
import com.example.ozattask.repository.UserRepository;
import com.example.ozattask.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public List<ChatDto> getChats() {
        return chatRepository.findAll().stream().map(chat -> ChatDto.builder()
                .id(chat.getId())
                .title(chat.getTitle())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    public void deleteChat(Long chatId) {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        if (chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            chat.getChatMessages().forEach(chatMessage -> chatMessageRepository.delete(chatMessage));
            chatRepository.delete(chat);
        }
    }


    @Transactional
    public void deleteChatMessageByUserId(Long userId) {
        chatMessageRepository.deleteBySenderId(userId);
    }

    @Transactional
    public ChatFullInfoDto getChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        return ChatFullInfoDto.builder()
                .chatId(chat.getId())
                .chatMessages(chat.getChatMessages().stream().map(chatMessage -> ChatMessageDto.builder()
                        .chatMessageId(chatMessage.getId())
                        .message(chatMessage.getMessage())
                        .sender(
                                UserDto.builder()
                                        .id(chatMessage.getSender().getId())
                                        .username(chatMessage.getSender().getUsername())
                                        .firstName(chatMessage.getSender().getFirstName())
                                        .lastName(chatMessage.getSender().getLastName())
                                        .build()
                        )
                        .build()).collect(Collectors.toList()))
                .title(chat.getTitle())
                .build();
    }

    @Transactional
    public void sendMessageToChat(SendMessageRequestDto messageRequestDto, Long chatId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User sender = userDetailsService.getAccountByUsername(username);

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        ChatMessage chatMessage = ChatMessage.builder()
                .chat(chat)
                .sender(sender)
                .message(messageRequestDto.getMessage())
                .build();

        chatMessageRepository.save(chatMessage);
    }

    public void createChat(ChatDto chatForm) {
        chatRepository.save(
                Chat.builder()
                        .title(chatForm.getTitle())
                        .build()
        );
    }

}
