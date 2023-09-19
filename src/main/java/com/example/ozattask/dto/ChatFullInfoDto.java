package com.example.ozattask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatFullInfoDto {

    private Long chatId;

    private String title;

    private List<ChatMessageDto> chatMessages;
}
