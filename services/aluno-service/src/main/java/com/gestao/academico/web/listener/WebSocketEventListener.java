package com.gestao.academico.web.listener;

import com.gestao.academico.web.dto.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);
    private final SimpMessagingTemplate template;
    public WebSocketEventListener(SimpMessagingTemplate template) { this.template = template; }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor != null ? accessor.getSessionId() : "unknown";
        log.info("WebSocket conectado, sessionId={}", sessionId);
        template.convertAndSend("/topic/public", new ChatMessage("SYSTEM","system","Um usuário conectou"));
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Object usernameObj = (accessor != null && accessor.getSessionAttributes() != null)
                ? accessor.getSessionAttributes().get("username") : null;
        String username = usernameObj != null ? usernameObj.toString() : "desconhecido";
        log.info("Sessão desconectada, username={}, sessionId={}", username, accessor != null ? accessor.getSessionId() : "unknown");
        template.convertAndSend("/topic/public", new ChatMessage("LEAVE", username, username + " saiu"));
    }
}
