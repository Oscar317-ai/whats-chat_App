package com.example.e_chat.config;

import com.example.e_chat.chat.ChatMessage;
import com.example.e_chat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component  // Indicates that this is a Spring-managed component
@RequiredArgsConstructor  // Generates a constructor with required fields (final)
@Slf4j  // Lombok annotation for logging
public class WebSocketEventListener {

	private final SimpMessageSendingOperations messageTemplate;  // Used to send messages

	@EventListener  // Listens for WebSocket events
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");

		if (username != null) {
			log.info("User Disconnected: {}", username);  // Log the disconnection
			var chatMessage = ChatMessage.builder()
					.type(MessageType.LEAVE)
					.sender(username)
					.build();
			// Send a message to notify others that this user has left
			messageTemplate.convertAndSend("/topic/public", chatMessage);
		}
	}
}
