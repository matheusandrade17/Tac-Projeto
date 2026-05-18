package com.gestao.academico.web.controller;

import com.gestao.academico.web.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage send(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        if ("JOIN".equalsIgnoreCase(message.type())) {
            headerAccessor.getSessionAttributes().put("username", message.sender());
        }
        return message;
    }
}

