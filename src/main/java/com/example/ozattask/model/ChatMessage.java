package com.example.ozattask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "chat_messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User sender;

    private String message;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Chat chat;
}
