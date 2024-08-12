package com.gotocode.ai.controller;

import com.gotocode.ai.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatbot) {
        this.chatService = chatbot;
    }

    @GetMapping("/ask")
    public String askQuestion(@RequestParam String question) {
        // Chama o método chat do Chatbot passando a pergunta
        return chatService.getResponse(question);
    }

}
