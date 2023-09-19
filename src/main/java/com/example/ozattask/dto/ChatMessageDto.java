package com.example.ozattask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private Long chatMessageId;

    private UserDto sender;

    private String message;
}
